package com.ufrn.nei.almoxarifadoapi.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Configuration
public class MimeMessageSenderConfig {
    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Bean
    MimeMessage configMimeMessage() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", sender);
        props.put("mail.smtp.from", sender);

        System.out.println("FROM: " + sender);
        System.out.println("props: " + props);

        Session session = Session.getInstance(props);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        return message;
    }
}
