package it.tabacchi.prodotti.prezzostorico;

import it.tabacchi.enums.Categoria;

public record PrezzoStoricoDto(
        Long id,
        String descrizioneProdotto,
        Categoria categoriaProdotto,
        String prezzoVendita,
        String prezzoAcquisto,
        String dataInizio,
        String dataFine
) {}
