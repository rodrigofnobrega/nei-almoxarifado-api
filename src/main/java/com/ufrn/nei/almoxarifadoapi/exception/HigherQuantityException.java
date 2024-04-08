package com.ufrn.nei.almoxarifadoapi.exception;

public class HigherQuantityException extends RuntimeException {
    public HigherQuantityException() {
        super("Quantidade informada maior que a permitida");
    }

    public HigherQuantityException(String message) {
        super(message);
    }
}
