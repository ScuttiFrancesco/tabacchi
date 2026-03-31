package it.tabacchi.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    
    @NotBlank(message = "La password attuale è obbligatoria")
    private String currentPassword;
    
    @NotBlank(message = "La nuova password è obbligatoria")
    @Size(min = 8, message = "La password deve essere di almeno 8 caratteri")
    @Pattern(
            regexp = ".*[A-Z].*",
            message = "La password deve contenere almeno una lettera maiuscola"
    )
    // Almeno un carattere speciale (incluso spazio, simboli comuni)
    @Pattern(
            regexp = ".*[@#$%^&+=!].*",
            message = "La password deve contenere almeno un carattere speciale (@#$%^&+=!)"
    )
    private String newPassword;
    
    public ChangePasswordRequest() {}
    
    // Getters and Setters
    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
