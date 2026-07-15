package it.service;

import it.dto.PagamentoDto;
import it.dto.PrenotazioneDto;
import it.dto.UtenteDto;
import it.mapper.Converter;
import it.mapper.PagamentoMapper;
import it.mapper.PrenotazioneMapper;
import it.model.Pagamento;
import it.model.Prenotazione;
import it.repository.PagamentoRepository;
import it.repository.PrenotazioneRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService extends AbstractService<Prenotazione, PrenotazioneDto>{
    private final PrenotazioneMapper prenotazioneMapper;
    private final PrenotazioneRepository prenotazioneRepository;
    protected PrenotazioneService(JpaRepository<Prenotazione, Integer> repository, Converter<Prenotazione, PrenotazioneDto> converter, PrenotazioneMapper prenotazioneMapper, PrenotazioneRepository prenotazioneRepository) {
        super(repository, converter);
        this.prenotazioneMapper = prenotazioneMapper;
        this.prenotazioneRepository = prenotazioneRepository;
    }


}
