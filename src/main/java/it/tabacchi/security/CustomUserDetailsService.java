package it.tabacchi.security;

import it.tabacchi.user.User;
import it.tabacchi.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // La normalizzazione in minuscolo è fondamentale per la coerenza
        String normalizedEmail = email.toLowerCase();

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Questo servizio viene usato da Spring Security DOPO il login,
        // per caricare i dettagli dell'utente per ogni richiesta autenticata con JWT.
        // La logica di business (es. controllo del restaurantId al login) non va qui,
        // ma nel AuthService, perché questa interfaccia non prevede parametri
        // aggiuntivi.

        return new UserPrincipal(user, authorities);
    }
}
