package com.ufrn.nei.almoxarifadoapi.controller.utils;

import com.ufrn.nei.almoxarifadoapi.exception.PageableException;

public class ValidatePagination {
    public static void validatePageParameters(int page, int size) {
        if (size == 0) {
            throw new PageableException("O tamanho não pode ser zero");
        } else if (page < 0) {
            throw new PageableException("O tamanho da página não pode ser negativo");
        }
    }

    public static void validateTotalPages(int page, int totalPages) {
        if (page >= totalPages) {
            throw new PageableException("Tamanho de páginas solicitadas maior que o total");
        }
    }
}
