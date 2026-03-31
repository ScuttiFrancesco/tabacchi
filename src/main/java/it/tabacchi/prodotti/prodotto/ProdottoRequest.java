package it.tabacchi.prodotti.prodotto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdottoRequest(

        @NotBlank(message = "Il barcode è obbligatorio")
        @NotNull(message = "Il barcode non può essere null")
        String barcode,

        @NotBlank(message = "Il codice AAMS è obbligatorio")
        @NotNull(message = "Il codice AAMS non può essere null")
        String aamsCode,

        @NotBlank(message = "La descrizione è obbligatoria")
        @NotNull(message = "La descrizione non può essere null")
        String descrizione,

        @NotNull(message = "La categoria è obbligatoria")
        String categoria
) {
}
