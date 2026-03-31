package it.tabacchi.shared;

import it.tabacchi.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import it.tabacchi.user.User;
import it.tabacchi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Component
public class SharedMethods {

    private final UserRepository userRepository;

    public SharedMethods(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Ottiene l'utente corrente dal SecurityContext
     */
    public User getUserFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Se l'autenticazione è nulla o non è il nostro Principal, lanciamo errore
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new EntityNotFoundException("Utente non autenticato nel contesto");
        }

        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

        // Usiamo l'ID che abbiamo già nel Principal!
        // IMPORTANTE: Assicurati che findById nel repository abbia l' @EntityGraph
        return userRepository.findById(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con ID: " + principal.getId()));
    }

    /**
     * Ottiene l'email dell'utente corrente dal SecurityContext
     */
    public String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    /**
     * Verifica se l'utente è autenticato
     */
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}
