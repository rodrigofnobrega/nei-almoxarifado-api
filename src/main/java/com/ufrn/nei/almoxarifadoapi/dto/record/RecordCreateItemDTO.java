package com.ufrn.nei.almoxarifadoapi.dto.record;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordCreateItemDTO {
    @Positive
    private Long userID;
    @NotNull
    private ItemCreateDTO item;
}
