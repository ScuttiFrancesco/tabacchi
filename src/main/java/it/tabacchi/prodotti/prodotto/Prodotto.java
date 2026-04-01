package it.tabacchi.prodotti.prodotto;

import it.tabacchi.enums.Categoria;
import jakarta.persistence.*;

@Entity
@Table(name = "prodotti")
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barcode", unique = true, nullable = false)
    private String barcode;

    @Column(name = "aams_code", nullable = false)
    private String aamsCode;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "is_attivo")
    private Boolean isAttivo = true;

    //@formatter:off
    public Prodotto() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getAamsCode() { return aamsCode; }
    public void setAamsCode(String aamsCode) { this.aamsCode = aamsCode; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Boolean isAttivo() { return isAttivo; }
    public void setAttivo(Boolean attivo) { this.isAttivo = attivo; }
    //@formatter:on
}
