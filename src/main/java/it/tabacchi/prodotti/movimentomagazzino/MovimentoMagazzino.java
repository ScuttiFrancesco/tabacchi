package it.tabacchi.prodotti.movimentomagazzino;

import it.tabacchi.enums.TipoMovimento;
import it.tabacchi.prodotti.dettagliomovimento.DettaglioMovimento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "movimenti_magazzino")
public class MovimentoMagazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "movimentoMagazzino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DettaglioMovimento> dettagliMovimento;

    @Column(name = "quantita", nullable = false)
    private Integer quantitaProdotti;

    @Column(name = "ricavo", nullable = false, precision = 10, scale = 2)
    private BigDecimal ricavo;

    @Column(name = "guadagno", nullable = false, precision = 10, scale = 2)
    private BigDecimal guadagno;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimento", nullable = false)
    private TipoMovimento tipoMovimento;

    @Column(name = "data_movimento", nullable = false)
    private LocalDateTime dataMovimento = LocalDateTime.now();

    //@formatter:off
    public MovimentoMagazzino() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<DettaglioMovimento> getDettagliMovimento() { return dettagliMovimento; }
    public void setDettagliMovimento(List<DettaglioMovimento> dettagliMovimento) { this.dettagliMovimento = dettagliMovimento; }
    public Integer getQuantitaProdotti() { return quantitaProdotti; }
    public void setQuantitaProdotti(Integer quantitaProdotti) { this.quantitaProdotti = quantitaProdotti; }
    public BigDecimal getRicavoTotale() { return ricavo; }
    public void setRicavoTotale(BigDecimal ricavo) { this.ricavo = ricavo; }
    public BigDecimal getGuadagnoTotale() { return guadagno; }
    public void setGuadagnoTotale(BigDecimal guadagno) { this.guadagno = guadagno; }
    public TipoMovimento getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(TipoMovimento tipoMovimento) { this.tipoMovimento = tipoMovimento; }
    public LocalDateTime getDataMovimento() { return dataMovimento; }
    public void setDataMovimento(LocalDateTime dataMovimento) { this.dataMovimento = dataMovimento; }
    //@formatter:on

    public void addDettaglioMovimento(DettaglioMovimento dettaglio) {
        if (dettagliMovimento == null) {
            dettagliMovimento = new ArrayList<DettaglioMovimento>();
        }
        dettagliMovimento.add(dettaglio);
        dettaglio.setMovimentoMagazzino(this);
    }

    public void removeDettaglioMovimento(DettaglioMovimento dettaglio) {
        if (dettagliMovimento != null) {
            dettagliMovimento.remove(dettaglio);
            dettaglio.setMovimentoMagazzino(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        // 1. Controllo identità fisica
        if (this == o) return true;

        // 2. Controllo null e classe (instanceof gestisce bene i proxy di Hibernate)
        if (!(o instanceof MovimentoMagazzino that)) return false;

        // 3. Se l'ID è nullo, non sono uguali (a meno che non siano lo stesso oggetto, già gestito al punto 1)
        if (id == null || that.getId() == null) return false;

        // 4. Confronto solo l'ID
        return Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        // Usiamo una costante per gli oggetti nuovi, altrimenti l'ID.
        // Questo garantisce che l'hash non cambi mai durante la vita dell'oggetto.
        return getClass().hashCode();
    }
}
