package com.ufrn.nei.almoxarifadoapi.exception;


public class ConflictUpdatePasswordException extends RuntimeException {
    public ConflictUpdatePasswordException() { 
        super("Um dos atributos deve estar vazio: código de recuperação ou senha atual");
    }
    public ConflictUpdatePasswordException(String message) { 
        super(message);
    }
}
