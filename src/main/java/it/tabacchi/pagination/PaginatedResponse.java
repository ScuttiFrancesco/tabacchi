package it.tabacchi.pagination;


public class PaginatedResponse<T> {
    private T data;
    private PaginationInfo paginazione;
    
    // Getters and Setters
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    
    public PaginationInfo getPaginazione() { return paginazione; }
    public void setPaginazione(PaginationInfo paginazione) { this.paginazione = paginazione; }
}
