package it.tabacchi.prodotti.dettagliomovimento;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DettaglioMovimentoRepository extends JpaRepository<DettaglioMovimento, Long> {

    @Override
    @EntityGraph(attributePaths = {"prodotto"})
    Optional<DettaglioMovimento> findById(Long id);
}
