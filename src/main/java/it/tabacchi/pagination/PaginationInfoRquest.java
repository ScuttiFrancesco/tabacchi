package it.tabacchi.pagination;

import org.springframework.data.domain.Sort;

public class PaginationInfoRquest {
    private int paginaCorrente;
    private int numeroElementiPagina;
    private String sortBy;
    private Sort.Direction sortDirection;

    //@formatter:off
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