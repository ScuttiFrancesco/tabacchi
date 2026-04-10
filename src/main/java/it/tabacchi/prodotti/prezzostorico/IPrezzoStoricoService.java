package it.tabacchi.prodotti.prezzostorico;

import java.time.LocalDate;
import java.util.List;

import it.tabacchi.pagination.PaginatedResponse;
import it.tabacchi.pagination.PaginationInfoRequest;

public interface IPrezzoStoricoService {

    void bulkInsert(List<PrezzoStoricoRequest> prezzoStoricoRequests, LocalDate dataInizio, LocalDate dataFine);

    PrezzoStoricoDto update(Long id, PrezzoStoricoRequest prezzoStoricoRequest, LocalDate dataInizio, LocalDate dataFine);

    PrezzoStoricoDto getPrezzoStoricoById(Long id);

    PaginatedResponse<List<PrezzoStoricoDto>> search(PaginationInfoRequest paginationInfo, PrezzoStoricoFilter filter);

}
