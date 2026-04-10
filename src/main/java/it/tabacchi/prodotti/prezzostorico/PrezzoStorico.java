package it.tabacchi.prodotti.prezzostorico;

import it.tabacchi.prodotti.prodotto.Prodotto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "prezzi_storici")
public class PrezzoStorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "prezzo_vendita", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoVendita;
    
    @Column(name = "prezzo_acquisto", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoAcquisto;
    
    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;
    
    @Column(name = "data_fine")
    private LocalDate dataFine;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Prodotto prodotto;
    
    //@formatter:off
    public PrezzoStorico() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getPrezzoVendita() { return prezzoVendita; }
    public void setPrezzoVendita(BigDecimal prezzoVendita) { this.prezzoVendita = prezzoVendita; }
    public BigDecimal getPrezzoAcquisto() { return prezzoAcquisto; }
    public void setPrezzoAcquisto(BigDecimal prezzoAcquisto) { this.prezzoAcquisto = prezzoAcquisto; }
    public LocalDate getDataInizio() { return dataInizio; }
    public void setDataInizio(LocalDate dataInizio) { this.dataInizio = dataInizio; }
    public LocalDate getDataFine() { return dataFine; }
    public void setDataFine(LocalDate dataFine) { this.dataFine = dataFine; }
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    //@formatter:on
}
