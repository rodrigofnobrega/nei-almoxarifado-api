package com.ufrn.nei.almoxarifadoapi.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDetailsDTO {
    public Long id;
    public String name;
    public Long itemTagging;
    public boolean available;
}
