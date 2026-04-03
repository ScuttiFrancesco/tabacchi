package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.prodotto.Prodotto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prodotti_magazzino")
public class ProdottoMagazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Prodotto prodotto;

    @Column(name = "prezzo_vendita", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoVendita;

    @Column(name = "prezzo_acquisto", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoAcquisto;

    @Column(name = "scorta_attuale", nullable = false)
    private Integer scortaAttuale;

    @Column(name = "scorta_minima", nullable = false)
    private Integer scortaMinima;

    @Column(name = "quantita_da_ordinare", nullable = false)
    private Integer quantitaDaOrdinare;

    @Column(name = "ultimo_aggiornamento")
    private LocalDateTime ultimoAggiornamento = LocalDateTime.now();

    //@formatter:off
    public ProdottoMagazzino() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public BigDecimal getPrezzoVendita() { return prezzoVendita; }
    public void setPrezzoVendita(BigDecimal prezzoVendita) { this.prezzoVendita = prezzoVendita; }
    public BigDecimal getPrezzoAcquisto() { return prezzoAcquisto; }
    public void setPrezzoAcquisto(BigDecimal prezzoAcquisto) { this.prezzoAcquisto = prezzoAcquisto; }
    public Integer getScortaAttuale() { return scortaAttuale; }
    public void setScortaAttuale(Integer scortaAttuale) { this.scortaAttuale = scortaAttuale; }
    public Integer getScortaMinima() { return scortaMinima; }
    public void setScortaMinima(Integer scortaMinima) { this.scortaMinima = scortaMinima; }
    public Integer getQuantitaDaOrdinare() { return quantitaDaOrdinare; }
    public void setQuantitaDaOrdinare(Integer quantitaDaOrdinare) { this.quantitaDaOrdinare = quantitaDaOrdinare; }
    public LocalDateTime getUltimoAggiornamento() { return ultimoAggiornamento; }
    public void setUltimoAggiornamento(LocalDateTime ultimoAggiornamento) { this.ultimoAggiornamento = ultimoAggiornamento; }
    //@formatter:on

    public void aggiornaMagazzino(Integer quantita, TipoMovimento tipoMovimento){
        if (quantita != null && quantita > 0) {
            if (tipoMovimento == TipoMovimento.RIFORNIMENTO) {
                this.scortaAttuale += quantita;
            } else if (tipoMovimento == TipoMovimento.VENDITA) {
                if (quantita > this.scortaAttuale) {
                    throw new IllegalArgumentException("Quantità di vendita superiore alla scorta attuale");
                }
                this.scortaAttuale -= quantita;
            }
        }
    }
}
