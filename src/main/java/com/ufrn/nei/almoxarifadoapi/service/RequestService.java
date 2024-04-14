package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.exception.ModifyStatusException;
import com.ufrn.nei.almoxarifadoapi.exception.UnauthorizedAccessException;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtAuthenticationContext;
import com.ufrn.nei.almoxarifadoapi.repository.RequestRepository;

import java.util.Objects;

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

    @Transactional
    public RequestEntity create(RequestCreateDTO data) {
        UserEntity user = userService.findById(JwtAuthenticationContext.getId());
        ItemEntity item = itemService.findById(data.getItemID());
        RequestStatusEnum status = RequestStatusEnum.PENDENTE;

        RequestEntity request = RequestMapper.toRequest(data, user, item, status);

        requestRepository.save(request);

        return request;
    }

    @Transactional
    public Boolean decline(Long id) {
        RequestEntity request = findById(id);

        if (request.getStatus().equals(RequestStatusEnum.RECUSADO)) {
            return Boolean.TRUE;
        }

        if (!request.getStatus().equals(RequestStatusEnum.PENDENTE)) {
            log.warn("Tentando alterar o status de uma solicitaçã que não está como pendente.");
            throw new ModifyStatusException("Não é possível alterar o status de uma solicitação que não está pendente");
        }

        try {
            request.setStatus(RequestStatusEnum.RECUSADO);

            requestRepository.save(request);
        } catch (RuntimeException err) {
            log.error(err.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Transactional(readOnly = true)
    public Page<RequestEntity> findAll(Pageable pageable) {
        Page<RequestEntity> requests = requestRepository.findAll(pageable);

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
}
