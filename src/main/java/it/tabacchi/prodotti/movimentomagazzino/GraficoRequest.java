package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoGrafico;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GraficoRequest(
        @NotNull(message = "Il tipo di grafico è obbligatorio")
        TipoGrafico tipoGrafico,

        Integer numeroElementi
) {
}
