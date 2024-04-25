package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.PageableMapper;
import com.ufrn.nei.almoxarifadoapi.dto.pageable.PageableDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.infra.RestErrorMessage;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RecordProjection;
import com.ufrn.nei.almoxarifadoapi.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Records", description = "Contém todas as operações relativas aos recursos para leitura dos registros")
@RestController
@RequestMapping("/api/v1/records")
public class RecordController {
        @Autowired
        private RecordService recordService;

        @Operation(summary = "Recuperar todos os registros",
                description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
                security = @SecurityRequirement(name = "security"),
                parameters = {
                        @Parameter(in = ParameterIn.QUERY, name = "page",
                                content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                                description = "Representa a página retornada"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "size",
                                content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                                description = "Representa o total de elementos por página"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                                array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                                description = "Representa a ordenação dos resultados. Aceita múltiplos critérios de ordenação"
                        ),
                },
                responses = {
                        @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                                content = @Content(mediaType = "applicaton/json;charset=UTF-8", schema = @Schema(implementation = PageableDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
        })
        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
                Page<RecordProjection> records = recordService.findAll(pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }


        @Operation(summary = "Listar registros pelo ID.",
                description = "Listará os registros com o ID informado. Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
                security = @SecurityRequirement(name = "security"),
                responses = {
                        @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso.",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecordProjection.class))),
                        @ApiResponse(responseCode = "404", description = "Erro ao listar registro. Registro não foi encontrado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
                        @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
        })
        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<RecordProjection> findById(@PathVariable Long id) {
                RecordProjection response = recordService.findById(id);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Recuperar registros por informações de usuário",
                description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
                security = @SecurityRequirement(name = "security"),
                parameters = {
                        @Parameter(in = ParameterIn.QUERY, name = "id",
                                schema = @Schema(type = "integer", defaultValue = "null"),
                                description = "Identificador do usuário. Deve ser maior que zero"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "name",
                                schema = @Schema(type = "string", defaultValue = "null"),
                                description = "Nome do usuário"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "email",
                                schema = @Schema(type = "string", format = "email", defaultValue = "null"),
                                description = "E-mail do usuário"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "role",
                                schema = @Schema(type = "string", defaultValue = "null"),
                                description = "Role do usuário"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "size",
                                schema = @Schema(type = "integer", defaultValue = "20"),
                                description = "Número máximo de registros por página"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "page",
                                schema = @Schema(type = "integer", defaultValue = "0"),
                                description = "Página a ser retornada"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "sort",
                                schema = @Schema(type = "string", defaultValue = "id,asc"),
                                description = "Ordenação dos resultados. Pode aceitar múltiplos critérios de ordenação"
                        )
                },
                responses = {
                        @ApiResponse(responseCode = "200", description = "Recursos localizados com sucesso",
                                content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PageableDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
                        @ApiResponse(responseCode = "403", description = "Erro na validação dos parâmetros",
                                content = @Content(mediaType = "application/json;charset=UTF-8"))
                })
        @GetMapping("/query/users")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findByUsers(@RequestParam(required = false) @Positive Long id,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) @Email String email,
                                                       @RequestParam(required = false) String role,
                                                       Pageable pageable) {
                Page<RecordProjection> records = recordService.findByUsers(id, name, email, role, pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Listar registros por informações do item.",
                description = "Listará os registros utilizando as informações dos itens. Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
                security = @SecurityRequirement(name = "security"),
                parameters = {
                        @Parameter(in = ParameterIn.QUERY, name = "id",
                                schema = @Schema(type = "integer", defaultValue = "null"),
                                description = "Identificador do item. Deve ser maior que zero"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "name",
                                schema = @Schema(type = "string", defaultValue = "null"),
                                description = "Nome do item"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "itemTagging",
                                schema = @Schema(type = "integer", defaultValue = "null"),
                                description = "Código sipac do item"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "size",
                                schema = @Schema(type = "integer", defaultValue = "20"),
                                description = "Número máximo de registros por página"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "page",
                                schema = @Schema(type = "integer", defaultValue = "0"),
                                description = "Página a ser retornada"
                        ),
                        @Parameter(in = ParameterIn.QUERY, name = "sort",
                                schema = @Schema(type = "string", defaultValue = "id,asc"),
                                description = "Ordenação dos resultados. Pode aceitar múltiplos critérios de ordenação"
                        )
                },
                responses = {
                        @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso.",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageableDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Usuário não está autenticado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
                        @ApiResponse(responseCode = "403", description = "Erro na validação dos parâmetros",
                                content = @Content(mediaType = "application/json;charset=UTF-8"))
        })
        @GetMapping("/query/itens")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findByItens(@RequestParam(required = false) @Positive Long id,
                                                       @RequestParam(required = false) @Positive Long itemTagging,
                                                       @RequestParam(required = false) String name,
                                                       Pageable pageable) {
                Page<RecordProjection> records = recordService.findByItens(id, itemTagging, name, pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
