package com.ufrn.nei.almoxarifadoapi.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class RefactorDate {
    public static String refactorTimestamp(Timestamp date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formatedDate = date.toLocalDateTime().format(formatter);

        return formatedDate;
    }
}
