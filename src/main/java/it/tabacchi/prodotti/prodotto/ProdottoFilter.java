package it.tabacchi.prodotti.prodotto;

import it.tabacchi.enums.Categoria;

public class ProdottoFilter {

    private Categoria categoria;

    //@formatter:off
    public ProdottoFilter() {}
    public ProdottoFilter(Categoria categoria) {
        this.categoria = categoria;
    }
    public Categoria getCategoria() { return categoria; }
    //@formatter:on
}
