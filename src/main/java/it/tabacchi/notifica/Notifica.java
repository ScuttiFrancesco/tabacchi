package it.tabacchi.notifica;

import it.tabacchi.enums.TipoNotifica;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "notifiche", indexes = {
    @Index(name = "idx_notifica_destinatario_letta", columnList = "destinatario_id, letta"),
    @Index(name = "idx_notifica_destinatario_tipo", columnList = "destinatario_id, tipo_notifica"),
    @Index(name = "idx_notifica_data", columnList = "data")
})
public class Notifica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destinatario_id", nullable = false)
    private Long destinatarioId;

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "messaggio")
    private String messaggio;

    @Column(name = "letta", nullable = false)
    private boolean letta = false;

    @Column(name = "is_visibile", nullable = false)
    private boolean isVisibile = true;

    @Column(name = "data", nullable = false)
    private Instant data = Instant.now();

    @Enumerated(EnumType.STRING)
    private TipoNotifica tipoNotifica;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "to_pagina")
    private String toPagina;

    // @formatter:off
    public Notifica() {}
    public Notifica(Long destinatarioId, String titolo, String messaggio, TipoNotifica tipoNotifica) {
        this.destinatarioId = destinatarioId;
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.tipoNotifica = tipoNotifica;
    }
    public Notifica(Long destinatarioId, String titolo, String messaggio, TipoNotifica tipoNotifica, String endpoint, String toPagina) {
        this.destinatarioId = destinatarioId;
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.tipoNotifica = tipoNotifica;
        this.endpoint = endpoint;
        this.toPagina = toPagina;
    }
    public Long getId() { return id; }
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
    public boolean isLetta() { return letta; }
    public void setLetta(boolean letta) { this.letta = letta; }
    public boolean isVisibile() { return isVisibile; }
    public void setVisibile(boolean isVisibile) { this.isVisibile = isVisibile; }
    public Instant getData() { return data; }
    public void setData(Instant data) { this.data = data; }
    public TipoNotifica getTipoNotifica() { return tipoNotifica; }
    public void setTipoNotifica(TipoNotifica tipoNotifica) { this.tipoNotifica = tipoNotifica; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getToPagina() { return toPagina; }
    public void setToPagina(String toPagina) { this.toPagina = toPagina; }
    //@formatter:on

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notifica)) return false;
        Notifica that = (Notifica) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
