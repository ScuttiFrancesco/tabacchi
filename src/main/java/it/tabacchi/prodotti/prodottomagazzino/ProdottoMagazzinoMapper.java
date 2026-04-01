package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.prodotti.prodotto.ProdottoMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProdottoMapper.class})
public interface ProdottoMagazzinoMapper {

    ProdottoMagazzinoDto toDto(ProdottoMagazzino prodottomagazzino);

    List<ProdottoMagazzinoList> toDtoList(List<ProdottoMagazzino> prodottiMagazzino);

    ProdottoMagazzino toEntity(ProdottoMagazzinoRequest dto);


}
