package com.ufrn.nei.almoxarifadoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RoleRepository;
import com.ufrn.nei.almoxarifadoapi.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    private RoleResponseDto roleResponseDto;

    @BeforeEach
    void setup() {
        roleResponseDto = new RoleResponseDto(1L, "Cliente");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRole() throws Exception {
        RoleCreateDto roleCreateDto = new RoleCreateDto("Cliente");

        when(roleService.save(roleCreateDto)).thenReturn(RoleMapper.toRole(roleResponseDto));

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(roleCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role").value("Cliente"));

        verify(roleService, times(1)).save(roleCreateDto);
    }

    @Test
    void testFindRoleById() throws Exception {
        when(roleService.findById(1L)).thenReturn(RoleMapper.toRole(roleResponseDto));

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role").value("Cliente"));

        verify(roleService, times(1)).findById(1L);
    }

    @Test
    void testFindAllRoles() throws Exception {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setRole("Cliente");
        Page<RoleEntity> mockPage = new PageImpl<>(Collections.singletonList(roleEntity));
        when(roleService.findAllRoles(any(Pageable.class))).thenReturn(mockPage);

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].role").value("Cliente"));

        verify(roleService, times(1)).findAllRoles(any(Pageable.class));
    }

    @Test
    public void testUpdateRole() throws Exception {
        RoleUpdateDto roleUpdateDto = new RoleUpdateDto("Cliente");

        when(roleService.updateRoleById(1L, roleUpdateDto)).thenReturn(RoleMapper.toRole(roleResponseDto));

        mockMvc.perform(put("/api/v1/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(roleUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.role").value("Cliente"));

        verify(roleService, times(1)).updateRoleById(1L, roleUpdateDto);
    }
}
