package com.ufrn.nei.almoxarifadoapi.infra.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailTemplates mailTemplates;

    @Async
    public void sendMailUserCreatedAsync(String userEmail, String userName) {
        SimpleMailMessage message = mailTemplates.buildMailMessageUserCreated(userEmail, userName);

        buildSendEmailAsync(message);
    }

    @Async
    public void sendMailRequestCreatedAsync(String userEmail, String userName,
                                               String itemName, Timestamp date, Long itemQuantity) {
        SimpleMailMessage message =
                mailTemplates.buildMailMessageRequestCreated(userEmail, userName, itemName, date, itemQuantity);

        buildSendEmailAsync(message);
    }

    @Async
    public void sendMailRequestAcceptedAsync(String userEmail, String userName,
                                               String itemName, Timestamp date, Long itemQuantity) {
        SimpleMailMessage message =
                mailTemplates.buildMailMessageRequestAccepted(userEmail, userName, itemName, date, itemQuantity);

        buildSendEmailAsync(message);
    }

    @Async
    public void sendMailRequestDeniedAsync(String userEmail, String userName,
                                             String itemName, Timestamp date, Long itemQuantity) {
        SimpleMailMessage message =
                mailTemplates.buildMailMessageRequestDenied(userEmail, userName, itemName, date, itemQuantity);

        buildSendEmailAsync(message);
    }

    @Async
    public void sendMailRequestCanceledAsync(String userEmail, String userName,
                                           String itemName, Timestamp date, Long itemQuantity) {
        SimpleMailMessage message =
                mailTemplates.buildMailMessageRequestCanceled(userEmail, userName, itemName, date, itemQuantity);

        buildSendEmailAsync(message);
    }

    @Async
    public void sendMailForgotPassword(String userEmail, String token) throws MessagingException {
        try {
            MimeMessage mime = javaMailSender.createMimeMessage();
            mailTemplates.buildMailMessageForgotPassword(userEmail, token, mime);
            buildSendEmailAsync(mime);
        } catch (MessagingException e) {
          e.printStackTrace();
        }
    }

    @Async
    private void buildSendEmailAsync(MimeMessage mimeMessage) throws MessagingException {
        try {
            javaMailSender.send(mimeMessage);
            log.info("Email enviado com sucesso para {}", mimeMessage.getRecipients(Message.RecipientType.TO));
        } catch (MailAuthenticationException ex) {
            log.error("Erro na autenticação do email - {}", ex.getMessage());
        } catch (MailSendException ex) {
            log.error("Erro ao enviar email para {}: {}", mimeMessage.getRecipients(Message.RecipientType.TO), ex.getMessage());
        } catch (Exception ex) {
            log.error("Erro desconhecido ao enviar email para {}: {}", mimeMessage.getRecipients(Message.RecipientType.TO), ex.getMessage());
        }
    }

    @Async
    private void buildSendEmailAsync(SimpleMailMessage simpleMailMessage) {
        try {
            javaMailSender.send(simpleMailMessage);
            log.info("Email enviado com sucesso para {}", simpleMailMessage.getTo());
        } catch (MailAuthenticationException ex) {
            log.error("Erro na autenticação do email - {}", ex.getMessage());
        } catch (MailSendException ex) {
            log.error("Erro ao enviar email para {}: {}", simpleMailMessage.getTo(), ex.getMessage());
        } catch (Exception ex) {
            log.error("Erro desconhecido ao enviar email para {}: {}", simpleMailMessage.getTo(), ex.getMessage());
        }
    }
}
