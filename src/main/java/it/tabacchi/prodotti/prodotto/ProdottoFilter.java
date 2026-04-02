package it.tabacchi.prodotti.prodotto;

import it.tabacchi.enums.Categoria;

public class ProdottoFilter {

    private Categoria categoria;
    private Boolean isAttivo;

    //@formatter:off
    public ProdottoFilter() {}
    public ProdottoFilter(Categoria categoria, Boolean isAttivo) {
        this.categoria = categoria;
        this.isAttivo = isAttivo;
    }
    public Categoria getCategoria() { return categoria; }
    public Boolean isAttivo() { return isAttivo; }
    //@formatter:on
}
