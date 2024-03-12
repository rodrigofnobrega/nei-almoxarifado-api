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
        String role = userEntity.getRole().getRole();

        if (role.contains("ROLE_")) {
            role = role.substring("ROLE_".length());
        }

        String finalRole = role;

        PropertyMap<UserEntity, UserResponseDTO> propertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setRole(finalRole);
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
