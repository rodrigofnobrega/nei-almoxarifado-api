package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        role = new RoleEntity(1L, "Cliente");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Testa a criação de novas roles")
    void saveRoleTest() {
        when(roleRepository.save(role)).thenReturn(role);

        RoleCreateDto data = new RoleCreateDto();
        data.setRole(role.getRole());

        RoleEntity savedRole = roleService.save(data);

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

        RoleEntity foundRole = roleService.findById(roleId);

        verify(roleRepository, times(1)).findById(roleId);

        assertNotNull(foundRole);

        assertEquals(role.getId(), foundRole.getId());
        assertEquals(role.getRole(), foundRole.getRole());
    }

    @Test
    @DisplayName("Teste para encontrar todas as roles cadastradas")
    @Transactional(readOnly = true)
    void testFindAllRoles() {
        // Criar uma lista paginada com a entidade de papel
        Page<RoleEntity> mockPage = new PageImpl<>(Collections.singletonList(role));

        // Configurar o mock para retornar a lista paginada
        when(roleRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Chamar o método do serviço
        Page<RoleEntity> rolePage = roleService.findAllRoles(PageRequest.of(0, 10));

        // Verificar se o método do repositório foi chamado
        verify(roleRepository, times(1)).findAll(any(Pageable.class));

        // Verificar se a lista não é nula
        assertNotNull(rolePage);

        // Verificar se os valores da primeira entidade na lista correspondem aos valores esperados
        RoleEntity roleEntity = rolePage.getContent().get(0);
        assertEquals(role.getId(), roleEntity.getId());
        assertEquals(role.getRole(), roleEntity.getRole());
    }

    @Test
    @DisplayName("Teste para mudar o nome de uma role pelo ID")
    void testRenameRole() {
        RoleUpdateDto newRole = new RoleUpdateDto("Admin");

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(roleRepository.save(any(RoleEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RoleEntity response = roleService.updateRoleById(role.getId(), newRole);

        verify(roleRepository, times(1)).findById(role.getId());
        verify(roleRepository, times(1)).save(any(RoleEntity.class));

        assertEquals(role.getId(), response.getId());
        assertEquals(newRole.getNewRole(), response.getRole());
    }
}