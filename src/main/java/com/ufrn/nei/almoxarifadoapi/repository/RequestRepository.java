package com.ufrn.nei.almoxarifadoapi.repository;

import com.ufrn.nei.almoxarifadoapi.entity.RequestEntity;
import com.ufrn.nei.almoxarifadoapi.enums.RequestStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    @NonNull
    Page<RequestEntity> findAll(@NonNull Pageable pageable);

    Page<RequestEntity> findByStatus(RequestStatusEnum status, @NonNull Pageable pageable);
}
