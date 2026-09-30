package com.codeMentra.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.frontend.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(String toEmail, String username, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify your CodeMentra account");
        message.setText(
                "Hi " + username + ",\n\n" +
                "Click the link below to verify your email and activate your account:\n\n" +
                baseUrl + "/api/auth/verify?token=" + token + "\n\n" +
                "If you didn't create this account, ignore this email."
        );
        mailSender.send(message);
    }
}
