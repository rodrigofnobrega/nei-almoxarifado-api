package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleCreateDto roleCreateDto) {
        RoleResponseDto role = roleService.save(RoleMapper.toRole(roleCreateDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> findRoleById(@PathVariable Long id) {
        RoleResponseDto role = roleService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> findAllRoles() {
        List<RoleResponseDto> roles = roleService.findAllRoles();

        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id, @RequestBody RoleUpdateDto updateRole) {
        RoleResponseDto role = roleService.updateRoleById(id, updateRole);

        return ResponseEntity.status(HttpStatus.OK).body(role);
    }
}