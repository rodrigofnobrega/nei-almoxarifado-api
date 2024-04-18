package com.ufrn.nei.almoxarifadoapi.repository;

import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RoleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT r FROM RoleEntity r")
    Page<RoleProjection> findAllPageable(Pageable pageable);
}
