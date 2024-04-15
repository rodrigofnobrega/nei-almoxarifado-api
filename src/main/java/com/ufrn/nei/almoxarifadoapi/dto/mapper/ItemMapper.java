package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import org.modelmapper.ModelMapper;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ItemMapper {

    public static ItemResponseDTO toResponseDTO(ItemEntity itemEntity) {
        return new ModelMapper().map(itemEntity, ItemResponseDTO.class);
    }

    public static Page<ItemResponseDTO> toPageResponseDTO(Page<ItemEntity> data) {
        List<ItemResponseDTO> dtos = data.getContent().stream()
                .map(ItemMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, data.getPageable(), data.getTotalElements());
    }

    public static ItemEntity toItem(ItemCreateDTO itemDTO) {
        return new ModelMapper().map(itemDTO, ItemEntity.class);
    }

    public static ItemEntity toItem(ItemResponseDTO itemDTO) {
        return new ModelMapper().map(itemDTO, ItemEntity.class);
    }

}
