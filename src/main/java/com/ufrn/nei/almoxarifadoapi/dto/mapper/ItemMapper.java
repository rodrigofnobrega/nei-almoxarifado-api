package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemDetailsDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemUpdateDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;

public class ItemMapper {

    public static ItemDetailsDTO toDetailsDTO(ItemEntity itemEntity) {
        return new ModelMapper().map(itemEntity, ItemDetailsDTO.class);
    }

    public static List<ItemDetailsDTO> toListDetailsDTO(List<ItemEntity> items) {
        return items.stream().map(i -> toDetailsDTO(i)).collect(Collectors.toList());
    }

    public static ItemEntity toItem(ItemCreateDTO itemDTO) {
        return new ModelMapper().map(itemDTO, ItemEntity.class);
    }

    public static ItemEntity toItem(ItemUpdateDTO itemDTO) {
        return new ModelMapper().map(itemDTO, ItemEntity.class);
    }
}
