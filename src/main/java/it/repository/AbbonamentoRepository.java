package it.repository;

import it.enumerated.StatoAbbonamentoEnum;
import it.model.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;
import it.enumerated.AbbonamentoTypeEnum;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Integer> {


    // Abbonamento di un cliente
    Abbonamento findByUtenteId(Integer idUtente);

    // Ricerca per stato
    List<Abbonamento> findByStato(
            StatoAbbonamentoEnum stato
    );

    // Ricerca per tipologia
    List<Abbonamento> findByTipo(
            AbbonamentoTypeEnum tipo
    );

    // Abbonamenti in scadenza in un periodo
    List<Abbonamento> findByDataFineBetween(
            LocalDate inizio,
            LocalDate fine
    );

    // Abbonamenti scaduti
    List<Abbonamento> findByDataFineBefore(
            LocalDate data
    );

    // Abbonamenti validi oggi
    List<Abbonamento> findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
            LocalDate dataInizio,
            LocalDate dataFine
    );
}
