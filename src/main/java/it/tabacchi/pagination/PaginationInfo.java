package it.tabacchi.pagination;

import org.springframework.data.domain.Sort;

public class PaginationInfo {
    private int paginaCorrente;
    private int numeroElementiPagina;
    private int numeroElementiTotali;
    private int numeroPagineTotali;
    private boolean hasNext;
    private boolean hasPrevious;
    private String sortBy;
    private Sort.Direction sortDirection;

   //@formatter:off
    public int getPaginaCorrente() { return paginaCorrente; }
    public void setPaginaCorrente(int paginaCorrente) { this.paginaCorrente = paginaCorrente + 1; }
    public int getNumeroElementiPagina() { return numeroElementiPagina; }
    public void setNumeroElementiPagina(int numeroElementiPagina) { this.numeroElementiPagina = numeroElementiPagina; }
    public int getNumeroElementiTotali() { return numeroElementiTotali; }
    public void setNumeroElementiTotali(int numeroElementiTotali) { this.numeroElementiTotali = numeroElementiTotali; }
    public int getNumeroPagineTotali() { return numeroPagineTotali; }
    public void setNumeroPagineTotali(int numeroPagineTotali) { this.numeroPagineTotali = numeroPagineTotali; }
    public boolean isHasNext() { return hasNext; }
    public void setHasNext(boolean hasNext) { this.hasNext = hasNext; }
    public boolean isHasPrevious() { return hasPrevious; }
    public void setHasPrevious(boolean hasPrevious) { this.hasPrevious = hasPrevious; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public Sort.Direction getSortDirection() { return sortDirection; }
    public void setSortDirection(Sort.Direction sortDirection) { this.sortDirection = sortDirection; }
}
