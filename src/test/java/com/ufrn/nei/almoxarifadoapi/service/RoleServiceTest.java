package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private RoleEntity role;

    @BeforeEach
    public void setup() {
        role = createRole();
        MockitoAnnotations.openMocks(this);
    }

    private RoleEntity createRole() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setRole("Cliente");

        return roleEntity;
    }

    private RoleUpdateDto createRoleResponseDto() {
        RoleUpdateDto roleDto = new RoleUpdateDto();
        roleDto.setNewRole("Admin");

        return roleDto;
    }

    @Test
    @DisplayName("Testa a criação de novas roles")
    void saveRoleTest() {
        when(roleRepository.save(role)).thenReturn(role);

        RoleResponseDto savedRole = roleService.save(role);

        verify(roleRepository, times(1)).save(role);

        assertNotNull(savedRole);

        assertEquals(role.getId(), savedRole.getId());
        assertEquals(role.getRole(), savedRole.getRole());
    }

    @Test
    @DisplayName("Testa encontrar uma Role pelo ID no serviço")
    @Transactional(readOnly = true)
    void testFindRoleById() {
        Long roleId = role.getId();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        RoleResponseDto foundRole = roleService.findById(roleId);

        verify(roleRepository, times(1)).findById(roleId);

        assertNotNull(foundRole);

        assertEquals(role.getId(), foundRole.getId());
        assertEquals(role.getRole(), foundRole.getRole());
    }

    @Test
    @DisplayName("Teste para encontrar todas as roles cadastradas")
    @Transactional(readOnly = true)
    void testFindAllRoles() {
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        List<RoleResponseDto> roleResponse = roleService.findAllRoles();

        verify(roleRepository, times(1)).findAll();

        assertNotNull(roleResponse);

        assertEquals(role.getRole(), roleResponse.get(0).getRole());
        assertEquals(role.getId(), roleResponse.get(0).getId());
    }

    @Test
    @DisplayName("Teste para mudar o nome de uma role pelo ID")
    void testRenameRole() {
        RoleUpdateDto newRole = createRoleResponseDto();

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(roleRepository.save(any(RoleEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoleResponseDto response = roleService.updateRoleById(role.getId(), newRole);

        verify(roleRepository, times(1)).findById(role.getId());
        verify(roleRepository, times(1)).save(any(RoleEntity.class));

        assertEquals(role.getId(), response.getId());
        assertEquals(newRole.getNewRole(), response.getRole());
    }
}