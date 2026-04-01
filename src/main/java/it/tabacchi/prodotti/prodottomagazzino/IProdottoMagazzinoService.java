package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.enums.Categoria;
import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;


import java.util.List;

public interface IProdottoMagazzinoService {

    ProdottoMagazzinoDto create(ProdottoMagazzinoRequest request);

    ProdottoMagazzinoDto update(ProdottoMagazzinoRequest update);

    ProdottoMagazzinoDto getById(Long id);

    void delete(Long id);

    PaginatedResponse<List<ProdottoMagazzinoList>> getAll(PaginationInfoRequest paginationInfo);

    PaginatedResponse<List<ProdottoMagazzinoList>> getAllByCategoria(Categoria categoria, PaginationInfoRequest paginationInfo);
}
