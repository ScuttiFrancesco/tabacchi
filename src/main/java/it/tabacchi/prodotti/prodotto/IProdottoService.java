package it.tabacchi.prodotti.prodotto;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRquest;

import java.util.List;

public interface IProdottoService {

    ProdottoUpdate create(ProdottoRequest request);

    ProdottoUpdate update(ProdottoUpdate update);

    ProdottoUpdate getById(Long id);

    void delete(Long id);

    PaginatedResponse<List<ProdottoList>> getAll(PaginationInfoRquest paginationInfo);
}
