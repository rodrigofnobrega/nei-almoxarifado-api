package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.repository.RecordRepository;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RecordProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Transactional
    public RecordEntity save(RecordCreateDTO recordCreateDTO, RecordOperationEnum operationEnum) {
        UserEntity user = userService.findById(recordCreateDTO.getUserID());
        ItemEntity item = itemService.findById(recordCreateDTO.getItemID());
        RecordEntity record = new RecordEntity(user, item, recordCreateDTO.getQuantity(), operationEnum);

        record = recordRepository.save(record);

        return record;
    }

    @Transactional(readOnly = true)
    public Page<RecordProjection> findAll(Pageable pageable) {
        return recordRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public RecordProjection findById(Long id) {
        return recordRepository.findByIdProjection(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Registro não encontrado com o id='%s'", id)));
    }

    @Transactional(readOnly = true)
    public Page<RecordProjection> findByUsers(Long id, String name, String email, String role, Pageable pageable) {
        return recordRepository.findByUsers(id, name, email, role, pageable);
    }

    @Transactional(readOnly = true)
    public Page<RecordProjection> findByItens(Long id, Long itemTagging, String name, Pageable pageable) {
        return recordRepository.findByItens(id, itemTagging, name, pageable);
    }
}
