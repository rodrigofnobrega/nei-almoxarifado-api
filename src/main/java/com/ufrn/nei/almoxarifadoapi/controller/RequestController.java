package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.PageableMapper;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.pageable.PageableDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.exception.StatusNotFoundException;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RequestProjection;
import com.ufrn.nei.almoxarifadoapi.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/decline/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> decline(@PathVariable Long id) {
        Boolean request = requestService.decline(id);

        if (request == Boolean.TRUE) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/cancel/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        Boolean request = requestService.cancel(id);

        if (request == Boolean.TRUE) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> findAll(Pageable pageable) {
        Page<RequestProjection> requestPage = requestService.findAll(pageable);
        PageableDTO response = PageableMapper.toDto(requestPage);

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
    public ResponseEntity<PageableDTO> findByStatus(@PathVariable String status,
                                                                 Pageable pageable) {
        Page<RequestProjection> requestPage = requestService.findByStatus(status, pageable);
        PageableDTO response = PageableMapper.toDto(requestPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> findByUserId(@PathVariable Long id,
                                                                 Pageable pageable) {
        Page<RequestProjection> requestPage = requestService.findByUserID(id, pageable);
        PageableDTO response = PageableMapper.toDto(requestPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/item/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> findByItemId(@PathVariable Long id,
                                                                 Pageable pageable) {
        Page<RequestProjection> requestPage = requestService.findByItemID(id, pageable);
        PageableDTO response = PageableMapper.toDto(requestPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
