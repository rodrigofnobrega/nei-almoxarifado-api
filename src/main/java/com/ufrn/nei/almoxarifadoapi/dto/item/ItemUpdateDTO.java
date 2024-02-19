package com.ufrn.nei.almoxarifadoapi.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateDTO {
    String name;
    @Positive
    Long itemTagging;
    @PositiveOrZero
    int quantityLend;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemUpdateDTO that = (ItemUpdateDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(itemTagging, that.itemTagging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, itemTagging);
    }

    @Override
    public String toString() {
        return "ItemUpdateDTO{" +
                "name='" + name + '\'' +
                ", itemTagging=" + itemTagging +
                '}';
    }
}
