package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import com.ufrn.nei.almoxarifadoapi.utils.RemoveRolePrefix;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();
    Boolean getActive();
    @JsonIgnore
    RoleEntity getRole();

    @JsonProperty("role")
    default String removeRolePrefix() {
        return RemoveRolePrefix.getRoleWithoutPrefix(getRole());
    }
}