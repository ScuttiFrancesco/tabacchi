package it.tabacchi.prodotti.movimentomagazzino;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.enums.TipoMovimento;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MovimentoMagazzinoList(
        Long id,
        Integer quantitaProdotti,
        BigDecimal ricavo,
        BigDecimal guadagno,
        TipoMovimento tipoMovimento
) {
}
