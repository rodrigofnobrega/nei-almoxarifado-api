package com.ufrn.nei.almoxarifadoapi.exception;

public class DeleteErrorException extends RuntimeException {
    public DeleteErrorException() {
        super("Erro ao deletar usuário");
    }

    public DeleteErrorException(String message) {
        super(message);
    }
}
