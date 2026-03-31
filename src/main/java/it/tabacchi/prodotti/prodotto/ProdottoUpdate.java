package it.tabacchi.prodotti.prodotto;

public record ProdottoUpdate(
        Long id,
        String barcode,
        String aamsCode,
        String descrizione,
        String categoria,
        Boolean attivo
) {
}
