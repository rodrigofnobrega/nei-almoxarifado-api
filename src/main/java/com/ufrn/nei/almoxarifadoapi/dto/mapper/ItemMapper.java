package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.modelmapper.ModelMapper;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ItemMapper {

    public static ItemResponseDTO toResponseDTO(ItemEntity itemEntity) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<ItemEntity, ItemResponseDTO>() {
            @Override
            protected void configure() {
                skip(destination.getLastRecord());
            }
        });

        ItemResponseDTO itemResponse = modelMapper.map(itemEntity, ItemResponseDTO.class);

        UserEntity user = itemEntity.getCreatedBy();
        UserResponseDTO userResponse = UserMapper.toResponseDTO(user);
        itemResponse.setCreatedBy(userResponse);

        RecordEntity record = itemEntity.getLastRecord();
        RecordResponseDTO recordResponseDTO = RecordMapper.toResponseDTO(record);
        itemResponse.setLastRecord(recordResponseDTO);

        return  itemResponse;
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
