package it.tabacchi.schedule;

import it.tabacchi.enums.TipoNotifica;
import it.tabacchi.notifica.NotificaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.tabacchi.auth.service.EmailService;
import it.tabacchi.auth.service.PasswordService;
import it.tabacchi.user.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ScheduleService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final NotificaService notificaService;

    @Autowired
    public ScheduleService(UserRepository userRepository, PasswordService passwordService, EmailService emailService, PasswordEncoder passwordEncoder, NotificaService notificaService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.notificaService = notificaService;
    }

    @Scheduled(cron = "0 07 19 ? * SUN")
    @Transactional
    public void resetDailyCounters() {

    notificaService.creaNotifica(1L, "MESSAGGIO D'AMORE", "EVELINA...la mia ragione di vita", TipoNotifica.INFO);

    }

    

}