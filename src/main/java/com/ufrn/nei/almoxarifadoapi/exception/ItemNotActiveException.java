package com.ufrn.nei.almoxarifadoapi.exception;

public class ItemNotActiveException extends RuntimeException {
    public ItemNotActiveException() {
        super("O item já foi excluido anteriormente");
    }

    public ItemNotActiveException(String message) {
        super(message);
    }
}
