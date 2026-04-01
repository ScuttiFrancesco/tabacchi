package it.tabacchi.prodotti.grafico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GraficoDto {

    private String etichetta;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private BigDecimal guadagnoTotale;
    private BigDecimal guadagnoMedio;
    private BigDecimal guadagnoMinimo;
    private BigDecimal guadagnoMassimo;
    private BigDecimal ricavoTotale;
    private BigDecimal percentualeGuadagno;
    private List<GuadagnoDto> guadagni;

    //@formatter:off
    public GraficoDto(){}
    public GraficoDto(String etichetta, LocalDate dataInizio, LocalDate dataFine, BigDecimal guadagnoTotale,
                      BigDecimal guadagnoMedio, BigDecimal guadagnoMinimo, BigDecimal guadagnoMassimo,
                      BigDecimal ricavoTotale, BigDecimal percentualeGuadagno, List<GuadagnoDto> guadagni) {
        this.etichetta = etichetta;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.guadagnoTotale = guadagnoTotale;
        this.guadagnoMedio = guadagnoMedio;
        this.guadagnoMinimo = guadagnoMinimo;
        this.guadagnoMassimo = guadagnoMassimo;
        this.ricavoTotale = ricavoTotale;
        this.percentualeGuadagno = percentualeGuadagno;
        this.guadagni = guadagni;
    }
    public void setEtichetta(String etichetta) { this.etichetta = etichetta;}
    public String getEtichetta() { return etichetta; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    public LocalDate getDataFine() { return dataFine; }
    public void setGuadagnoTotale(BigDecimal guadagnoTotale) { this.guadagnoTotale = guadagnoTotale; }
    public BigDecimal getGuadagnoTotale() { return guadagnoTotale; }
    public void setGuadagnoMedio(BigDecimal guadagnoMedio) { this.guadagnoMedio = guadagnoMedio; }
    public BigDecimal getGuadagnoMedio() { return guadagnoMedio; }
    public void setGuadagnoMinimo(BigDecimal guadagnoMinimo) { this.guadagnoMinimo = guadagnoMinimo; }
    public BigDecimal getGuadagnoMinimo() { return guadagnoMinimo; }
    public void setGuadagnoMassimo(BigDecimal guadagnoMassimo) { this.guadagnoMassimo = guadagnoMassimo; }
    public BigDecimal getGuadagnoMassimo() { return guadagnoMassimo; }
    public void setRicavoTotale(BigDecimal ricavoTotale) { this.ricavoTotale = ricavoTotale; }
    public BigDecimal getRicavoTotale() { return ricavoTotale; }
    public void setPercentualeGuadagno(BigDecimal percentualeGuadagno) { this.percentualeGuadagno = percentualeGuadagno; }
    public BigDecimal getPercentualeGuadagno() { return percentualeGuadagno; }
    public void setGuadagni(List<GuadagnoDto> guadagni) { this.guadagni = guadagni; }
    public List<GuadagnoDto> getGuadagni() { return guadagni; }
    //@formatter:on

}
