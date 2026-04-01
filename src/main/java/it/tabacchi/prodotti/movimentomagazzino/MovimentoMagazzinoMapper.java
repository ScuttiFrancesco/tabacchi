package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DettaglioMovimentoMapper.class})
public interface MovimentoMagazzinoMapper {

    MovimentoMagazzinoDto toDto(MovimentoMagazzino movimento);

    List<MovimentoMagazzinoList> toDtoList(List<MovimentoMagazzino> prodotti);

    MovimentoMagazzino toEntity(MovimentoMagazzinoRequest dto);


}
