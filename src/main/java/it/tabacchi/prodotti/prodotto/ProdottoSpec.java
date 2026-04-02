package it.tabacchi.prodotti.prodotto;

import it.tabacchi.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

public class ProdottoSpec {

    public static Specification<Prodotto> byCategoria(Categoria categoria) {
       if (categoria == null) {
           return null;
       }
       return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoria"), categoria);
    }

    public static Specification<Prodotto> isAttivo(Boolean attivo) {
        if (attivo == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isAttivo"), attivo);
    }

}
