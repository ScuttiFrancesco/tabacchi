package it.tabacchi.prodotti.prodottomagazzino;

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

    @Column(name = "ultimo_aggiornamento")
    private LocalDateTime ultimoAggiornamento;
}
