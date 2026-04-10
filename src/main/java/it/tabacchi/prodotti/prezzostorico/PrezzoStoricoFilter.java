package it.tabacchi.prodotti.prezzostorico;

import it.tabacchi.enums.Categoria;

public class PrezzoStoricoFilter {

    private Categoria categoria;
    private Boolean isAttivo;

    //@formatter:off
    public PrezzoStoricoFilter() {}
    public PrezzoStoricoFilter(Categoria categoria, Boolean isAttivo) {
        this.categoria = categoria;
        this.isAttivo = isAttivo;
    }
    public Categoria getCategoria() { return categoria; }
    public Boolean isAttivo() { return isAttivo; }

}
