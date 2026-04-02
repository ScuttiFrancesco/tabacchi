package it.tabacchi.prodotti.prodotto;

public record ProdottoList(
        Long id,
        String barcode,
        String aamsCode,
        String descrizione
) {
}
