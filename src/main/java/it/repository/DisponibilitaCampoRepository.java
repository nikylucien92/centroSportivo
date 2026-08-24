package it.repository;

import it.model.DisponibilitaCampo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilitaCampoRepository extends JpaRepository<DisponibilitaCampo,Integer> {

    List<DisponibilitaCampo> findByData(LocalDate data);
    List<DisponibilitaCampo> findByCampoId(Integer idCampo);
}
