package it.tabacchi.prodotti.prodotto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProdottoList(
        Long id,
        String barcode,
        String aamsCode,
        String descrizione
) {
}
