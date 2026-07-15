package it.service;

import it.dto.PagamentoDto;
import it.dto.PrenotazioneDto;
import it.dto.UtenteDto;
import it.mapper.Converter;
import it.mapper.PagamentoMapper;
import it.mapper.PrenotazioneMapper;
import it.model.Pagamento;
import it.model.Prenotazione;
import it.model.Utente;
import it.repository.PagamentoRepository;
import it.repository.PrenotazioneRepository;
import it.repository.UtenteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService extends AbstractService<Prenotazione, PrenotazioneDto>{
    private final PrenotazioneMapper prenotazioneMapper;
    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteRepository utenteRepository;
    private final EmailService emailService;
    protected PrenotazioneService(JpaRepository<Prenotazione, Integer> repository, Converter<Prenotazione, PrenotazioneDto> converter, PrenotazioneMapper prenotazioneMapper, PrenotazioneRepository prenotazioneRepository, UtenteRepository utenteRepository, EmailService emailService) {
        super(repository, converter);
        this.prenotazioneMapper = prenotazioneMapper;
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteRepository = utenteRepository;
        this.emailService = emailService;
    }

    public PrenotazioneDto effetuaPrenotazione(PrenotazioneDto prenotazioneDto,
                                               Integer utenteId) throws Exception {
        Utente utente=utenteRepository.findById(utenteId)
                .orElseThrow(()->new Exception("Utente non trovato"));
        Prenotazione prenotazione=prenotazioneMapper.toEntity(prenotazioneDto);
        prenotazione.setUtenteCreato(utente);

        Prenotazione saved=prenotazioneRepository.save(prenotazione);

        String emilTo=saved.getUtenteCreato().getEmail();
        String ogetto="Confermata Prenotazione per "+saved.getUtenteCreato().getCognome() + " "+saved.getUtenteCreato().getNome();
        String testo="Buongiotno "+saved.getUtenteCreato().getNome()+
                ", La tua prenotazione per il campo "+ saved.getDisponibilitaCampo()+
                ", Nella giornata di "+saved.getDataPrenotazione().toString()+
                ", Con il prezzo di "+saved.getCostoTotale()+
                ", E stat confermata con succ";
        emailService.sendEmail(emilTo,ogetto,testo);
        return prenotazioneMapper.toDTO(saved);
    }


}
