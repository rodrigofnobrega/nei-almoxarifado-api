package com.ufrn.nei.almoxarifadoapi.dto.mapper;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemRecordDTO;
import com.ufrn.nei.almoxarifadoapi.dto.record.RecordResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.role.RoleResponseDto;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class RecordMapper {
    public static RecordResponseDTO toResponseDTO(RecordEntity recordEntity) {
        ModelMapper mapper = new ModelMapper();
        RecordResponseDTO recordResponseDTO = mapper.map(recordEntity, RecordResponseDTO.class);

        recordResponseDTO.setUser(UserMapper.toRecordDTO(recordEntity.getUser()));
        recordResponseDTO.setItem(mapper.map(recordEntity.getItem(), ItemRecordDTO.class));

        return recordResponseDTO;
    }

    public static Page<RecordResponseDTO> toPageResponseDTO(Page<RecordEntity> data) {
        List<RecordResponseDTO> dtos = data.getContent().stream()
                .map(RecordMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, data.getPageable(), data.getTotalElements());
    }
}
