package it.tabacchi.prodotti.prezzostorico;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrezzoStoricoRequest(

        @NotBlank(message = "La descrizione del prodotto è obbligatoria")
        @NotNull(message = "La descrizione del prodotto non può essere null")
        String barcodeProdotto,
        @NotNull(message = "Il codice AAMS del prodotto non può essere null")
        BigDecimal prezzoVendita,
        @NotNull(message = "Il prezzo di acquisto del prodotto non può essere null")
        BigDecimal prezzoAcquisto
) {}
