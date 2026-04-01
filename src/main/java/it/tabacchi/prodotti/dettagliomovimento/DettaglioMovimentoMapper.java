package it.tabacchi.prodotti.dettagliomovimento;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DettaglioMovimentoMapper {

    @Mapping(target = "barcodeProdotto", source = "prodotto.barcode")
    @Mapping(target = "descrizioneProdotto", source = "prodotto.descrizione")
    DettaglioMovimentoDto toDto(DettaglioMovimento dettaglio);

    List<DettaglioMovimentoDto> toDtoList(List<DettaglioMovimento> dettagli);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "prezzoAcquisto", ignore = true)
    @Mapping(target = "prezzoVendita", ignore = true)
    DettaglioMovimento toEntity(DettaglioMovimentoRequest dto);


}
