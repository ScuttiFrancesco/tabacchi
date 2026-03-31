package it.tabacchi.prodotti.movimentomagazzino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface MovimentoMagazzinoRepository extends JpaRepository<MovimentoMagazzino, Long> {

    @Query("SELECT COALESCE(SUM(m.prezzoVenditaTotale ), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.data BETWEEN :start AND :end")
    BigDecimal getRicavoTotale(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(SUM(m.prezzoVenditaTotale - m.prezzoAcquistoTotale), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.data BETWEEN :start AND :end")
    BigDecimal getGuadagnoTotale(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(AVG(m.prezzoVenditaTotale - m.prezzoAcquistoTotale), 0) AS big_decimal " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.data BETWEEN :start AND :end")
    BigDecimal getGuadagnoMedio(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(MIN(m.prezzoVenditaTotale - m.prezzoAcquistoTotale), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.data BETWEEN :start AND :end")
    BigDecimal getGuadagnoMinimo(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COALESCE(MAX(m.prezzoVenditaTotale - m.prezzoAcquistoTotale), 0) " +
            "FROM MovimentoMagazzino m " +
            "WHERE m.tipoMovimento = 'VENDITA' " +
            "AND m.data BETWEEN :start AND :end")
    BigDecimal getGuadagnoMassimo(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
