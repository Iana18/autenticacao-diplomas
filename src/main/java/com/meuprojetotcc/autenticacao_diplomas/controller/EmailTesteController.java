package com.meuprojetotcc.autenticacao_diplomas.controller;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTesteController {

    private final JavaMailSender mailSender;

    public EmailTesteController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/teste-email")
    public String enviarEmailTeste() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("lianasanca29@gmail.com"); // seu email
            message.setSubject("Teste de envio de e-mail Spring Boot");
            message.setText("Olá! Este é um teste de envio de e-mail usando Spring Boot.");

            mailSender.send(message);

            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar e-mail: " + e.getMessage();
        }
    }
}
