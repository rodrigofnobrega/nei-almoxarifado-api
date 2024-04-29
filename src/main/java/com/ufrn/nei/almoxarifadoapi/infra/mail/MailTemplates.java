package com.ufrn.nei.almoxarifadoapi.infra.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

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

}
