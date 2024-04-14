package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.exception.PageableException;
import com.ufrn.nei.almoxarifadoapi.exception.StatusNotFoundException;
import com.ufrn.nei.almoxarifadoapi.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RequestResponseDTO> create(@RequestBody @Valid RequestCreateDTO data) {
        RequestEntity request = requestService.create(data);
        RequestResponseDTO response = RequestMapper.toResponseDTO(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/accept/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> accept(@PathVariable Long id) {
        Boolean request = requestService.accept(id);

        if (request == Boolean.TRUE) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/decline/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> decline(@PathVariable Long id) {
        Boolean request = requestService.decline(id);

        if (request == Boolean.TRUE) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/cancel/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        Boolean request = requestService.cancel(id);

        if (request == Boolean.TRUE) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RequestResponseDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        validatePageParameters(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<RequestEntity> requestPage = requestService.findAll(pageable);

        validateTotalPages(page, requestPage.getTotalPages());

        Page<RequestResponseDTO> response = RequestMapper.toPageResponseDTO(requestPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<RequestResponseDTO> findById(@PathVariable Long id) {
        RequestEntity request = requestService.findById(id);

        RequestResponseDTO response = RequestMapper.toResponseDTO(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RequestResponseDTO>> findByStatus(@PathVariable String status,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        validatePageParameters(page, size);

        // Convertendo a string de status para o enum statusEnum
        RequestStatusEnum statusEnum = Arrays.stream(RequestStatusEnum.values())
                .filter(e -> e.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new StatusNotFoundException(String.format("Status='%s' não encontrado", status)));

        Pageable pageable = PageRequest.of(page, size);
        Page<RequestEntity> requestPage = requestService.findByStatus(statusEnum, pageable);

        validateTotalPages(page, requestPage.getTotalPages());

        Page<RequestResponseDTO> response = RequestMapper.toPageResponseDTO(requestPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Métodos auxiliares

    private void validatePageParameters(int page, int size) {
        if (size == 0) {
            throw new PageableException("O tamanho não pode ser zero");
        } else if (page < 0) {
            throw new PageableException("O tamanho da página não pode ser negativo");
        }
    }

    private void validateTotalPages(int page, int totalPages) {
        if (page >= totalPages) {
            throw new PageableException("Tamanho de páginas solicitados maior que o total");
        }

    }
}
