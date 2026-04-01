package it.tabacchi.prodotti.movimentomagazzino;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovimentoMagazzinoSpec {

    public static Specification<MovimentoMagazzino> byData(LocalDate data) {
       if (data == null) {
           return null;
       }
       return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dataMovimento"), data);
    }

}
