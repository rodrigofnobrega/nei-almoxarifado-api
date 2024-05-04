package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.PageableMapper;
import com.ufrn.nei.almoxarifadoapi.dto.pageable.PageableDTO;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleCreateDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleUpdateDto;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RoleMapper;
import com.ufrn.nei.almoxarifadoapi.infra.RestErrorMessage;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RoleProjection;
import com.ufrn.nei.almoxarifadoapi.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Roles", description = "Contém todas as operações relativas aos recursos para criar, atualizar, deleter e ler uma role")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Operation(summary = "Cadastrar uma role",
            description = "Requsição exige o uso de um bearer token. Acesso restrito a role='ADMIN'.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Role cadastrado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleCreateDto roleCreateDto) {
        RoleEntity role = roleService.save(roleCreateDto);

        RoleResponseDto response = RoleMapper.toResponseDto(role);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar role pelo ID",
            description = "Requsição exige o uso de um bearer token. Acesso restrito a role='ADMIN'.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role encontrada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Role não encontrada.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDto> findRoleById(@PathVariable Long id) {
        RoleEntity role = roleService.findById(id);

        RoleResponseDto response = RoleMapper.toResponseDto(role);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar todas as roles.",
            description = "Requsição exige o uso de um bearer token. Acesso restrito a role='ADMIN'.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada."
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Representa o total de elementos por página."
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Representa a ordenação dos resultados. Aceita múltiplos critérios de ordenação."
                    ),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roles encontradas com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> findAllRoles(Pageable pageable) {
        Page<RoleProjection> roles = roleService.findAllRoles(pageable);
        PageableDTO response = PageableMapper.toDto(roles);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Deletar role pelo ID",
            description = "Requsição exige o uso de um bearer token. Acesso restrito a role='ADMIN'. NÃO DELETAR ROLES DE ADMIN E USUÁRIO",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Role deletada com sucesso.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Role não encontrada.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        if (roleService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Atualizar role pelo ID",
            description = "Requsição exige o uso de um bearer token. Acesso restrito a role='ADMIN'. NÃO ATUALIZAR ROLES DE ADMIN E USUÁRIO",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role atualizada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDto> updateRole(@PathVariable Long id, @RequestBody RoleUpdateDto updateRole) {
        RoleEntity role = roleService.updateRoleById(id, updateRole);

        RoleResponseDto response = RoleMapper.toResponseDto(role);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}