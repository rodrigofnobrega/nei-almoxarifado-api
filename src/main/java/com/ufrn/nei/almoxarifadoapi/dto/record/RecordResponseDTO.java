package com.ufrn.nei.almoxarifadoapi.dto.record;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemRecordDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserRecordDTO;
import com.ufrn.nei.almoxarifadoapi.enums.RecordOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponseDTO {
    private Long id;
    private UserRecordDTO user;
    private ItemRecordDTO item;
    private Integer quantity;
    private RecordOperationEnum operation;
    private String data;
}
