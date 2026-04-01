package it.tabacchi.prodotti.prodottomagazzino;

import it.tabacchi.enums.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdottoMagazzinoRepository extends JpaRepository<ProdottoMagazzino, Long> {

    Optional<ProdottoMagazzino> findByProdottoBarcode(String barcode);
    boolean existsByProdottoBarcode(String barcode);
    boolean existsByProdottoBarcodeAndIdNot(String barcode, Long id);

    Page<ProdottoMagazzino> findAllByProdottoCategoria(Categoria categoria, Pageable pageable);
}
