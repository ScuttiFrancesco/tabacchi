package it.tabacchi.prodotti.prodottomagazzino;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
