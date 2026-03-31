package it.tabacchi.notifica;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.enums.TipoNotifica;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificaDto {

    private Long id;
    private Long destinatarioId;
    private String titolo;
    private String messaggio;
    private boolean letta;
    private boolean isVisibile;
    private Instant data;
    private TipoNotifica tipoNotifica;
    private String endpoint;
    private String toPagina;

    // @formatter:off
    public NotificaDto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
    public boolean isLetta() { return letta; }
    public void setLetta(boolean letta) { this.letta = letta; }
    public boolean isVisibile() { return isVisibile; }
    public void setVisibile(boolean visibile) { isVisibile = visibile; }
    public Instant getData() { return data; }
    public void setData(Instant data) { this.data = data; }
    public TipoNotifica getTipoNotifica() { return tipoNotifica; }
    public void setTipoNotifica(TipoNotifica tipoNotifica) { this.tipoNotifica = tipoNotifica; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getToPagina() { return toPagina; }
    public void setToPagina(String toPagina) { this.toPagina = toPagina; }
    //@formatter:on
}
