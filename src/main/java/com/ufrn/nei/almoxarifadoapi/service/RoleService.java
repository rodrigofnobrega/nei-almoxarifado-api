package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RoleProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public RoleEntity save(RoleCreateDto data) {
        RoleEntity role = RoleMapper.toRole(data);

        roleRepository.save(role);
        return role;
    }

    @Transactional(readOnly = true)
    public RoleEntity findById(Long id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Role não encontrada com o id: %d", id)));
        return role;
    }

    @Transactional(readOnly = true)
    public Optional<RoleEntity> findByRoleName(String role) {
        return roleRepository.findByRole(role);
    }

    @Transactional(readOnly = true)
    public Page<RoleProjection> findAllRoles(Pageable pageable) {
        return roleRepository.findAllPageable(pageable);
    }

    @Transactional
    public Boolean deleteById(Long id) {
        try {
            if (roleRepository.existsById(id)) {
                roleRepository.deleteById(id);
                return true;
            } else {
                log.warn("Tentativa de excluir um registro inexistente com ID: {}", id);
                return false;
            }
        } catch (IllegalArgumentException ex) {
            log.error("Erro ao excluir registro com ID: {}", id, ex);
            throw new IllegalArgumentException("Erro ao excluir registro.", ex);
        }
    }

    @Transactional
    public RoleEntity updateRoleById(Long id, RoleUpdateDto newRole) {
        RoleEntity role = findById(id);
        role.setRole(newRole.getNewRole());

        roleRepository.save(role);
        return role;
    }
}