package it.tabacchi.auth.dto;

import jakarta.validation.constraints.NotBlank;


public class ResetPasswordRequest {
    
    @NotBlank(message = "La email è obbligatoria")
    private String email;

    public ResetPasswordRequest() {}
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    
}
