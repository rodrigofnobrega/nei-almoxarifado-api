package com.ufrn.nei.almoxarifadoapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
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
}
