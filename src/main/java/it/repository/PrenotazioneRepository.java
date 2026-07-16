package it.repository;

import it.model.Prenotazione;
import it.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {

    List<Prenotazione> findByUtenteCreatoId(Integer id);

    @Query("select coalesce(sum(p.costoTotale),0.0)From Prenotazione p where p.utenteCreato.id = :utenteId")
    Double getTotaleSpesoDaUtente(@Param("utenteId")Integer utenteId);
}
