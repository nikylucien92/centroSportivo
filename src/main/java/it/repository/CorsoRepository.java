package it.repository;

import it.model.Corso;
import it.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorsoRepository extends JpaRepository<Corso,Integer> {
}
