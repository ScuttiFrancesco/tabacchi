package it.tabacchi.prodotti.prodottomagazzino;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdottoMagazzinoRequest(

        Long id,

        @NotNull(message = "Il barcode del prodotto è obbligatorio.")
        String barcodeProdotto,

        @NotNull(message = "Il prezzo di vendita è obbligatorio.")
        @Min(value = 0, message = "Il prezzo di vendita deve essere positivo.")
        BigDecimal prezzoVendita,

        @NotNull(message = "Il prezzo di acquisto è obbligatorio.")
        @Min(value = 0, message = "Il prezzo di acquisto deve essere positivo.")
        BigDecimal prezzoAcquisto,

        @NotNull(message = "La scorta minima è obbligatoria.")
        @Min(value = 1, message = "La scorta minima deve essere almeno 1.")
        Integer scortaMinima,

        @NotNull(message = "La quantità da ordinare è obbligatoria.")
        @Min(value = 1, message = "La quantità da ordinare non può essere negativa.")
        Integer quantitaDaOrdinare
) {
}
