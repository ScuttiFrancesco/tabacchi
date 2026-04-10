package it.tabacchi.prodotti.prodotto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.tabacchi.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProdottoDto(

        Long id,
        String barcode,

        @NotBlank(message = "Il codice AAMS è obbligatorio")
        @NotNull(message = "Il codice AAMS non può essere null")
        String aamsCode,

        @NotBlank(message = "La descrizione è obbligatoria")
        @NotNull(message = "La descrizione non può essere null")
        String descrizione,

        @NotNull(message = "La categoria è obbligatoria")
        Categoria categoria,

        Boolean attivo
) {
}
