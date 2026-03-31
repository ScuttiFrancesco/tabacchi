package it.tabacchi.auth.service;

import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
public class PasswordService {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom random = new SecureRandom();
    
    public String generateSecurePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }
    
    public String generateVerificationCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
        return code.toString();
    }
}
