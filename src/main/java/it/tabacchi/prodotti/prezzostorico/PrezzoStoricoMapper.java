package it.tabacchi.prodotti.prezzostorico;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrezzoStoricoMapper {

    @Mapping(target = "categoriaProdotto", source = "prodotto.categoria")
    @Mapping(target = "descrizioneProdotto", source = "prodotto.descrizione")
    PrezzoStoricoDto toDto(PrezzoStorico prezzoStorico);

    List<PrezzoStoricoDto> toDtoList(List<PrezzoStorico> prezzoStoricoList);

    @Mapping(target = "prodotto", ignore = true)
    PrezzoStorico toEntity(PrezzoStoricoRequest dto);


}
