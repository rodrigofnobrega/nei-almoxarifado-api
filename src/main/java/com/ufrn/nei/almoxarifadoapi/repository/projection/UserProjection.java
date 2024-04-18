package com.ufrn.nei.almoxarifadoapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserProjection {
    Long getId();
    String getName();
    String getEmail();
    Boolean getActive();
    @JsonIgnore
    RoleEntity getRole();
    interface RoleEntity {
        String getRole();
    }

    @JsonProperty("role")
    default String getRoleWithoutPrefix() {
        return getRole().getRole().substring("ROLE_".length());
    }
}
