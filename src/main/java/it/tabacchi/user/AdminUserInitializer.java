package it.tabacchi.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().isEmpty()) {
            System.out.println("Nessun utente  trovato. Creazione dell'utente admin di default...");

            User admin = new User();           
       
            admin.setEmail("frarapto87@gmail.com");
            admin.setPassword(passwordEncoder.encode("Admin1234!"));
            admin.setIsTemporaryPassword(false);
            admin.setNome("Admin");
            admin.setCognome("User");

            userRepository.save(admin);
            
            System.out.println("Utente ADMIN creato con successo.");
        } else {
            System.out.println("Utente ADMIN già presente nel database.");
        }
    }
}
