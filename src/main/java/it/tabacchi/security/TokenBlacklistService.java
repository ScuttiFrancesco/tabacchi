package it.tabacchi.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // token -> expiry time
    private final ConcurrentHashMap<String, Instant> blacklist = new ConcurrentHashMap<>();

    public void revokeToken(String token, Instant expiresAt) {
        if (token != null && !token.isBlank()) {
            blacklist.put(token, expiresAt);
        }
    }

    public boolean isRevoked(String token) {
        Instant expiry = blacklist.get(token);
        if (expiry == null) return false;
        // Se il token è già scaduto naturalmente, lo rimuoviamo e non serve bloccarlo
        if (Instant.now().isAfter(expiry)) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }

    // Pulizia automatica ogni ora dei token già scaduti
    @Scheduled(fixedRate = 3_600_000)
    public void cleanup() {
        Instant now = Instant.now();
        blacklist.entrySet().removeIf(entry -> now.isAfter(entry.getValue()));
    }
}
