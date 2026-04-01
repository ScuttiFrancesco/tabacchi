package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;
import it.tabacchi.prodotti.grafico.GraficoDto;
import it.tabacchi.prodotti.grafico.GraficoRequest;

import java.util.List;

public interface IMovimentoMagazzinoService {

    MovimentoMagazzinoDto create(MovimentoMagazzinoRequest request);

    MovimentoMagazzinoDto update(MovimentoMagazzinoRequest request);

    MovimentoMagazzinoDto getById(Long id);

    PaginatedResponse<List<MovimentoMagazzinoList>> search(MovimentoMagazzinoFilter filter, PaginationInfoRequest paginationInfo);

    void delete(Long id);

    GraficoDto getDatiGrafico(GraficoRequest request);

}
