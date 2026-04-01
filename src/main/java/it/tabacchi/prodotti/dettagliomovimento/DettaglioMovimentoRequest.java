package it.tabacchi.prodotti.dettagliomovimento;

public record DettaglioMovimentoRequest(
        Long id,
        String barcodeProdotto,
        Integer quantita
) {
}
