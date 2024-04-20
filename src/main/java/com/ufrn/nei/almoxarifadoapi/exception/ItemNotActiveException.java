package com.ufrn.nei.almoxarifadoapi.exception;

public class ItemNotActiveException extends RuntimeException {
    public ItemNotActiveException() {
        super("O item não está disponível");
    }

    public ItemNotActiveException(String message) {
        super(message);
    }
}
