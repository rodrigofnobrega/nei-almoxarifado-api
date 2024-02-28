package com.ufrn.nei.almoxarifadoapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "itens")
public class ItemEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = false, length = 255)
    private String name;

    @Column(name = "tombamento", nullable = false, unique = true)
    private Long itemTagging;

    @Column(name = "quantidade_disponiveis", nullable = false)
    private int quantityAvailable;

    @Column(name = "quantidade_emprestados", nullable = false)
    private int quantityLend;

    @Column(name = "criado_em", nullable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "atualizado_em", nullable = false)
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "ativo", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "item")
    private List<RecordEntity> records;

    public ItemEntity(long id, String name, Long tagging) {
        this.id = id;
        this.name = name;
        this.itemTagging = tagging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(itemTagging, that.itemTagging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, itemTagging);
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemTagging=" + itemTagging +
                '}';
    }
}
