package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoDto;

import java.math.BigDecimal;
import java.util.List;

public record MovimentoMagazzinoDto(
        Long id,
        Integer quantitaProdotti,
        BigDecimal ricavo,
        BigDecimal guadagno,
        TipoMovimento tipoMovimento,
        List<DettaglioMovimentoDto> dettagliMovimento
) {
}
