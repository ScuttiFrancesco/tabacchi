package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.prodotti.prodotto.ProdottoDto;

import java.math.BigDecimal;

public record ProdottoMagazzinoDto(

        Long id,
        ProdottoDto prodotto,
        BigDecimal prezzoVendita,
        BigDecimal prezzoAcquisto,
        Integer scortaMinima
) {
}
