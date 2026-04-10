package it.tabacchi.prodotti.prezzostorico;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrezzoStoricoRepository extends JpaRepository<PrezzoStorico, Long>, JpaSpecificationExecutor<PrezzoStorico> {

    @Query("SELECT p FROM PrezzoStorico p ORDER BY p.id ASC LIMIT 1")
    Optional<PrezzoStorico> findOne();

    @Override
    @EntityGraph(attributePaths = {"prodotto"})
    Optional<PrezzoStorico> findById(Long id);
}
