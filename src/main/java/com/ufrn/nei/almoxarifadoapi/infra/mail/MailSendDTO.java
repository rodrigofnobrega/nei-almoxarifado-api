package com.ufrn.nei.almoxarifadoapi.infra.mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailSendDTO {
    @NotBlank
    @Email(message = "formato do e-mail invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.+-]+\\.[a-z]{2,}$")
    private String sendTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;
}
