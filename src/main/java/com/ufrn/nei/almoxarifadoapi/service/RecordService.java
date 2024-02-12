package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.ItemMapper;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.UserMapper;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public RecordEntity save(RecordCreateDTO recordCreateDTO) {
        UserEntity user = userService.findById(recordCreateDTO.getUserID());
        ItemEntity item = ItemMapper.toItem(itemService.findItem(recordCreateDTO.getItemID()));

        RecordEntity record = new RecordEntity(user, item);

        return recordRepository.save(record);
    }
}
