package com.ufrn.nei.almoxarifadoapi.infra.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthenticationContext {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Long getId() {
        JwtUserDetails jwtUserDetails = validatePrincipal();

        if (jwtUserDetails != null) {
            return jwtUserDetails.getId();
        }

        return null;
    }

    public static String getEmail() {
        JwtUserDetails jwtUserDetails = validatePrincipal();

        if (jwtUserDetails != null) {
            return jwtUserDetails.getUsername();
        }

        return null;
    }

    public static JwtUserDetails validatePrincipal() {
        Authentication authentication = getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof JwtUserDetails) {
            return (JwtUserDetails) principal;
        }

        return null;
    }
}
