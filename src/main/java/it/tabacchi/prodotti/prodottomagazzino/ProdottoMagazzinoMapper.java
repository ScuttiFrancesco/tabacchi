package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.prodotti.prodotto.ProdottoMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProdottoMapper.class})
public interface ProdottoMagazzinoMapper {

    ProdottoMagazzinoDto toDto(ProdottoMagazzino prodottomagazzino);

    @Mapping(target = "barcodeProdotto", source = "prodotto.barcode")
    @Mapping(target = "descrizioneProdotto", source = "prodotto.descrizione")
    @Mapping(target = "aamsProdotto", source = "prodotto.aamsCode")
    ProdottoMagazzinoList toList(ProdottoMagazzino prodottomagazzino);

    List<ProdottoMagazzinoList> toDtoList(List<ProdottoMagazzino> prodottiMagazzino);

    ProdottoMagazzino toEntity(ProdottoMagazzinoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntityUpdate(ProdottoMagazzinoRequest dto, @MappingTarget ProdottoMagazzino entity);


}
