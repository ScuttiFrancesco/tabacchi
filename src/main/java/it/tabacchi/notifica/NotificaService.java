package it.tabacchi.notifica;

import it.tabacchi.enums.TipoNotifica;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.pagination.PaginationUse;
import it.tabacchi.shared.SharedMethods;
import it.tabacchi.user.User;
import it.tabacchi.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@Service
public class NotificaService {

    private final NotificaRepository notificaRepository;
    private final NotificaMapper notificaMapper;
    private final UserRepository userRepository;
    private final SharedMethods sharedMethods;

    // L'imbuto reattivo: trasmette le notifiche in tempo reale a chi è ascolto
    private final Sinks.Many<NotificaDto> busNotifiche = Sinks.many().multicast().onBackpressureBuffer();

    public NotificaService(NotificaRepository notificaRepository, NotificaMapper notificaMapper, UserRepository userRepository, SharedMethods sharedMethods) {
        this.notificaRepository = notificaRepository;
        this.notificaMapper = notificaMapper;
        this.userRepository = userRepository;
        this.sharedMethods = sharedMethods;
    }

    private User getCurrentUser() {
        return sharedMethods.getUserFromContext();
    }

    @Transactional
    public void creaNotifica(Long userId, String titolo, String messaggio, TipoNotifica tipo) {
        Notifica n = new Notifica(userId, titolo, messaggio, tipo);
        n = notificaRepository.save(n);

        // PUBBLICA sul bus: questo "sveglia" istantaneamente il curl nel terminale
        busNotifiche.tryEmitNext(notificaMapper.toDto(n));
    }

    @Transactional(readOnly = true)
    public Flux<ServerSentEvent<NotificaDto>> streamNotificheUtente(Long userId) {
        return busNotifiche.asFlux()
                .filter(dto -> dto.getDestinatarioId().equals(userId))
                .map(dto -> ServerSentEvent.<NotificaDto>builder(dto).event("nuova")
                        .build())
                .onErrorResume(e -> Flux.empty());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<List<NotificaDto>> getAllByCriteria(NotificaFilter filter, PaginationInfoRequest paginationInfo) {

        Specification<Notifica> spec = Specification.where(NotificaSpec.byDestinatarioId(sharedMethods.getUserFromContext().getId()))
                .and(NotificaSpec.byTipoNotifica(filter.getTipoNotifica()))
                .and(NotificaSpec.byLettura(filter.getLetta()))
                .and(NotificaSpec.byDataBetween(filter.getDataInizio(), filter.getDataFine()))
                .and(NotificaSpec.byTitoloStarting(filter.getTitolo()))
                .and(NotificaSpec.byMessaggioContaining(filter.getMessaggio()))
                .and(NotificaSpec.byIsVisibile(filter.getIsVisibile()));

        Page<Notifica> notifichePaged = notificaRepository.findAll(spec, PaginationUse.pagination(paginationInfo));
        return PaginationUse.buildPaginatedResponse(notifichePaged, notificaMapper::toDtoList, paginationInfo);
    }

    @Transactional(readOnly = true)
    public NotificaDto getNotificaById(Long notificaId) {

        Notifica notifica = notificaRepository.findByIdAndDestinatarioId(notificaId, getCurrentUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Notifica non trovata con ID: " + notificaId + " per l'utente corrente"));
        return notificaMapper.toDto(notifica);
    }

    @Transactional
    public void segnaComeLetta(Long notificaId, boolean letta) {
        Notifica notifica = notificaRepository.findByIdAndDestinatarioId(notificaId, getCurrentUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Notifica non trovata con ID: " + notificaId + " per l'utente corrente"));
        notifica.setLetta(letta);
        notificaRepository.save(notifica);
        busNotifiche.tryEmitNext(notificaMapper.toDto(notifica)); // Aggiorna anche il bus per eventuali ascoltatori
    }

    @Transactional
    public void segnaTutteComeLette(boolean lette) {
        List<Notifica> notificheNonLette = notificaRepository.findAllByDestinatarioIdAndLettaFalse(getCurrentUser().getId());
        notificheNonLette.forEach(n -> n.setLetta(lette));
        notificaRepository.saveAll(notificheNonLette);
        notificheNonLette.forEach(n -> busNotifiche.tryEmitNext(notificaMapper.toDto(n))); // Aggiorna anche il bus per eventuali ascoltatori
    }

    @Transactional
    public void eliminaNotifica(Long notificaId) {
        Notifica notifica = notificaRepository.findByIdAndDestinatarioId(notificaId, getCurrentUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Notifica non trovata con ID: " + notificaId + " per l'utente corrente"));
        notifica.setVisibile(false); // Soft delete: nascondi la notifica invece di eliminarla fisicamente
        notificaRepository.save(notifica);
        busNotifiche.tryEmitNext(notificaMapper.toDto(notifica)); // Aggiorna anche il bus per eventuali ascoltatori (es. per rimuovere la notifica dalla lista)
    }
}
