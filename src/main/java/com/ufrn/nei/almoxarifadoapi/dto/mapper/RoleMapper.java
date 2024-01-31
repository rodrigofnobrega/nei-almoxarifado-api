package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import org.modelmapper.ModelMapper;

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
        return new ModelMapper().map(roleEntity, RoleResponseDto.class);
    }

    public static List<RoleResponseDto> toListResponseDto(List <RoleEntity> roles) {
        return roles.stream().map(role -> toResponseDto(role)).collect(Collectors.toList());
    }
}
