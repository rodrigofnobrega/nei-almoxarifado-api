package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.other.EmailForgotPasswordDTO;
import com.ufrn.nei.almoxarifadoapi.dto.other.RecoveryTokenValidateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserLoginDTO;
import com.ufrn.nei.almoxarifadoapi.entity.RecoveryTokenEntity;
import com.ufrn.nei.almoxarifadoapi.entity.UserEntity;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.infra.RestErrorMessage;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtToken;
import com.ufrn.nei.almoxarifadoapi.infra.jwt.JwtUserDetailsService;
import com.ufrn.nei.almoxarifadoapi.infra.mail.MailService;
import com.ufrn.nei.almoxarifadoapi.repository.RecoveryTokenRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

import com.ufrn.nei.almoxarifadoapi.service.UserService;

@Tag(name = "Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
  @Autowired
  private JwtUserDetailsService detailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private MailService mailService;

  @Autowired
  private RecoveryTokenRepository recoveryTokenRepository;

  @Operation(summary = "Autenticar na API", description = "Recurso de autenticação na API", responses = {
      @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um Bearer Token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtToken.class))),
      @ApiResponse(responseCode = "400", description = "Credenciais inválidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class))),
      @ApiResponse(responseCode = "422", description = "Campo(s) Inválido(s)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorMessage.class)))
  })
  @PostMapping
  public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO login, HttpServletRequest request) {
    log.info("Processo de autenticação pelo login {}", login.getEmail());

    try {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          login.getEmail(), login.getPassword());

      authenticationManager.authenticate(authenticationToken);

      JwtToken token = detailsService.getTokenAuthenticated(login.getEmail());

      return ResponseEntity.status(HttpStatus.OK).body(token);
    } catch (AuthenticationException ex) {
      log.warn("Bad credentials from username {}", login.getEmail());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
  }

  @PostMapping("/forgotPassword")
  public ResponseEntity<?> forgotPassword(@RequestBody @Valid EmailForgotPasswordDTO emailDTO,
      HttpServletRequest request) {
    try {
      String email = emailDTO.getEmail();
      UserEntity user = userService.findByEmail(email);

      Optional<RecoveryTokenEntity> tokenEntity = userService.getRecoveryTokenUser(user.getId());

      String recoveryToken = UUID.randomUUID().toString().substring(0, 6);

      RecoveryTokenEntity token = tokenEntity.isEmpty() ? null : tokenEntity.get();

      if (token == null) {
        token = userService.createRecoveryTokenUser(user, recoveryToken);
      } else if (token.isUsed() || isRecoveryTokenExpired(token)) {
        userService.updateRecoveryTokenUser(token, recoveryToken);
      }

      mailService.sendMailForgotPassword(email, token.getToken());

      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
  }

  @PostMapping("/validateRecoveryToken")
  public ResponseEntity<?> validateRecoveryToken(@RequestBody @Valid RecoveryTokenValidateDTO tokenDTO,
      HttpServletRequest request) {
    try {
      String token = tokenDTO.getToken();
      RecoveryTokenEntity recoveryToken = recoveryTokenRepository.findByToken(token).orElseThrow(
          () -> new EntityNotFoundException("Código digitado não existe."));

      if (recoveryToken.isUsed() || isRecoveryTokenExpired(recoveryToken)) {
        throw new Exception("Código expirado.");
      }

      recoveryToken.setUsed(true);
      recoveryToken.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
      recoveryTokenRepository.save(recoveryToken);

      return ResponseEntity.status(HttpStatus.OK).body(true);

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
  }

  private boolean isRecoveryTokenExpired(RecoveryTokenEntity token) {
    return Timestamp.valueOf(LocalDateTime.now()).after(token.getValidUntil());
  }
}
