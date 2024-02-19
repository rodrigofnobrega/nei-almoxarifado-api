package com.ufrn.nei.almoxarifadoapi.dto.other;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuantityUpdateDTO {
    @Positive
    private Integer items;
    @NotNull
    private Boolean toLend;
}