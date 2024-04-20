package com.ufrn.nei.almoxarifadoapi.repository;

import java.util.Optional;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.repository.projection.ItemProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Page<ItemProjection> findAllByAvailableTrue(Pageable pageable);

    Optional<ItemEntity> findBySipacCode(Long sipacCode);

    Optional<ItemEntity> findByName(String name);
}
