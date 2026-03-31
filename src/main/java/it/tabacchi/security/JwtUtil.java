package it.tabacchi.security;

import java.security.Key;
import java.util.Date;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import it.tabacchi.user.User;

@Component
public class JwtUtil {

    @Value("${jwt.secret:mySecretKey}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 ore
    private long jwtExpirationMs;

    @Value("${jwt.refresh.expiration:604800000}") // 7 giorni
    private long refreshTokenExpirationMs;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("userId", user.getId())
                .claim("username", user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractClaims(token).get("userId").toString());
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public Instant getAccessTokenExpiry() {
        return Instant.now().plusMillis(jwtExpirationMs);
    }

    public Instant getRefreshTokenExpiry() {
        return Instant.now().plusMillis(refreshTokenExpirationMs);
    }

    // Metodo per estrarre il ruolo in modo sicuro (solo per uso interno)
    public String extractRoleSecure(String token) {
        try {
            Claims claims = extractClaims(token);
            return (String) claims.get("role");
        } catch (Exception e) {
            throw new SecurityException("Token non valido o compromesso");
        }
    }

    // Metodo per validare che l'utente abbia un ruolo specifico
    public boolean hasRole(String token, String requiredRole) {
        try {
            String userRole = extractRoleSecure(token);
            return userRole != null && userRole.equals(requiredRole);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public long getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }
}
