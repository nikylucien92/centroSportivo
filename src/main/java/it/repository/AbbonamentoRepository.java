package it.repository;

import it.model.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;
import it.enumerated.AbbonamentoTypeEnum;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Integer> {


    // Ricerca per tipo di abbonamento
    List<Abbonamento> findByTipo(AbbonamentoTypeEnum tipo);


    // Ricerca abbonamenti per stato
    List<Abbonamento> findByStato(String stato);


    // Ricerca abbonamenti per prezzo
    List<Abbonamento> findByPrezzo(Double prezzo);


    // Abbonamenti con prezzo massimo
    List<Abbonamento> findByPrezzoLessThanEqual(Double prezzo);

    // Cerca abbonamento associato ad un utente
    Abbonamento findByUtenteId(Integer idUtente);


}
