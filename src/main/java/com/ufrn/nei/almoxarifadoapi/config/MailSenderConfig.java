package com.ufrn.nei.almoxarifadoapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class MailSenderConfig {
    @Value("${spring.mail.username}")
    private String sender;

    @Bean
    SimpleMailMessage configSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        return simpleMailMessage;
    }
}
