package it.tabacchi.prodotti.dettagliomovimento;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DettaglioMovimentoDto(
        Long id,
        Integer quantita,
        BigDecimal prezzoAcquisto,
        BigDecimal prezzoVendita,
        String barcodeProdotto,
        String descrizioneProdotto
) {
}
