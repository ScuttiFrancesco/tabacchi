package it.tabacchi.prodotti.prodotto;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;


import java.util.List;

public interface IProdottoService {

    ProdottoDto create(ProdottoDto request);

    ProdottoDto update(ProdottoDto update);

    ProdottoDto getById(Long id);

    void delete(Long id);

    PaginatedResponse<List<ProdottoList>> search(ProdottoFilter filter, PaginationInfoRequest paginationInfo);
}
