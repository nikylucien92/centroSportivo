package it.service;

import it.dto.UtenteDto;
import it.mapper.Converter;
import it.mapper.PrenotazioneMapper;
import it.mapper.UtenteMapper;
import it.model.Utente;
import it.repository.PrenotazioneRepository;
import it.repository.UtenteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UtenteService extends AbstractService<Utente, UtenteDto> {
    private final UtenteMapper utenteMapper;
    private final PrenotazioneMapper prenotazioneMapper;
    private final UtenteRepository utenteRepository;
    protected UtenteService(JpaRepository<Utente, Integer> repository, Converter<Utente, UtenteDto> converter, UtenteMapper utenteMapper, PrenotazioneMapper prenotazioneMapper, UtenteRepository utenteRepository) {
        super(repository, converter);
        this.utenteMapper = utenteMapper;
        this.prenotazioneMapper = prenotazioneMapper;
        this.utenteRepository = utenteRepository;
    }
}
