package it.tabacchi.notifica;

import it.tabacchi.enums.TipoNotifica;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class NotificaSpec {

    public static Specification<Notifica> byDataBetween(LocalDate dataInizio, LocalDate dataFine) {
        return (root, query, cb) -> {
            if (dataInizio == null && dataFine == null) return null;
            if (dataInizio != null && dataFine == null) return cb.greaterThanOrEqualTo(root.get("data"), dataInizio);
            if (dataInizio == null) return cb.lessThanOrEqualTo(root.get("data"), dataFine);
            return cb.between(root.get("data"), dataInizio, dataFine);
        };
    }

    public static Specification<Notifica> byTitoloStarting(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty())
                return null;
            String pattern = keyword.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("titolo")), pattern);
        };
    }

    public static Specification<Notifica> byMessaggioContaining(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty())
                return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("messaggio")), pattern);
        };
    }

   public static Specification<Notifica> byLettura(Boolean letta) {
        return (root, query, criteriaBuilder) -> {
            if (letta == null)
                return null;
            return criteriaBuilder.equal(root.get("letta"), letta);
        };
    }

    public static Specification<Notifica> byTipoNotifica(TipoNotifica tipo) {
        return (root, query, criteriaBuilder) -> {
            if (tipo == null)
                return null;
            return criteriaBuilder.equal(root.get("tipoNotifica"), tipo);
        };
    }

    public static Specification<Notifica> byDestinatarioId(Long destinatarioId) {
        return (root, query, criteriaBuilder) -> {
            if (destinatarioId == null)
                return null;
            return criteriaBuilder.equal(root.get("destinatarioId"), destinatarioId);
        };
    }

    public static Specification<Notifica> byIsVisibile(Boolean isVisibile) {
        return (root, query, criteriaBuilder) -> {
            if (isVisibile == null)
                return null;
            return criteriaBuilder.equal(root.get("isVisibile"), isVisibile);
        };
    }
}