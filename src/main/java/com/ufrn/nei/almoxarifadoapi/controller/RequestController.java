package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RequestMapper;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.request.RequestResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
}
