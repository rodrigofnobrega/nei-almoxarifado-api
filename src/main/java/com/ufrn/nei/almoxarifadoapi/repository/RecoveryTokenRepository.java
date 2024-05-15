package com.ufrn.nei.almoxarifadoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufrn.nei.almoxarifadoapi.entity.RecoveryTokenEntity;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryTokenEntity, Long> {
  Optional<RecoveryTokenEntity> findByToken(String token);
}
