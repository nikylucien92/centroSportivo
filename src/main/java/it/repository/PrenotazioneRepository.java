package it.repository;

import it.model.Prenotazione;
import it.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {



    List<Prenotazione> findByUtenteCreatoId(Integer id);
}
