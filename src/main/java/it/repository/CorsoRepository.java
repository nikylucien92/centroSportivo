package it.repository;

import it.enumerated.GiorniEnum;
import it.model.Corso;
import it.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public interface CorsoRepository extends JpaRepository<Corso,Integer> {
    List<Corso> findBySportIgnoreCase(String sport);

    List<Corso> findByGiorni(GiorniEnum giorno);

    List<Corso> findByCampoId(Integer campoId);

    List<Corso> findByOraInizioGreaterThanEqual(LocalTime ora);

    List<Corso> findByPrezzoLessThanEqual(BigDecimal prezzoMax);

    @Query("SELECT c FROM Corso c WHERE " +
            "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:livello IS NULL OR c.livello = :livello) AND " +
            "(:sport IS NULL OR LOWER(c.sport) = LOWER(:sport)) AND " +
            "(:campoId IS NULL OR c.campo.id = :campoId) AND " +
            "(:giorno IS NULL OR c.giorni = :giorno) AND " +
            "(:oraInizio IS NULL OR c.oraInizio >= :oraInizio) AND " +
            "(:prezzoMax IS NULL OR c.prezzo <= :prezzoMax)")
    List<Corso> findByFiltriDinamici(
            @Param("nome") String nome,
            @Param("livello") String livello,
            @Param("sport") String sport,
            @Param("campoId") Integer campoId,
            @Param("giorno") GiorniEnum giorno,
            @Param("oraInizio") LocalTime oraInizio,
            @Param("prezzoMax") BigDecimal prezzoMax);
}
