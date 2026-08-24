package it.repository;

import it.model.Prenotazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {

    List<Prenotazione> findByUtenteCreatoId(Integer id);

    @Query("select coalesce(sum(p.costoTotale),0.0)From Prenotazione p where p.utenteCreato.id = :utenteId")
    Double getTotaleSpesoDaUtente(@Param("utenteId")Integer utenteId);

    List<Prenotazione> findByDataPrenotazioneBetween(LocalDateTime inizio, LocalDateTime fine);


    @Query("""
    SELECT p
    FROM Prenotazione p
    WHERE p.utenteCreato.id = :idUtente
    ORDER BY p.dataPrenotazione DESC
""")
    Page<Prenotazione> findPrenotazioniByUtente(@Param("idUtente") Integer idUtente, Pageable pageable);


}
