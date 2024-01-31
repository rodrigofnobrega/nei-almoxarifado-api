package com.ufrn.nei.almoxarifadoapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Registros")
public class RecordEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private ItemEntity item;

    private Timestamp data = new Timestamp(System.currentTimeMillis());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordEntity that = (RecordEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(item, that.item) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, item, data);
    }

    @Override
    public String toString() {
        return "RecordEntity{" +
                "id=" + id +
                ", user=" + user +
                ", item=" + item +
                ", data=" + data +
                '}';
    }
}
