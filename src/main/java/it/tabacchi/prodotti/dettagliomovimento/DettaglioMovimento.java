package it.tabacchi.prodotti.dettagliomovimento;

import it.tabacchi.prodotti.movimentomagazzino.MovimentoMagazzino;
import it.tabacchi.prodotti.prodotto.Prodotto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "dettagli_movimento")
public class DettaglioMovimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimento_magazzino_id")
    private MovimentoMagazzino movimentoMagazzino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    @Column(name = "quantita", nullable = false)
    private Integer quantita;

    @Column(name = "prezzo_acquisto", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoAcquisto;

    @Column(name = "prezzo_vendita", nullable = false, precision = 10, scale = 2)
    private BigDecimal prezzoVendita;

    //@formatter:off
    public DettaglioMovimento() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MovimentoMagazzino getMovimentoMagazzino() { return movimentoMagazzino; }
    public void setMovimentoMagazzino(MovimentoMagazzino movimentoMagazzino) {this.movimentoMagazzino = movimentoMagazzino;}
    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
    public Integer getQuantita() { return quantita; }
    public void setQuantita(Integer quantita) { this.quantita = quantita; }
    public BigDecimal getPrezzoAcquisto() { return prezzoAcquisto; }
    public void setPrezzoAcquisto(BigDecimal prezzoAcquisto) { this.prezzoAcquisto = prezzoAcquisto; }
    public BigDecimal getPrezzoVendita() { return prezzoVendita; }
    public void setPrezzoVendita(BigDecimal prezzoVendita) { this.prezzoVendita = prezzoVendita; }
    //@formatter:on

    // In DettaglioMovimento.java (senza toccare equals/hashCode standard)
    public boolean duplicato(DettaglioMovimento other) {
        if (other == null) return false;
        return Objects.equals(this.prodotto.getId(), other.getProdotto().getId()) &&
                Objects.equals(this.quantita, other.getQuantita());
    }
}
