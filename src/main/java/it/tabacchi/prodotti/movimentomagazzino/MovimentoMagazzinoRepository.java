package it.tabacchi.prodotti.movimentomagazzino;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovimentoMagazzinoRepository extends JpaRepository<MovimentoMagazzino, Long>, JpaSpecificationExecutor<MovimentoMagazzino> {

    @Query("SELECT COALESCE(SUM(m.ricavo ), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.dataMovimento BETWEEN :start AND :end")
    BigDecimal getRicavoTotale(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(m.guadagno), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.dataMovimento BETWEEN :start AND :end")
    BigDecimal getGuadagnoTotale(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(AVG(m.guadagno), 0) AS big_decimal " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.dataMovimento BETWEEN :start AND :end")
    BigDecimal getGuadagnoMedio(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(MIN(m.guadagno), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.dataMovimento BETWEEN :start AND :end")
    BigDecimal getGuadagnoMinimo(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(MAX(m.guadagno), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.dataMovimento BETWEEN :start AND :end")
    BigDecimal getGuadagnoMassimo(@Param("start") LocalDate start, @Param("end") LocalDate end);


    @Override
    @EntityGraph(attributePaths = {"dettagliMovimento", "dettagliMovimento.prodotto"})
    Optional<MovimentoMagazzino> findById(Long id);
}
