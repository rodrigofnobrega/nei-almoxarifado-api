package com.ufrn.nei.almoxarifadoapi.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RecuperacaoTokens")
public class RecoveryTokenEntity {
  @Id
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id_usuario")
  private UserEntity user;

  @Column(name = "token")
  private String token;

  @Column(name = "usado")
  private boolean used = false;

  @Column(name = "criado_em")
  private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

  @Column(name = "atualizado_em")
  private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

  @Column(name = "valido_ate")
  private Timestamp validUntil = Timestamp.valueOf(LocalDateTime.now().plusMinutes(30L));

  public RecoveryTokenEntity(UserEntity user, String token, boolean isUsed) {
    this.user = user;
    this.token = token;
    used = isUsed;
  }
}
