package it.tabacchi.prodotti.movimentomagazzino;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoDto;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MovimentoMagazzinoDto(
        Long id,
        Integer quantitaProdotti,
        BigDecimal ricavo,
        BigDecimal guadagno,
        TipoMovimento tipoMovimento,
        List<DettaglioMovimentoDto> dettagliMovimento
) {
}
