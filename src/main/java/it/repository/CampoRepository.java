package it.repository;

import it.model.Campo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CampoRepository extends JpaRepository<Campo, Integer> {


    // Ricerca campo tramite nome
    Campo findByNome(String nome);


    // Filtra campi per tipologia (Calcetto, Padel, Tennis...)
    List<Campo> findByTipologia(String tipologia);


    // Filtra campi coperti o scoperti
    List<Campo> findByCoperto(Boolean coperto);


    // Campi con prezzo massimo orario
    List<Campo> findByPrezzoLessThanEqual(Double prezzo);


    // Verifica se esiste un campo con quel nome
    boolean existsByNome(String nome);


    // Ricerca combinata: tipologia + copertura
    List<Campo> findByTipologiaAndCoperto(
            String tipologia,
            Boolean coperto
    );

}
