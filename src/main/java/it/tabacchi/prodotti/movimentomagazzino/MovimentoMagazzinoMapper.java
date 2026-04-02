package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimentoMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DettaglioMovimentoMapper.class})
public interface MovimentoMagazzinoMapper {

    MovimentoMagazzinoDto toDto(MovimentoMagazzino movimento);

    List<MovimentoMagazzinoList> toDtoList(List<MovimentoMagazzino> prodotti);

    @Mapping(target = "dettagliMovimento", ignore = true)
    MovimentoMagazzino toEntity(MovimentoMagazzinoRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dettagliMovimento", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntityUpdate(MovimentoMagazzinoRequest dto, @MappingTarget MovimentoMagazzino entity);
}
