package it.tabacchi.prodotti.grafico;

import it.tabacchi.enums.TipoGrafico;
import jakarta.validation.constraints.NotNull;


public record GraficoRequest(
        @NotNull(message = "Il tipo di grafico è obbligatorio")
        TipoGrafico tipoGrafico,

        Integer numeroElementi
) {
}
