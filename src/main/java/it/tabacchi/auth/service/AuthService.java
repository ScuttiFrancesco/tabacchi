package it.tabacchi.auth.service;

import it.tabacchi.auth.RefreshToken;
import it.tabacchi.auth.dto.*;
import it.tabacchi.enums.auth.MfaType;
import it.tabacchi.exception.InvalidRefreshTokenException;
import it.tabacchi.security.JwtUtil;
import it.tabacchi.user.User;
import it.tabacchi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final PasswordService passwordService;

    @Autowired
    public AuthService(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil,
                      RefreshTokenService refreshTokenService,
                      AuthenticationManager authenticationManager,
                      EmailService emailService,
                      PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    public AuthResponse authenticate(AuthRequest request) {
        String normalizedEmail = request.getEmail().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenziali non valide");
        }
        // Prepariamo la risposta di sfida (MFA)
        AuthResponse response = new AuthResponse();
        response.setRequiresVerification(true);
        response.setIsTemporaryPassword(user.getIsTemporaryPassword());

        // Determiniamo i metodi disponibili
        List<MfaType> methods = new ArrayList<>();
        methods.add(MfaType.EMAIL); // Email sempre disponibile
        if (user.getMfaSecret() != null && user.getMfaEnabled()) {
            methods.add(MfaType.AUTHENTICATOR);
        }
        response.setAvailableMfaMethods(methods);
        // Se l'unico metodo è EMAIL, inviamo subito il codice per comodità
        if (methods.size() == 1) {
            sendEmailVerification(user);
        }
        return response;
    }

    // Metodo per inviare/re-inviare il codice email (chiamabile anche da un endpoint dedicato)
    public void sendEmailVerification(User user) {
        String verificationCode = passwordService.generateVerificationCode();
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiry(Instant.now().plusSeconds(600));
        userRepository.save(user);

       
        emailService.sendVerificationCode(user.getEmail(), user.getNome(), verificationCode);
    }

    private AuthResponse createFullAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // Prepariamo la lista dei metodi per il frontend
        List<MfaType> methods = new ArrayList<>();
        methods.add(MfaType.EMAIL);
        if (user.getMfaSecret() != null && user.getMfaEnabled()) {
            methods.add(MfaType.AUTHENTICATOR);
        }

        AuthResponse response = new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                jwtUtil.getAccessTokenExpiry(),
                refreshToken.getExpiryDate()
        );

        // Impostiamo i metadati MFA
        response.setRequiresVerification(false); // Qui è FALSE perché abbiamo finito
        response.setAvailableMfaMethods(methods);
        response.setIsTemporaryPassword(user.getIsTemporaryPassword());

        return response;
    }

    public void changePassword(ChangePasswordRequest request, String email) {
        String normalizedEmail = email.toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Password attuale non corretta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setIsTemporaryPassword(false);
        userRepository.save(user);
    }

    public void resetPassword(ResetPasswordRequest request) {
        String normalizedEmail = request.getEmail().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        String newPassword = passwordService.generateSecurePassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setIsTemporaryPassword(true);
        userRepository.save(user);

        emailService.sendTemporaryPassword(user.getEmail(), user.getNome(), newPassword);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request, String authorizationHeader) {
        String refreshTokenValue = extractRefreshToken(request, authorizationHeader);

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenValue)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token non valido"));

        User user = refreshToken.getUser();
        String accessToken = jwtUtil.generateAccessToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                jwtUtil.getAccessTokenExpiry(),
                refreshToken.getExpiryDate()
        );
    }

    public void logout(RefreshTokenRequest request, String authorizationHeader) {
        refreshTokenService.deleteByToken(extractRefreshToken(request, authorizationHeader));
    }

    private String extractRefreshToken(RefreshTokenRequest request, String authorizationHeader) {
        if (request != null && StringUtils.hasText(request.getRefreshToken())) {
            return request.getRefreshToken();
        }

        if (StringUtils.hasText(authorizationHeader)) {
            RefreshTokenRequest fallbackRequest = new RefreshTokenRequest();
            fallbackRequest.setRefreshToken(authorizationHeader);

            if (StringUtils.hasText(fallbackRequest.getRefreshToken())) {
                return fallbackRequest.getRefreshToken();
            }
        }

        throw new InvalidRefreshTokenException("Refresh token è obbligatorio");
    }
}
       

