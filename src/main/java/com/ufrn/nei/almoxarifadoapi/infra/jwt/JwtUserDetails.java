package com.ufrn.nei.almoxarifadoapi.infra.jwt;

import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {
    private UserEntity user;

    public JwtUserDetails(UserEntity user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().getRole()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().getRole();
    }
}