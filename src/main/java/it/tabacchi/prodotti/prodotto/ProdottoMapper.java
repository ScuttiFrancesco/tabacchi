package it.tabacchi.prodotti.prodotto;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdottoMapper {

    ProdottoDto toDto(Prodotto prodotto);

    ProdottoList toListDto(Prodotto prodotto);

    List<ProdottoList> toDtoList(List<Prodotto> prodotti);

    @Mapping(target = "prodottoMagazzino", ignore = true)
    Prodotto toEntity(ProdottoDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntityUpdate(ProdottoDto dto, @MappingTarget Prodotto entity);

}
