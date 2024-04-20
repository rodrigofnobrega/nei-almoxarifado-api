package com.ufrn.nei.almoxarifadoapi.repository;

import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.repository.projection.RecordProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {
    @Query("SELECT r FROM RecordEntity r")
    Page<RecordProjection> findAllPageable(Pageable pageable);

    @Query("SELECT r FROM RecordEntity r WHERE (r.id = :id)")
    Optional<RecordProjection> findByIdProjection(Long id);

    @Query("SELECT ue FROM RecordEntity ue WHERE " +
            "(ue.user.id = :id) OR " +
            "LOWER(ue.user.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(ue.user.email) LIKE LOWER(CONCAT('%', :email, '%')) OR " +
            "LOWER(ue.user.role.role) LIKE LOWER(CONCAT('%', :role, '%'))")
    Page<RecordProjection> findByUsers(Long id, String name, String email, String role, Pageable pageable);

    @Query("SELECT ue FROM RecordEntity ue WHERE " +
            "(ue.item.id = :id) OR " +
            "CAST(ue.item.sipacCode AS string) LIKE CONCAT('%', CAST(:sipacCode AS string), '%') OR " +
            "LOWER(ue.item.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<RecordProjection> findByItens(Long id, Long sipacCode, String name, Pageable pageable);
}
