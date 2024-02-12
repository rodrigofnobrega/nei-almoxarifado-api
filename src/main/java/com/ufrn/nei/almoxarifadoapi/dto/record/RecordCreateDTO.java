package com.ufrn.nei.almoxarifadoapi.dto.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordCreateDTO {
    private Long userID;
    private Long itemID;
}
