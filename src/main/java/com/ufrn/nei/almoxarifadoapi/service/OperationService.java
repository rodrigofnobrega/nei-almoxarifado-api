package com.ufrn.nei.almoxarifadoapi.service;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.ItemMapper;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordCreateItemDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import com.ufrn.nei.almoxarifadoapi.exception.OperationErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private RecordService recordService;

    public RecordEntity toLend(RecordCreateDTO createDTO) {
        itemService.sumLendQuantity(createDTO.getItemID(), createDTO.getQuantity());

        return recordService.save(createDTO, RecordOperationEnum.EMPRESTIMO);
    }


    public RecordEntity toReturn(RecordCreateDTO createDTO) {
        List<RecordEntity> records = recordService.findByUsers(createDTO.getUserID(), null, null, null);

        int lendQuantity = 0;

        for (RecordEntity record : records) {
            if (record.getItem().getId() == createDTO.getItemID()) {
                if (record.getOperationEnum() == RecordOperationEnum.EMPRESTIMO) {
                    lendQuantity += record.getQuantity();
                } else if (record.getOperationEnum() == RecordOperationEnum.DEVOLUCAO) {
                    lendQuantity -= record.getQuantity();
                }
            }
        }

        if (lendQuantity <= 0) {
            String itemName = itemService.findById(createDTO.getItemID()).getName();
            throw new OperationErrorException("O usuário não possui empréstimo ativo com o item " + itemName);
        } else if (createDTO.getQuantity() > lendQuantity) {
            throw new OperationErrorException("O usuário está tentando devolver itens além da quantidade emprestada");
        }

        itemService.subtractLendQuantity(createDTO.getItemID(), createDTO.getQuantity());

        return recordService.save(createDTO, RecordOperationEnum.DEVOLUCAO);
    }

    public RecordEntity toRegister(RecordCreateItemDTO createDTO) {
        ItemResponseDTO item = itemService.createItem(createDTO.getItem());

        return recordService.save(createDTO, ItemMapper.toItem(item), RecordOperationEnum.CADASTRO);
    }

    public RecordEntity toDelete(RecordCreateDTO createDTO) {
        itemService.deleteItem(createDTO.getItemID(), createDTO.getQuantity());

        return recordService.save(createDTO, RecordOperationEnum.EXCLUSAO);
    }
}
