package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.parameters.P;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleEntity toRole(RoleCreateDto roleCreateDto) {
        return new ModelMapper().map(roleCreateDto, RoleEntity.class);
    }

    public static RoleEntity toRole(RoleResponseDto roleCreateDto) {
        return new ModelMapper().map(roleCreateDto, RoleEntity.class);
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

    public static List<RoleResponseDto> toListResponseDto(List <RoleEntity> roles) {
        return roles.stream().map(role -> toResponseDto(role)).collect(Collectors.toList());
    }
}
