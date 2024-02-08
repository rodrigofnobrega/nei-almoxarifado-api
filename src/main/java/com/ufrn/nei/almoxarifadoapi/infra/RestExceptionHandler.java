package com.ufrn.nei.almoxarifadoapi.infra;

import com.ufrn.nei.almoxarifadoapi.exception.CreateEntityException;
import com.ufrn.nei.almoxarifadoapi.exception.DeleteErrorException;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.exception.PasswordInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleItemNotFoundException(EntityNotFoundException exception,
                                                                  HttpServletRequest request) {
        log.info("API ERROR - ", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(CreateEntityException.class)
    public ResponseEntity<RestErrorMessage> handleCreateEntityException(CreateEntityException exception,
                                                                        HttpServletRequest request) {
        log.info("API ERROR - ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                         HttpServletRequest request) {
        log.info("API ERROR - ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, "Campo(s) invalido(s)"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                            HttpServletRequest request) {
        log.info("API ERROR - ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, "Campo(s) invalido(s)"));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<RestErrorMessage> handlePasswordInvalidException(PasswordInvalidException exception,
                                                                           HttpServletRequest request) {
        log.info("API ERROR - ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(DeleteErrorException.class)
    public ResponseEntity<RestErrorMessage> handleDeleteErrorException(DeleteErrorException exception,
                                                                       HttpServletRequest request) {
        log.info("API ERROR - ", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
}
