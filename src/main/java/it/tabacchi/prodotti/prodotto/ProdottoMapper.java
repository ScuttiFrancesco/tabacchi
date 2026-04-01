package it.tabacchi.prodotti.prodotto;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdottoMapper {

    ProdottoDto toDto(Prodotto prodotto);

    List<ProdottoList> toDtoList(List<Prodotto> prodotti);

    Prodotto toEntity(ProdottoDto dto);


}
