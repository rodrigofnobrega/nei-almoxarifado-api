package com.ufrn.nei.almoxarifadoapi.repository.projection.component;

import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RemoveRolePrefixComponent {
    public static String getRoleWithoutPrefix(RoleEntity role) {
        return role.getRole().substring("ROLE_".length());
    }

    public static String getRoleWithoutPrefix(String role) {
        return role.substring("ROLE_".length());
    }
}