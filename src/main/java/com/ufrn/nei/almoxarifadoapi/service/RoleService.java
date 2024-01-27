package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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

    @Transactional(readOnly = true)
    public List<RoleResponseDto> findAllRoles() {
        return RoleMapper.toListResponseDto(roleRepository.findAll());
    }

    @Transactional
    public Boolean deleteById(Long id) {
        try {
            if (roleRepository.existsById(id)) {
                roleRepository.deleteById(id);
                return Boolean.TRUE;
            } else {
                log.warn("Tentativa de excluir um registro inexistente com ID: {}", id);
                return Boolean.FALSE;
            }
        } catch (IllegalArgumentException ex) {
            log.error("Erro ao excluir registro com ID: {}", id, ex);
            throw new IllegalArgumentException("Erro ao excluir registro.", ex);
        }
    }
}