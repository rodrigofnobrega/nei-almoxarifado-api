package com.ufrn.nei.almoxarifadoapi.repository;

import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
