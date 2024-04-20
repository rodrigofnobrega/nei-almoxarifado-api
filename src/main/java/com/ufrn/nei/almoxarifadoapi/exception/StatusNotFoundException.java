package com.ufrn.nei.almoxarifadoapi.exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException() {
    }

    public StatusNotFoundException(String message) {
        super(message);
    }
}
