package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public RoleResponseDto save(RoleEntity roleEntity) {
        return RoleMapper.toResponseDto(roleRepository.save(roleEntity));
    }

    @Transactional(readOnly = true)
    public RoleResponseDto findById(Long id) {
        return RoleMapper.toResponseDto(roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Role não encontrada com o id: %d", id))
        ));
    }
}