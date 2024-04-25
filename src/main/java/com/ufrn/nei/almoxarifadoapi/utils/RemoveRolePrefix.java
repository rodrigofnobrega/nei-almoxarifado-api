package com.ufrn.nei.almoxarifadoapi.utils;

import com.ufrn.nei.almoxarifadoapi.entity.RoleEntity;

public class RemoveRolePrefix {
    public static String getRoleWithoutPrefix(String role) {
        if (role.startsWith("ROLE_")) {
            return role.substring("ROLE_".length());
        }
        return role;
    }

    public static String getRoleWithoutPrefix(RoleEntity roleEntity) {
        return getRoleWithoutPrefix(roleEntity.getRole());
    }
}
