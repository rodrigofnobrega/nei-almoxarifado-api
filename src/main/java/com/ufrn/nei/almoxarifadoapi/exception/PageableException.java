package com.ufrn.nei.almoxarifadoapi.exception;

public class PageableException extends RuntimeException {
    public PageableException() {
        super("Erro ao definir paginação");
    }

    public PageableException(String message) {
        super(message);
    }
}
