package com.ufrn.nei.almoxarifadoapi.dto.record;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemRecordDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserRecordDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordResponseDTO {
    private Long id;
    private UserRecordDTO user;
    private ItemRecordDTO item;
}
