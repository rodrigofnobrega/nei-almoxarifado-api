package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO toResponseDTO(UserEntity userEntity) {
        String role;

        if (userEntity.getRole().getRole().contains("ROLE_")) {
            role = userEntity.getRole().getRole().substring("ROLE_".length());
        } else {
            role = userEntity.getRole().getRole();
        }

        PropertyMap<UserEntity, UserResponseDTO> propertyMap = new PropertyMap<UserEntity, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    public static UserEntity toUser(UserCreateDTO userDTO) {
        PropertyMap<UserCreateDTO, UserEntity> propertyMap = new PropertyMap<UserCreateDTO, UserEntity>() {
            @Override
            protected void configure() {
                map().setId(null);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);

        return modelMapper.map(userDTO, UserEntity.class);
    }

    public static List<UserResponseDTO> toListResponseDTO(List<UserEntity> users) {
        return users.stream().map(i -> toResponseDTO(i)).collect(Collectors.toList());
    }
}
