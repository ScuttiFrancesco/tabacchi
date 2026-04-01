package it.tabacchi.pagination;

import org.springframework.data.domain.Sort;

public class PaginationInfoRequest {
    private int paginaCorrente;
    private int numeroElementiPagina;
    private String sortBy;
    private Sort.Direction sortDirection;

    //@formatter:off
    public PaginationInfoRequest(int page, int size, String sortBy, Sort.Direction direction) {
        this.paginaCorrente = page;
        this.numeroElementiPagina = size;
        this.sortBy =sortBy;
        this.sortDirection = direction;
    }
    public int getPaginaCorrente() { return paginaCorrente; }
    public void setPaginaCorrente(int paginaCorrente) { this.paginaCorrente = paginaCorrente; }
    public int getNumeroElementiPagina() { return numeroElementiPagina; }
    public void setNumeroElementiPagina(int numeroElementiPagina) { this.numeroElementiPagina = numeroElementiPagina; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public Sort.Direction getSortDirection() { return sortDirection; }
    public void setSortDirection(Sort.Direction sortDirection) { this.sortDirection = sortDirection; }
     //@formatter:on
}