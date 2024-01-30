package com.ufrn.nei.almoxarifadoapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Itens")
public class ItemEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = false, length = 255)
    private String name;

    @Column(name = "tombamento", nullable = false, unique = true)
    private Long itemTagging;

    @Column(name = "disponivel", nullable = false, unique = false)
    private Boolean available = Boolean.TRUE;

    @OneToMany(mappedBy = "item")
    private List<RecordEntity> records;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(itemTagging, that.itemTagging) && Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, itemTagging, available);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemTagging=" + itemTagging +
                ", available=" + available +
                '}';
    }
}
