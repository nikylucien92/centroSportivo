package it.service;

import it.dto.PrenotazioneDto;
import it.dto.UtenteDto;
import it.mapper.Converter;
import it.mapper.PrenotazioneMapper;
import it.mapper.UtenteMapper;
import it.model.Prenotazione;
import it.model.Utente;
import it.repository.PrenotazioneRepository;
import it.repository.UtenteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService extends AbstractService<Utente, UtenteDto> {
    private final UtenteMapper utenteMapper;
    private final PrenotazioneMapper prenotazioneMapper;
    private final UtenteRepository utenteRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    protected UtenteService(JpaRepository<Utente, Integer> repository, Converter<Utente, UtenteDto> converter, UtenteMapper utenteMapper, PrenotazioneMapper prenotazioneMapper, UtenteRepository utenteRepository, PrenotazioneRepository prenotazioneRepository) {
        super(repository, converter);
        this.utenteMapper = utenteMapper;
        this.prenotazioneMapper = prenotazioneMapper;
        this.utenteRepository = utenteRepository;
        this.prenotazioneRepository = prenotazioneRepository;
    }

    public UtenteDto findByEmail(String email) throws Exception {
        Utente utente=utenteRepository.findByEmail(email)
                .orElseThrow(()->new Exception("User not found"));
        List<Prenotazione> prenotazione= prenotazioneRepository.findByUtenteCreatoId(utente.getId());
        List<PrenotazioneDto> prenotazionedto = prenotazioneMapper.toDTOList(prenotazione);

        UtenteDto utentedto= utenteMapper.toDTO(utente);
        utentedto.setListaPrenotazioniDto(prenotazionedto);
        return utentedto;
    }


        public UtenteDto findByName(String nome) throws Exception{
        Utente utente=utenteRepository.findByName(nome).orElseThrow(()-> new ExpressionException("Name not found"));
        UtenteDto utenteDto=utenteMapper.toDTO(utente);
        return utenteDto;
        }




}
