package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.PuntoCaldo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GuadagnoDto {

    private BigDecimal guadagno;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private PuntoCaldo puntoCaldo;

    //@formatter:off
    public GuadagnoDto() {}
    public GuadagnoDto(BigDecimal guadagno, LocalDate dataInizio, LocalDate dataFine, PuntoCaldo puntoCaldo) {
        this.guadagno = guadagno;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.puntoCaldo = puntoCaldo;
    }
    public BigDecimal getGuadagno() { return guadagno; }
    public void setGuadagno(BigDecimal guadagno) { this.guadagno = guadagno; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    public PuntoCaldo getPuntoCaldo() { return puntoCaldo; }
    public void setPuntoCaldo(PuntoCaldo puntoCaldo) { this.puntoCaldo = puntoCaldo; }
    //@formatter:on
}
