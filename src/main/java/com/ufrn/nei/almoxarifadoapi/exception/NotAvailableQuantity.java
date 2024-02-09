package com.ufrn.nei.almoxarifadoapi.exception;

public class NotAvailableQuantity extends RuntimeException {
    public NotAvailableQuantity() {
        super("Não há quantidades disponíveis suficientes para este objeto.");
    }

    public NotAvailableQuantity(String message) {
        super(message);
    }
}
