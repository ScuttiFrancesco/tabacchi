package it.tabacchi.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Optional.of(userDetails.getUsername()); // Email dell'utente
        }
        
        // Fallback per operazioni di sistema o test
        return Optional.of("SYSTEM");
    }
}
