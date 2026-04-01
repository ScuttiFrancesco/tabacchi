package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MovimentoMagazzinoRequest(

        Long id,

        @NotNull(message = "Il tipo movimento è obbligatorio")
        TipoMovimento tipoMovimento,

        @NotNull(message = "I dettagli del movimento sono obbligatori")
        @Min(value = 1, message = "Deve essere presente almeno un dettaglio movimento")
        List<DettaglioMovimentoRequest> dettagliMovimento
) {
}
