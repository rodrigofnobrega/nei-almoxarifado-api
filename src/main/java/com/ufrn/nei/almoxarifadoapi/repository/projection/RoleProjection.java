package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufrn.nei.almoxarifadoapi.utils.RemoveRolePrefix;

public interface RoleProjection {
    Long getId();
    @JsonIgnore
    String getRole();
    Boolean getActive();

    @JsonProperty("role")
    default String removeRolePrefix() {
        return RemoveRolePrefix.getRoleWithoutPrefix(getRole());
    }
}
