package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    private RoleResponseDto roleResponseDto;

    @BeforeEach
    void setup() {
        roleResponseDto = createRoleResponseDto();
        MockitoAnnotations.openMocks(this);
    }

    private RoleResponseDto createRoleResponseDto() {
        RoleResponseDto role = new RoleResponseDto();
        role.setId(1L);
        role.setRole("Cliente");

        return role;
    }

    @Test
    void testFindRoleById() throws Exception {
        when(roleService.findById(1L)).thenReturn(roleResponseDto);

        mockMvc.perform(get("/api/v1/roles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.role").value("Cliente"));

        verify(roleService, times(1)).findById(1L);
    }

    @Test
    void testFindAllRoles() throws Exception {
        List<RoleResponseDto> mockRoles = Collections.singletonList(roleResponseDto);

        when(roleService.findAllRoles()).thenReturn(mockRoles);

        mockMvc.perform(get("/api/v1/roles"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].role").value("Cliente"));

        verify(roleService, times(1)).findAllRoles();
    }
}
