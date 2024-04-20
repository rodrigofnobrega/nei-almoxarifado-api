package com.ufrn.nei.almoxarifadoapi.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("O usuário está tentando acessar dados não autorizados");
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
