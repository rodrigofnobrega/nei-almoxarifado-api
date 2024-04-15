package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordRegisterDTO;
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

    public RecordEntity toConsume(RecordRegisterDTO createDTO) {
        itemService.deleteItem(createDTO.getItemID(), createDTO.getQuantity());

        RecordEntity record = recordService.save(createDTO, RecordOperationEnum.CONSUMO);

        return record;
    }

    public RecordEntity toRegister(ItemCreateDTO createDTO) {
        ItemEntity item = itemService.createItem(createDTO);

        RecordEntity record = recordService.save(createDTO, item, RecordOperationEnum.CADASTRO);

        return record;
    }

    public RecordEntity toDelete(RecordRegisterDTO deleteDTO) {
        itemService.deleteItem(deleteDTO.getItemID(), deleteDTO.getQuantity());

        RecordEntity record = recordService.save(deleteDTO, RecordOperationEnum.EXCLUSAO);

        return record;
    }
}
