package com.ufrn.nei.almoxarifadoapi.dto.item;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {
    public Long id;
    public String name;
    public Long itemTagging;
    public int quantityAvailable;
    public int quantityLend;
    public Timestamp createdAt;
    public Timestamp updatedAt;
    public boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemResponseDTO that = (ItemResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(itemTagging, that.itemTagging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, itemTagging);
    }

    @Override
    public String toString() {
        return "ItemResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemTagging=" + itemTagging +
                '}';
    }
}
