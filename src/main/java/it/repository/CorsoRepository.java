package it.repository;

import it.enumerated.GiorniEnum;
import it.model.Corso;
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
}
