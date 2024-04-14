package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.exception.PageableException;
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
        if (size == 0) {
            throw new PageableException("O tamanho não pode ser zero");
        } else if (page < 0) {
            throw new PageableException("O tamanho da página não pode ser negativo");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<RequestEntity> requestPage = requestService.findAll(pageable);

        if (page >= requestPage.getTotalPages()) {
            throw new PageableException("Tamanho de páginas solicitados maior que o total");
        }

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
}
