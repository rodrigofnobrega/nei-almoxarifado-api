package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordRegisterDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtAuthenticationContext;
import com.ufrn.nei.almoxarifadoapi.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Transactional
    public RecordEntity save(RecordRegisterDTO recordCreateDTO, RecordOperationEnum operationEnum) {
        UserEntity user = userService.findById(JwtAuthenticationContext.getId());
        ItemEntity item = itemService.findById(recordCreateDTO.getItemID());
        RecordEntity record = new RecordEntity(user, item, recordCreateDTO.getQuantity(), operationEnum);

        record = recordRepository.save(record);

        return record;
    }

    @Transactional
    public RecordEntity save(ItemCreateDTO createItemDTO, ItemEntity item, RecordOperationEnum operationEnum) {
        UserEntity user = userService.findById(JwtAuthenticationContext.getId());
        RecordEntity record = new RecordEntity(user, item, createItemDTO.getQuantity(), operationEnum);

        record = recordRepository.save(record);

        return record;
    }

    @Transactional(readOnly = true)
    public List<RecordEntity> findAll() {
        return recordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RecordEntity findById(Long id) {
        return recordRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Registro não encontrado com o id='%s'", id)));
    }

    @Transactional(readOnly = true)
    public List<RecordEntity> findByUsers(Long id, String name, String email, String role) {
        return recordRepository.findByUsers(id, name, email, role);
    }

    @Transactional(readOnly = true)
    public List<RecordEntity> findByItens(Long id, Long itemTagging, String name) {
        return recordRepository.findByItens(id, itemTagging, name);
    }
}
