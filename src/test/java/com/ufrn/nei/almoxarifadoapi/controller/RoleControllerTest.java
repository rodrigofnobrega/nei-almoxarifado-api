package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoleService roleService;

    @Test
    public void testFindAllRoles() throws Exception {
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setId(1L);
        roleResponseDto.setRole("Cliente");

        List<RoleResponseDto> mockRoles = Collections.singletonList(roleResponseDto );

        when(roleService.findAllRoles()).thenReturn(mockRoles);

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isFound())
                .andExpect(content().json("[{ 'id' : 1, 'role': 'Cliente' }]"));
    }
}
