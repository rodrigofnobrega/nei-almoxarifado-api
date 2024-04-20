package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleEntity toRole(RoleCreateDto roleCreateDto) {
        return new ModelMapper().map(roleCreateDto, RoleEntity.class);
    }

    public static RoleEntity toRole(RoleResponseDto roleResponseDto) {
        return new ModelMapper().map(roleResponseDto, RoleEntity.class);
    }

    public static RoleResponseDto toResponseDto(RoleEntity roleEntity) {
        String role = roleEntity.getRole();

        if (role.contains("ROLE_")) {
            role = role.substring("ROLE_".length());
        }

        String finalRole = role;

        PropertyMap<RoleEntity, RoleResponseDto> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setRole(finalRole);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(roleEntity, RoleResponseDto.class);
    }

    public static Page<RoleResponseDto> toPageResponseDTO(Page<RoleEntity> data) {
        List<RoleResponseDto> dtos = data.getContent().stream()
                .map(RoleMapper::toResponseDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, data.getPageable(), data.getTotalElements());
    }

    public static List<RoleEntity> toListRole(List<RoleResponseDto> rolesDto) {
        return rolesDto.stream().map(dto -> toRole(dto)).collect(Collectors.toList());
    }
}
