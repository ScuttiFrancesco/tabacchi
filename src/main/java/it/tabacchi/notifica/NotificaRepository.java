package it.tabacchi.notifica;

import it.tabacchi.enums.TipoNotifica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long>, JpaSpecificationExecutor<Notifica> {

    List<Notifica> findAllByDestinatarioIdAndLettaFalse(Long destinatarioId);
    Page<Notifica> findAllByDestinatarioId(Long destinatarioId, Pageable pageable);
    Page<Notifica> findAllByDestinatarioIdAndTipoNotifica(Long destinatarioId, TipoNotifica tipoNotifica, Pageable pageable);
    Page<Notifica> findAllByDestinatarioIdAndMessaggioContainingIgnoreCase(Long destinatarioId, String messaggio, Pageable pageable);
    Optional<Notifica> findByIdAndDestinatarioId(Long id, Long destinatarioId);


}
