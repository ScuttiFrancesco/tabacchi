package it.tabacchi.auth.dto;

import java.time.Instant;
import java.util.List;


public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Instant accessTokenExpiry;
    private Instant refreshTokenExpiry;
    private boolean requiresVerification = false;
    private boolean isTemporaryPassword = false;

    public AuthResponse() {
    }
    public AuthResponse(String accessToken, String refreshToken, Instant accessTokenExpiry, Instant refreshTokenExpiry) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    // @formatter:off
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Instant getAccessTokenExpiry() { return accessTokenExpiry; }
    public void setAccessTokenExpiry(Instant accessTokenExpiry) { this.accessTokenExpiry = accessTokenExpiry; }
    public Instant getRefreshTokenExpiry() { return refreshTokenExpiry; }
    public void setRefreshTokenExpiry(Instant refreshTokenExpiry) { this.refreshTokenExpiry = refreshTokenExpiry; }
    public boolean isRequiresVerification() { return requiresVerification; }
    public void setRequiresVerification(boolean requiresVerification) { this.requiresVerification = requiresVerification; }
    public boolean isTemporaryPassword() { return isTemporaryPassword; }
    public void setIsTemporaryPassword(boolean isTemporaryPassword) { this.isTemporaryPassword = isTemporaryPassword; }
    //@formatter:on
}
      