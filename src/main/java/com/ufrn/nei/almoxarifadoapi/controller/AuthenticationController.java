package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.user.UserLoginDTO;
import com.ufrn.nei.almoxarifadoapi.infra.RestErrorMessage;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtToken;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private JwtUserDetailsService detailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO login, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", login.getEmail());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(login.getEmail());

            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad credentials from username {}", login.getEmail());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
