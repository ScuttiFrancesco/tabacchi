package it.tabacchi.prodotti.movimentomagazzino;

import java.time.LocalDate;

public class MovimentoMagazzinoFilter {

    private LocalDate data;

    //@formatter:off
    public MovimentoMagazzinoFilter(){}
    public MovimentoMagazzinoFilter(LocalDate data) {
        this.data = data;
    }
    public LocalDate getData(){return this.data;}
     //@formatter:on
}
