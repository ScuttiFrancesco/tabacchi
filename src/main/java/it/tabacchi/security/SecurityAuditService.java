package it.tabacchi.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SecurityAuditService {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityAuditService.class);
    private static final Map<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_TIME = 900; // 15 minuti in secondi
    
    public void logSuccessfulLogin(String email, String ipAddress) {
        logger.info("Successful login for user: {} from IP: {}", email, ipAddress);
        loginAttempts.remove(email); // Reset tentativi dopo login riuscito
    }
    
    public void logFailedLogin(String email, String ipAddress) {
        logger.warn("Failed login attempt for user: {} from IP: {}", email, ipAddress);
        
        LoginAttempt attempt = loginAttempts.computeIfAbsent(email, k -> new LoginAttempt());
        attempt.incrementAttempts();
        
        if (attempt.getAttempts() >= MAX_ATTEMPTS) {
            attempt.setLockedUntil(Instant.now().plusSeconds(LOCKOUT_TIME));
            logger.error("Account locked for user: {} due to {} failed attempts", email, MAX_ATTEMPTS);
        }
    }
    
    public boolean isAccountLocked(String email) {
        LoginAttempt attempt = loginAttempts.get(email);
        if (attempt == null) return false;
        
        if (attempt.getLockedUntil() != null && attempt.getLockedUntil().isAfter(Instant.now())) {
            return true;
        }
        
        // Se il lockout è scaduto, resetta
        if (attempt.getLockedUntil() != null && attempt.getLockedUntil().isBefore(Instant.now())) {
            loginAttempts.remove(email);
        }
        
        return false;
    }
    
    public void logSuspiciousActivity(String email, String activity, String ipAddress) {
        logger.error("SUSPICIOUS ACTIVITY: {} for user: {} from IP: {}", activity, email, ipAddress);
    }
    
    private static class LoginAttempt {
        private int attempts = 0;
        private Instant lockedUntil;
        
        public void incrementAttempts() { this.attempts++; }
        public int getAttempts() { return attempts; }
        public Instant getLockedUntil() { return lockedUntil; }
        public void setLockedUntil(Instant lockedUntil) { this.lockedUntil = lockedUntil; }
    }
}
