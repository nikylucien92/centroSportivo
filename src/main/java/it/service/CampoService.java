package it.service;

import it.dto.AbbonamentoDto;
import it.dto.CampoDto;
import it.dto.UtenteDto;
import it.mapper.AbbonamentoMapper;
import it.mapper.CampoMapper;
import it.mapper.Converter;
import it.model.Abbonamento;
import it.model.Campo;
import it.model.DisponibilitaCampo;
import it.model.Utente;
import it.repository.AbbonamentoRepository;
import it.repository.CampoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CampoService extends AbstractService<Campo, CampoDto> {
    private final CampoMapper campomapper;
    private final CampoRepository campoRepository;
    protected CampoService(JpaRepository<Campo, Integer> repository,
                           Converter<Campo, CampoDto> converter, CampoMapper campomapper, CampoRepository campoRepository) {
        super(repository, converter);
        this.campomapper = campomapper;
        this.campoRepository = campoRepository;
    }
    public CampoDto findByName(Integer id) throws Exception{
        Campo campo=campoRepository.findById(id).orElseThrow(()-> new ExpressionException("Campo non trovato"));
        CampoDto campoDto=campomapper.toDTO(campo);
        return campoDto;
    }

    public CampoDto prenotaCampo(Integer id, LocalDate data, LocalTime ora) {
        Campo campo = campoRepository.findById(id).orElseThrow(() -> new ExpressionException("Campo non trovato"));

        for (DisponibilitaCampo disponibilita : campo.getListaDisponibilita()) {
            if (disponibilita.getDisponibilita()) {
                //se disponibile , mi setti la disponibilità a false , quindi che è occupato
                disponibilita.setDisponibilita(false);
                //settare l'orario e giorno in cui si prenota
                if(disponibilita.getData().equals(data) && disponibilita.getOraInizio().equals(ora))
                disponibilita.setDisponibilita(false);

                campoRepository.save(campo);

            }

        }            return campomapper.toDTO(campo);

    }

    //cancella prenotazione


}
