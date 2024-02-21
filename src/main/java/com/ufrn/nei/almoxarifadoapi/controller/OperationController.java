package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.RecordMapper;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateItemDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.service.OperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operacoes")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @PostMapping("/emprestimo")
    public ResponseEntity<RecordResponseDTO> toLend(@RequestBody @Valid RecordCreateDTO createDTO) {
        RecordEntity record = operationService.toLend(createDTO);

        return ResponseEntity.status(HttpStatus.OK).body(RecordMapper.toResponseDTO(record));
    }

    @PostMapping("/devolucao")
    public ResponseEntity<RecordResponseDTO> toReturn(@RequestBody @Valid RecordCreateDTO createDTO) {
        RecordEntity record = operationService.toReturn(createDTO);

        return ResponseEntity.status(HttpStatus.OK).body(RecordMapper.toResponseDTO(record));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<RecordResponseDTO> toRegister(@RequestBody @Valid RecordCreateItemDTO createDTO) {
        RecordEntity record = operationService.toRegister(createDTO);

        return ResponseEntity.status(HttpStatus.OK).body(RecordMapper.toResponseDTO(record));
    }

    @PostMapping("/exclusao")
    public ResponseEntity<RecordResponseDTO> toDelete(@RequestBody @Valid RecordCreateDTO createDTO) {
        RecordEntity record = operationService.toDelete(createDTO);

        return ResponseEntity.status(HttpStatus.OK).body(RecordMapper.toResponseDTO(record));
    }
}
