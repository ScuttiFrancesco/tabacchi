package it.tabacchi.prodotti.prodottomagazzino;

import java.math.BigDecimal;

public record ProdottoMagazzinoList(
        Long id,
        String barcodeProdotto,
        String descrizioneProdotto,
        String aamsProdotto,
        BigDecimal prezzoVendita,
        BigDecimal prezzoAcquisto,
        Integer scortaAttuale,
        Integer scortaMinima
) {
}
