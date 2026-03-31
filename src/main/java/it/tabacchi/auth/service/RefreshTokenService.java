package it.tabacchi.auth.service;

import java.time.Instant;
import java.util.Optional;

import it.tabacchi.auth.RefreshTokenRepository;
import it.tabacchi.auth.RefreshToken;
import it.tabacchi.exception.ExpiredRefreshTokenException;
import it.tabacchi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.tabacchi.user.User;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, 
                              JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtil = jwtUtil;
    }

    public RefreshToken createRefreshToken(User user) {
        // Rimuovi eventuali refresh token esistenti per questo utente
        refreshTokenRepository.deleteByUser(user);
        
        // Flush per assicurarsi che la cancellazione sia completata
        refreshTokenRepository.flush();
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(jwtUtil.generateRefreshToken(user));
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtUtil.getRefreshTokenExpirationMs()));
        
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException("Refresh token scaduto. Effettua nuovamente il login");
        }
        return token;
    }

    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
