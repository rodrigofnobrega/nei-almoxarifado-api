package com.ufrn.nei.almoxarifadoapi.repository.projection.component;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FormatDateComponent {
    public static String formatDate(Timestamp date) {
        LocalDateTime dateTime = date.toLocalDateTime();
        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        return formattedDateTime;
    }
}