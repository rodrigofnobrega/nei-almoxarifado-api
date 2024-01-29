package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private RoleEntity createRole(Long id, String role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setRole("Admin");

        return roleEntity;
    }

    @Test
    @DisplayName("Testa a criação de novas roles")
    void saveRoleTest() {
        RoleEntity roleEntity = createRole(1L, "Cliente");

        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);

        RoleResponseDto savedRole = roleService.save(roleEntity);

        verify(roleRepository, times(1)).save(roleEntity);

        assertNotNull(savedRole);

        assertEquals(roleEntity.getId(), savedRole.getId());
        assertEquals(roleEntity.getRole(), savedRole.getRole());
    }

    @Test
    @DisplayName("Testa encontrar uma Role pelo ID no serviço")
    @Transactional(readOnly = true)
    void testFindRoleById() {
        Long roleId = 1L;
        RoleEntity roleEntity = createRole(roleId, "Cliente");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(roleEntity));

        RoleResponseDto foundRole = roleService.findById(roleId);

        verify(roleRepository, times(1)).findById(roleId);

        assertNotNull(foundRole);

        assertEquals(roleEntity.getId(), foundRole.getId());
        assertEquals(roleEntity.getRole(), foundRole.getRole());
    }
}