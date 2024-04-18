package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.PageableMapper;
import com.ufrn.nei.almoxarifadoapi.dto.pageable.PageableDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.infra.RestErrorMessage;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RecordProjection;
import com.ufrn.nei.almoxarifadoapi.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Records", description = "Contém todas as operações relativas aos recursos para criação e leitura dos registros")
@RestController
@RequestMapping("/api/v1/records")
public class RecordController {
        @Autowired
        private RecordService recordService;

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
                Page<RecordProjection> records = recordService.findAll(pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Listar registros pelo ID.", description = "Listará os registros com o ID informado.", responses = {
                        @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecordResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Não foi possível encontrar o registro.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
        })
        @GetMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<RecordProjection> findById(@PathVariable Long id) {
                RecordProjection response = recordService.findById(id);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Listar registros por informações do usuário.", description = "Listará os registros utilizando as informações dos usuários.", responses = {
                        @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecordResponseDTO.class)))
        })
        @GetMapping("/query/users")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findByUsers(@RequestParam(required = false) Long id,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String email,
                                                       @RequestParam(required = false) String role,
                                                       Pageable pageable) {
                Page<RecordProjection> records = recordService.findByUsers(id, name, email, role, pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "Listar registros por informações do item.", description = "Listará os registros utilizando as informações dos itens.", responses = {
                        @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RecordResponseDTO.class)))
        })
        @GetMapping("/query/itens")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> findByItens(@RequestParam(required = false) Long id,
                                                                   @RequestParam(required = false) Long itemTagging,
                                                                   @RequestParam(required = false) String name,
                                                                   Pageable pageable) {
                Page<RecordProjection> records = recordService.findByItens(id, itemTagging, name, pageable);
                PageableDTO response = PageableMapper.toDto(records);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
