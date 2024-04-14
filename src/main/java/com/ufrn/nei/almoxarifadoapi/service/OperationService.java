package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateItemDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordRequestDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private RecordService recordService;

    public RecordEntity toConsume(RecordCreateDTO createDTO) {
        itemService.deleteOrConsumeItem(createDTO.getItemID(), createDTO.getQuantity());

        RecordEntity record = recordService.save(createDTO, RecordOperationEnum.CONSUMO);

        return record;
    }

    public RecordEntity toConsume(RecordRequestDTO createDTO) {
        itemService.deleteOrConsumeItem(createDTO.getItemID(), createDTO.getQuantity());

        RecordEntity record = recordService.save(createDTO, RecordOperationEnum.CONSUMO);

        return record;
    }

    public RecordEntity toRegister(RecordCreateItemDTO createDTO) {
        ItemEntity item = itemService.createItem(createDTO.getItem());

        RecordEntity record = recordService.save(createDTO, item, RecordOperationEnum.CADASTRO);

        return record;
    }

    public RecordEntity toDelete(RecordCreateDTO createDTO) {
        itemService.deleteOrConsumeItem(createDTO.getItemID(), createDTO.getQuantity());

        RecordEntity record = recordService.save(createDTO, RecordOperationEnum.EXCLUSAO);

        return record;
    }
}
