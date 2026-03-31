package it.tabacchi.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    
    @NotBlank(message = "Refresh token è obbligatorio")
    @JsonAlias({"token"})
    private String refreshToken;
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = normalize(refreshToken);
    }

    private String normalize(String refreshToken) {
        if (refreshToken == null) {
            return null;
        }

        String normalizedToken = refreshToken.trim();

        if (normalizedToken.startsWith("Bearer ")) {
            normalizedToken = normalizedToken.substring(7).trim();
        }

        if (normalizedToken.length() >= 2 && normalizedToken.startsWith("\"") && normalizedToken.endsWith("\"")) {
            normalizedToken = normalizedToken.substring(1, normalizedToken.length() - 1).trim();
        }

        return normalizedToken;
    }
}
