package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.UserMapper;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Transactional
    public UserResponseDTO save(UserCreateDTO createDTO) {
        UserEntity user = UserMapper.toItem(createDTO);

        for (RoleResponseDto role : roleService.findAllRoles()) {
            if (role.getRole().equalsIgnoreCase("cliente")) {
                user.setRole(RoleMapper.toRole(role));
            }
        }

        user = userRepository.save(user);

        return UserMapper.toResponseDTO(user);
    }

    public UserResponseDTO findById(Long id) {
       return UserMapper.toItem(userRepository.findById(id).orElseThrow(
               () -> new EntityNotFoundException(String.format("Usuário não encontrado com id='%s'", id))
       ));
    }

    public UserResponseDTO findByEmail(String email) {
        return UserMapper.toItem(userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário não encontrado com email='%s'", email))
        ));
    }

    public List<UserResponseDTO> findAll() {
        return UserMapper.toListResponseDTO(userRepository.findAll());
    }
}
