package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.utils.RemoveRolePrefix;

import java.util.List;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();
    Boolean getActive();
    @JsonIgnore
    RoleEntity getRole();
    @JsonIgnore
    List<RecordEntity> getRecords();

    @JsonProperty("role")
    default String removeRolePrefix() {
        return RemoveRolePrefix.getRoleWithoutPrefix(getRole());
    }

    @JsonProperty("existRecord")
    default Boolean existRecord() {
        if (getRecords().isEmpty()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}