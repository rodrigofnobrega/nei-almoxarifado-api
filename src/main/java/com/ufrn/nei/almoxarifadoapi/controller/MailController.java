package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.infra.mail.MailSendDTO;
import com.ufrn.nei.almoxarifadoapi.infra.mail.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/mails")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> sendEmail(@RequestBody @Valid MailSendDTO  mailSend) throws ExecutionException, InterruptedException {
        mailService.buildMail(mailSend);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
