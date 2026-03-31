package it.tabacchi.notifica;

import it.tabacchi.enums.TipoNotifica;

import java.time.LocalDate;

public class NotificaFilter {

    private String titolo;
    private String messaggio;
    private Boolean letta;
    private Boolean isVisibile;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private TipoNotifica tipoNotifica;

    //@formatter:off
    public NotificaFilter(){}
    public NotificaFilter(
            String titolo,
            String messaggio,
            Boolean letta,
            Boolean isVisibile,
            LocalDate dataInizio,
            LocalDate dataFine,
            TipoNotifica tipoNotifica
    ){
        this.titolo = titolo;
        this.messaggio = messaggio;
        this.letta = letta;
        this.isVisibile = isVisibile;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.tipoNotifica = tipoNotifica;
    }
    public String getTitolo() { return titolo;}
    public String getMessaggio() { return messaggio;}
    public Boolean getLetta() { return letta;}
    public Boolean getIsVisibile() { return isVisibile;}
    public LocalDate getDataInizio() { return dataInizio;}
    public LocalDate getDataFine() { return dataFine;}
    public TipoNotifica getTipoNotifica() { return tipoNotifica;}
    //@formatter:on
}
