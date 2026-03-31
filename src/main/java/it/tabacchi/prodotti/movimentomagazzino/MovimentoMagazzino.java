package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.prodotto.Prodotto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimenti_magazzino")
public class MovimentoMagazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Prodotto prodotto;

    @Column(name = "quantita", nullable = false)
    private Integer quantita;

    @Column(name = "prezzo_acquisto_totale", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoAcquistoTotale;

    @Column(name = "prezzo_vendita_totale", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoVenditaTotale;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimento", nullable = false)
    private TipoMovimento tipoMovimento;

    @Column(name = "data_movimento", nullable = false)
    private LocalDateTime data = LocalDateTime.now();

    //@formatter:off
    public MovimentoMagazzino() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    public BigDecimal getPrezzoAcquistoTotale() { return prezzoAcquistoTotale; }
    public void setPrezzoAcquistoTotale(BigDecimal prezzoAcquistoTotale) { this.prezzoAcquistoTotale = prezzoAcquistoTotale; }
    public BigDecimal getPrezzoVenditaTotale() { return prezzoVenditaTotale; }
    public void setPrezzoVenditaTotale(BigDecimal prezzoVenditaTotale) { this.prezzoVenditaTotale = prezzoVenditaTotale; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    //@formatter:on

}
