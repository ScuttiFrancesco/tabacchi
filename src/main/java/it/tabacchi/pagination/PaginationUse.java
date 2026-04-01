package it.tabacchi.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.function.Function;

public class PaginationUse {

    public static Pageable pagination(PaginationInfoRequest paginationInfoRquest) {
        Sort.Direction direction = paginationInfoRquest.getSortDirection() != null
                ? paginationInfoRquest.getSortDirection()
                : Sort.Direction.ASC;
        String sortBy = paginationInfoRquest.getSortBy();
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }
        return PageRequest.of(
                paginationInfoRquest.getPaginaCorrente(),
                paginationInfoRquest.getNumeroElementiPagina(),
                Sort.by(direction, sortBy));
    }

    /**
     * Costruisce una risposta paginata generica.
     * @param page La pagina di dati restituita dal repository.
     * @param mapper La funzione per mappare la lista di entità in una lista di DTO.
     * @param paginationInfoRquest La richiesta di paginazione originale per recuperare i parametri di sort.
     * @param <T> Il tipo dell'entità (es. User).
     * @param <D> Il tipo del DTO (es. UserDto).
     * @return Una risposta paginata completa.
     */
    public static <T, D> PaginatedResponse<List<D>> buildPaginatedResponse(
            Page<T> page,
            Function<List<T>, List<D>> mapper,
            PaginationInfoRequest paginationInfoRquest) {

        List<D> dtoList = mapper.apply(page.getContent());

        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setNumeroElementiPagina(page.getSize());
        paginationInfo.setPaginaCorrente(page.getNumber());
        paginationInfo.setNumeroElementiTotali((int) page.getTotalElements());
        paginationInfo.setNumeroPagineTotali(page.getTotalPages());
        paginationInfo.setHasNext(page.hasNext());
        paginationInfo.setHasPrevious(page.hasPrevious());
        paginationInfo.setSortBy(paginationInfoRquest.getSortBy());
        paginationInfo.setSortDirection(paginationInfoRquest.getSortDirection());

        PaginatedResponse<List<D>> response = new PaginatedResponse<>();
        response.setData(dtoList);
        response.setPaginazione(paginationInfo);

        return response;
    }


}
