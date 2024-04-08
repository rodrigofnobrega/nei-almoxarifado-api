package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.exception.HigherQuantityException;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtAuthenticationContext;
import com.ufrn.nei.almoxarifadoapi.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (data.getQuantity() > item.getQuantity()) {
            throw new HigherQuantityException();
        }

        RequestEntity request = RequestMapper.toRequest(data, user, item, status);

        requestRepository.save(request);

        return request;
    }
}
