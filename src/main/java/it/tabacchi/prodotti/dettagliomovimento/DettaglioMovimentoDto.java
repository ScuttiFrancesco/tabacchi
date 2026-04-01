package it.tabacchi.prodotti.dettagliomovimento;

import java.math.BigDecimal;

public record DettaglioMovimentoDto(
        Long id,
        Integer quantita,
        BigDecimal prezzoAcquisto,
        BigDecimal prezzoVendita,
        String barcodeProdotto,
        String descrizioneProdotto
) {
}
