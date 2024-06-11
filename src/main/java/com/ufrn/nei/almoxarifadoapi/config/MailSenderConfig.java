package com.ufrn.nei.almoxarifadoapi.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailSenderConfig {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Bean
    SimpleMailMessage configSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        return simpleMailMessage;
    }

    @Bean
    MimeMessage configMimeMessage() throws MessagingException {
        MimeMessage mime = javaMailSender.createMimeMessage();
        mime.setFrom(sender);
        return mime;
    }
}
