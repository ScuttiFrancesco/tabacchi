package it.tabacchi.prodotti.prodottomagazzino;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.prodotti.prodotto.ProdottoDto;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProdottoMagazzinoDto(

        Long id,
        ProdottoDto prodotto,
        BigDecimal prezzoVendita,
        BigDecimal prezzoAcquisto,
        Integer scortaMinima,
        Integer scortaAttuale
) {
}
