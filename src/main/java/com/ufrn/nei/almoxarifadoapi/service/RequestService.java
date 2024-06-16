package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.exception.ModifyStatusException;
import com.ufrn.nei.almoxarifadoapi.exception.StatusNotFoundException;
import com.ufrn.nei.almoxarifadoapi.exception.UnauthorizedAccessException;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtAuthenticationContext;
import com.ufrn.nei.almoxarifadoapi.infra.mail.MailService;
import com.ufrn.nei.almoxarifadoapi.repository.RequestRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import com.ufrn.nei.almoxarifadoapi.repository.projection.RequestProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private MailService mailService;

    @Transactional
    public RequestEntity create(RequestCreateDTO data) {
        UserEntity user = userService.findById(JwtAuthenticationContext.getId());
        ItemEntity item = itemService.findById(data.getItemID());
        RequestStatusEnum status = RequestStatusEnum.PENDENTE;

        RequestEntity request = RequestMapper.toRequest(data, user, item, status);

        requestRepository.save(request);
        mailService.sendMailRequestCreatedAsync(user.getEmail(), user.getName(),
                item.getName(), request.getCreatedAt(), request.getQuantity());

        return request;
    }

    @Transactional
    public Boolean accept(Long id) {
        RequestEntity request = findById(id);

        validateRequestStatus(request, RequestStatusEnum.ACEITO);

        RecordCreateDTO requestDTO =
                new RecordCreateDTO(request.getUser().getId(), request.getItem().getId(), Math.toIntExact(request.getQuantity()));

        if (operationService.toConsume(requestDTO) == null) {
            return Boolean.FALSE;
        }

        if (updateRequestStatus(request, RequestStatusEnum.ACEITO)) {
            UserEntity user = request.getUser();
            ItemEntity item = request.getItem();
            mailService.sendMailRequestAcceptedAsync(user.getEmail(), user.getName(),
                    item.getName(), request.getUpdatedAt(), request.getQuantity());

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Transactional
    public Boolean decline(Long id) {
        RequestEntity request = findById(id);

        if (updateRequestStatus(request, RequestStatusEnum.RECUSADO)) {
            UserEntity user = request.getUser();
            ItemEntity item = request.getItem();
            mailService.sendMailRequestDeniedAsync(user.getEmail(), user.getName(),
                    item.getName(), request.getUpdatedAt(), request.getQuantity());

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Transactional
    public Boolean cancel(Long id) {
        RequestEntity request = findById(id);

        if (updateRequestStatus(request, RequestStatusEnum.CANCELADO)) {
            UserEntity user = request.getUser();
            ItemEntity item = request.getItem();
            mailService.sendMailRequestCanceledAsync(user.getEmail(), user.getName(),
                    item.getName(), request.getUpdatedAt(), request.getQuantity());

            return Boolean.TRUE;
        }

    return Boolean.FALSE;
  }

    @Transactional(readOnly = true)
    public Page<RequestProjection> findAll(Pageable pageable) {
        Page<RequestProjection> requests = requestRepository.findAllPageable(pageable);

        return requests;
    }

    @Transactional(readOnly = true)
    public RequestEntity findById(Long id) {
        RequestEntity request = requestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Solicitação não encontrado com id='%s'", id)));

        // Id do usuário que fez a chamada ao método
        Long userIdRequest = JwtAuthenticationContext.getId();

        // Verificando se o usuário que chamou o controller é o dono da solicitação.
        // A exceção de acesso não autorizado não se aplica ao ADMIN,
        // por isso verificamos se quem invocou o método não possui a ROLE=ADMIN
        if (!Objects.equals(userIdRequest, request.getUser().getId()) &&
                !JwtAuthenticationContext.getAuthoritie().toString().contains("ROLE_ADMIN")) {
            throw new UnauthorizedAccessException(String.format("O usuário='%s' está tentando obter a " +
                    "solicitação de outro usuário", JwtAuthenticationContext.getEmail()));
        }

        return request;
    }

    @Transactional(readOnly = true)
    public Page<RequestProjection> findByStatus(String status, Pageable pageable) {
        // Convertendo a string de status para o enum statusEnum
        RequestStatusEnum statusEnum = Arrays.stream(RequestStatusEnum.values())
                .filter(e -> e.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status='%s' não encontrado", status)));

        Page<RequestProjection> requests = requestRepository.findByStatus(statusEnum, pageable);

        return requests;
    }

    @Transactional(readOnly = true)
    public Page<RequestProjection> findByUserID(Long id, Pageable pageable) {
        Page<RequestProjection> requests = requestRepository.findByUserId(id, pageable);

        return requests;
    }

    @Transactional(readOnly = true)
    public Page<RequestProjection> findByItemID(Long id, Pageable pageable) {
        Page<RequestProjection> requests = requestRepository.findByItemId(id, pageable);

        return requests;
    }

    // Métodos Auxiliares

    @Transactional
    private Boolean updateRequestStatus(RequestEntity request, RequestStatusEnum status) {
        validateRequestStatus(request, status);

        try {
            // Atualizar o status da solicitação
            request.setStatus(status);
            request.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            requestRepository.save(request);
        } catch (RuntimeException err) {
            log.error(err.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void validateRequestStatus(RequestEntity request, RequestStatusEnum status) {
        // Verificar se a solicitação já possui o novo status
        if (request.getStatus().equals(status)) {
            log.info("Solicitação já foi " +  status.toString().toLowerCase() +  " anteriormente");
            throw new ModifyStatusException("Solicitação já foi" +  status.toString().toLowerCase() +  "anteriormente");
        }

        // Verificar se a solicitação está pendente
        if (!request.getStatus().equals(RequestStatusEnum.PENDENTE)) {
            log.warn("Tentando alterar o status de uma solicitação que não está como pendente.");
            throw new ModifyStatusException("Não é possível alterar o status de uma solicitação que não está pendente");
        }
    }
}
