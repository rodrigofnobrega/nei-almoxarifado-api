package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.utils.RemoveRolePrefix;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class RequestMapper {
    public static RequestEntity toRequest(RequestCreateDTO data, UserEntity user, ItemEntity item, RequestStatusEnum status) {
        PropertyMap<RequestCreateDTO, RequestEntity> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(null);
                map().setUser(user);
                map().setItem(item);
                map().setStatus(status);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(data, RequestEntity.class);
    }

    public static RequestResponseDTO toResponseDTO(RequestEntity data) {
        UserResponseDTO user = UserMapper.toResponseDTO(data.getUser());
        ItemResponseDTO item = ItemMapper.toResponseDTO(data.getItem());

        String role = data.getUser().getRole().getRole();

        PropertyMap<RequestEntity, RequestResponseDTO> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setUser(user);
                map().getUser().setRole(RemoveRolePrefix.getRoleWithoutPrefix(role));
                map().setItem(item);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(data, RequestResponseDTO.class);
    }

    public static Page<RequestResponseDTO> toPageResponseDTO(Page<RequestEntity> data) {
        List<RequestResponseDTO> dtos = data.getContent().stream()
                .map(RequestMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, data.getPageable(), data.getTotalElements());
    }
}
