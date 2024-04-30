package com.ufrn.nei.almoxarifadoapi.infra.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    public Boolean sendMailUserCreatedAsync(String userEmail, String userName) {
        SimpleMailMessage message = mailTemplates.buildMailMessageUserCreated(userEmail, userName);

        CompletableFuture<Boolean> sender = buildSendEmailAsync(message);

        return sender.join();
    }

    @Async
    public Boolean sendMailRequestCreatedAsync(String userEmail, String userName,
                                               String itemName, Timestamp date, Integer itemQuantity) {
        SimpleMailMessage message =
                mailTemplates.buildMailMessageRequestCreated(userEmail, userName, itemName, date, itemQuantity);

        CompletableFuture<Boolean> sender = buildSendEmailAsync(message);

        return sender.join();
    }

    public Boolean buildMail(MailSendDTO mailSend) {
        SimpleMailMessage message = mailTemplates.buildMailGeneric(mailSend);

        CompletableFuture<Boolean> sender = buildSendEmailAsync(message);

        sender.join();

        return Boolean.TRUE;
    }

    @Async
    private CompletableFuture<Boolean> buildSendEmailAsync(SimpleMailMessage simpleMailMessage) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        try {
            javaMailSender.send(simpleMailMessage);

            log.info("Email enviado com sucesso para {}", simpleMailMessage.getTo());
            future.complete(Boolean.TRUE);
        } catch (MailSendException ex) {
            log.error("Erro ao enviar email para {}: {}", simpleMailMessage.getTo(), ex.getMessage());
            future.completeExceptionally(new MailSendException("Erro ao enviar email"));
        } catch (MailAuthenticationException ex) {
            log.error("Erro na autenticação do email - {}", ex.getMessage());
            future.completeExceptionally(new MailAuthenticationException("Erro na autenticação do email"));
        }

        return future;
    }
}
