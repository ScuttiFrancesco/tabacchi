package it.tabacchi.prodotti.prezzostorico;

import it.tabacchi.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

public class PrezzoStoricoSpec {

    public static Specification<PrezzoStorico> byCategoria(Categoria categoria) {
       if (categoria == null) {
           return null;
       }
       return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("prodotto").get("categoria"), categoria);
    }

    public static Specification<PrezzoStorico> isAttivo(Boolean attivo) {
        if (attivo == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("prodotto").get("isAttivo"), attivo);
    }

}
