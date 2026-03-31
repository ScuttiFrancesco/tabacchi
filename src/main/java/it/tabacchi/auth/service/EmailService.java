package it.tabacchi.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.mail.enabled", havingValue = "true", matchIfMissing = false)
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    public void sendTemporaryPassword(String toEmail, String name, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Credenziali di accesso - TABACCHI Management System");
        message.setText(String.format(
            "Ciao %s,\n\n" +
            "Le tue credenziali di accesso sono:\n" +
            "Email: %s\n" +
            "Password temporanea: %s\n\n" +
            "IMPORTANTE: Al primo accesso dovrai cambiare la password.\n\n" +
            "Saluti,\n" +
            "Team FNM",
            name, toEmail, temporaryPassword
        ));
        
        mailSender.send(message);
    }
    
    public void sendVerificationCode(String toEmail, String name, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Codice di verifica - TABACCHI Management System");
        message.setText(String.format(
            "Ciao %s,\n\n" +
            "Il tuo codice di verifica è: %s\n\n" +
            "Il codice è valido per 10 minuti.\n\n" +
            "Se non hai richiesto questo accesso, ignora questa email.\n\n" +
            "Saluti,\n" +
            "Team FNM",
            name, verificationCode
        ));
        
        mailSender.send(message);
    }
}
