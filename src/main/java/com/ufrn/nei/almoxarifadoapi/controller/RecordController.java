package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.RecordMapper;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @PostMapping
    public ResponseEntity<RecordResponseDTO> createRecord(@RequestBody RecordCreateDTO recordCreateDTO) {
        RecordEntity response = recordService.save(recordCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(RecordMapper.toResponseDTO(response));
    }

    @GetMapping
    public ResponseEntity<List<RecordResponseDTO>> findAll() {
        List<RecordEntity> recordEntityList = recordService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(RecordMapper.toListResponseDTO(recordEntityList));
    }
}
