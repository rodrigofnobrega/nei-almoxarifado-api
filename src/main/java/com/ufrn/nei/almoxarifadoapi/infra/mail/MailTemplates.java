package com.ufrn.nei.almoxarifadoapi.infra.mail;

import com.ufrn.nei.almoxarifadoapi.utils.RefactorDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MailTemplates {
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    public SimpleMailMessage buildMailMessageUserCreated(String userEmail, String userName) {
        String subject = "Sua conta foi criada com sucesso!";
        String text = String.format("Olá %s,\n\n"
                + "Seja bem-vindo ao nosso sistema de solicitação de itens do almoxarifado! Estamos muito felizes em tê-lo conosco.\n\n"
                + "Com este sistema, você terá acesso a uma variedade de itens disponíveis em nosso almoxarifado, tornando mais fácil e conveniente solicitar o que você precisa para suas atividades.\n\n"
                + "Aqui estão algumas coisas que você pode fazer com a sua nova conta:\n"
                + "- Explorar nosso catálogo de itens disponíveis.\n"
                + "- Criar solicitações para itens que você precisa.\n"
                + "- Acompanhar o status das suas solicitações.\n"
                + "- Receber notificações sobre o progresso das suas solicitações.\n\n"
                + "Estamos sempre trabalhando para melhorar nossa plataforma e proporcionar uma experiência mais eficiente para todos os nossos usuários.\n\n"
                + "Se precisar de ajuda ou tiver alguma dúvida, não hesite em entrar em contato conosco. Estamos aqui para ajudar!\n\n"
                + "Mais uma vez, obrigado por se juntar a nós. Esperamos que você aproveite ao máximo o nosso sistema de solicitação de itens do almoxarifado.\n\n"
                + "Atenciosamente,\n"
                + "Equipe do NEI", userName);

        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }
    public SimpleMailMessage buildMailGeneric(MailSendDTO mailSendDTO) {
        String subject = mailSendDTO.getSubject();
        String text = mailSendDTO.getText();
        simpleMailMessage.setTo(mailSendDTO.getSendTo());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public SimpleMailMessage buildMailMessageRequestCreated(String userEmail, String userName,
                                                            String itemName, Timestamp date, Long itemQuantity) {
        String formatDate = RefactorDate.refactorTimestamp(date);

        String subject = "Sua Solicitação foi criada com sucesso!";
        String text = String.format("Olá %s,\n\n"
                        + "Sua solicitação do item '%s' foi realizada com sucesso!\n\n"
                        + "Detalhes:\n"
                        + "- Item: %s\n"
                        + "- Quantidade: %d\n"
                        + "- Hora da Solicitação: %s\n\n"
                        + "Obrigado por utilizar nosso sistema.",
                userName, itemName, itemName, itemQuantity, formatDate);

        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public SimpleMailMessage buildMailMessageRequestAccepted(String userEmail, String userName,
                                                            String itemName, Timestamp date, Long itemQuantity) {
        String formatDate = RefactorDate.refactorTimestamp(date);

        String subject = "Sua solicitação foi aceita!";
        String text = String.format("Olá %s,\n\n"
                        + "Sua solicitação do item '%s' foi aceita!\n\n"
                        + "Detalhes:\n"
                        + "- Item: %s\n"
                        + "- Quantidade: %d\n"
                        + "- Hora da Aceitação: %s\n\n"
                        + "Obrigado por utilizar nosso sistema.",
                userName, itemName, itemName, itemQuantity, formatDate);

        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public SimpleMailMessage buildMailMessageRequestDenied(String userEmail, String userName,
                                                             String itemName, Timestamp date, Long itemQuantity) {
        String formatDate = RefactorDate.refactorTimestamp(date);

        String subject = "Sua solicitação foi recusada.";
        String text = String.format("Olá %s,\n\n"
                        + "Sua solicitação para o item '%s' foi recusada.\n\n"
                        + "Detalhes:\n"
                        + "- Item: %s\n"
                        + "- Quantidade: %d\n"
                        + "- Hora da Recusa: %s\n\n"
                        + "Por favor, entre em contato conosco para mais informações.\n\n"
                        + "Atenciosamente,\n"
                        + "Equipe do Almoxarifado",
                userName, itemName, itemName, itemQuantity, formatDate);

        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }

    public SimpleMailMessage buildMailMessageRequestCanceled(String userEmail, String userName,
                                                           String itemName, Timestamp date, Long itemQuantity) {
        String formatDate = RefactorDate.refactorTimestamp(date);

        String subject = "Confirmação de cancelamento.";
        String text = String.format("Olá %s,\n\n"
                        + "Sua solicitação para o item '%s' foi cancelada com sucesso.\n\n"
                        + "Detalhes:\n"
                        + "- Item: %s\n"
                        + "- Quantidade: %d\n"
                        + "- Hora do Cancelamento: %s\n\n"
                        + "Equipe do Almoxarifado.",
                userName, itemName, itemName, itemQuantity, formatDate);

        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        return simpleMailMessage;
    }
}
