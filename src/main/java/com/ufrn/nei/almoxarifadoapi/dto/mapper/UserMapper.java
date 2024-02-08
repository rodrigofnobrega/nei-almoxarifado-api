package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.modelmapper.ModelMapper;

public class UserMapper {
    public static UserResponseDTO toResponseDTO(UserEntity userEntity) {
        return new ModelMapper().map(userEntity, UserResponseDTO.class);
    }

    public static UserEntity toItem(UserResponseDTO userDTO) {
        return new ModelMapper().map(userDTO, UserEntity.class);
    }

    public static UserEntity toItem(UserCreateDTO userDTO) {
        return new ModelMapper().map(userDTO, UserEntity.class);
    }
}
