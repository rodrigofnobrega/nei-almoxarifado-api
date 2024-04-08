package com.ufrn.nei.almoxarifadoapi.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class RequestCreateDTO {
    @NotNull
    private Long itemID;
    @NotNull
    @Positive
    private Long quantity;
    @NotBlank
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestCreateDTO that = (RequestCreateDTO) o;
        return Objects.equals(itemID, that.itemID) && Objects.equals(quantity, that.quantity) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemID, quantity, description);
    }

    @Override
    public String toString() {
        return "RequestCreateDTO{" +
                ", itemID=" + itemID +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                '}';
    }
}
