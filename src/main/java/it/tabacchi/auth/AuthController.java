package it.tabacchi.auth;

import it.tabacchi.auth.dto.*;
import it.tabacchi.auth.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Auth", description = "API per l'autenticazione e la gestione delle sessioni utente")
public class AuthController {

    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Login utente",
        description = "Autentica un utente e restituisce i token di accesso e refresh"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Refresh token",
        description = "Genera un nuovo token di accesso utilizzando un token di refresh valido"
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
            @RequestBody(required = false) RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request, authorizationHeader);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Logout utente",
        description = "Effettua il logout invalidando il token di refresh"
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
            @RequestBody(required = false) RefreshTokenRequest request) {
        authService.logout(request, authorizationHeader);
        return ResponseEntity.ok("Logout effettuato con successo");
    }

    @Operation(
        summary = "Cambia password",
        description = "Permette all'utente autenticato di cambiare la propria password"
    )
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        authService.changePassword(request, email);
        return ResponseEntity.ok("Password cambiata con successo");
    }

    @Operation(
        summary = "Reset password",
        description = "Inizia il processo di reset della password inviando un'email con la password temporanea"
    )
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.status(HttpStatus.OK).body("Richiesta di reset password inviata con successo. Controlla la tua email per le istruzioni.");
    }
}