package com.ufrn.nei.almoxarifadoapi.exception;


public class InvalidRecoveryTokenException extends RuntimeException {
    public InvalidRecoveryTokenException() { 
        super("Código de recuperação de senha inválido");
    }
    public InvalidRecoveryTokenException(String message) { 
        super(message);
    }
}
