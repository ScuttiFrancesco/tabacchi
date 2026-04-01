package it.tabacchi.prodotti.prodotto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long>, JpaSpecificationExecutor<Prodotto> {

    Optional<Prodotto> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
    boolean existsByAamsCode(String aamsCode);

    boolean existsByBarcodeOrAamsCodeAndIdNot(String barcode,  String aamsCode, Long id);
}
