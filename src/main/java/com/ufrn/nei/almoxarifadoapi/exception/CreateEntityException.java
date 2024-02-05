package com.ufrn.nei.almoxarifadoapi.exception;

public class CreateEntityException extends RuntimeException {
    public CreateEntityException() {
        super("Erro na criação da entidade");
    }

    public CreateEntityException(String message) {
        super(message);
    }
}
