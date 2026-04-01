package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoMovimento;

import java.math.BigDecimal;

public record MovimentoMagazzinoList(
        Long id,
        Integer quantitaProdotti,
        BigDecimal ricavo,
        BigDecimal guadagno,
        TipoMovimento tipoMovimento
) {
}
