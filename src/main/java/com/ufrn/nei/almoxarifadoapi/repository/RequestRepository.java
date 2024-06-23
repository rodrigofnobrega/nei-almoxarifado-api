package com.ufrn.nei.almoxarifadoapi.repository;

import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RequestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    @Query("SELECT r FROM RequestEntity r")
    Page<RequestProjection> findAllPageable(Pageable pageable);

    @Query("SELECT r FROM RequestEntity r WHERE (r.status = :status)")
    Page<RequestProjection> findByStatus(RequestStatusEnum status, Pageable pageable);

    @Query("SELECT r FROM RequestEntity r WHERE (r.status = :status AND r.user.id = :userId)")
    Page<RequestProjection> findByStatus(RequestStatusEnum status, Long userId, Pageable pageable);

    @Query("SELECT r FROM RequestEntity r WHERE (r.user.id = :id)")
    Page<RequestProjection> findByUserId(Long id, Pageable pageable);

    @Query("SELECT r FROM RequestEntity r WHERE (r.item.id = :id)")
    Page<RequestProjection> findByItemId(Long id, Pageable pageable);
}
