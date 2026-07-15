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

import java.time.LocalDate;
import java.time.LocalTime;

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

        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new Exception("Utente non trovato"));

        Prenotazione prenotazione = prenotazioneMapper.toEntity(prenotazioneDto);
        prenotazione.setUtenteCreato(utente);

        Prenotazione saved = prenotazioneRepository.save(prenotazione);

        LocalDate data = saved.getDataPrenotazione().toLocalDate();
        LocalTime ora = saved.getDataPrenotazione().toLocalTime();

        String emailTo = saved.getUtenteCreato().getEmail();

        String oggetto = "Confermata Prenotazione per "
                + saved.getUtenteCreato().getCognome()
                + " "
                + saved.getUtenteCreato().getNome();


        String testo = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
            </head>

            <body style="font-family: Arial, sans-serif; background-color:#f4f4f4; padding:20px;">

                <div style="
                    max-width:600px;
                    margin:auto;
                    background:white;
                    padding:30px;
                    border-radius:10px;
                    box-shadow:0 2px 10px rgba(0,0,0,0.1);
                ">

                    <h2 style="color:#28a745; text-align:center;">
                        ✅ Prenotazione Confermata
                    </h2>

                    <p>
                        Buongiorno <strong>%s</strong>,
                    </p>

                    <p>
                        La tua prenotazione è stata confermata con successo.
                    </p>

                    <hr>

                    <h3>Dettagli prenotazione</h3>

                    <table style="
                        width:100%%;
                        border-collapse:collapse;
                    ">
                        <tr>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                Campo
                            </td>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                <strong>%s</strong>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                Data
                            </td>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                <strong>%s</strong>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                Ora
                            </td>
                            <td style="padding:8px; border-bottom:1px solid #ddd;">
                                <strong>%s</strong>
                            </td>
                        </tr>

                        <tr>
                            <td style="padding:8px;">
                                Costo totale
                            </td>
                            <td style="padding:8px;">
                                <strong>€ %.2f</strong>
                            </td>
                        </tr>
                    </table>


                    <p style="margin-top:25px;">
                        Ti aspettiamo presso il nostro centro sportivo.
                    </p>

                    <p style="color:#777; font-size:12px; text-align:center;">
                        Centro Sportivo © 2026
                    </p>

                </div>

            </body>
            </html>
            """.formatted(
                saved.getUtenteCreato().getNome(),
                saved.getDisponibilitaCampo().getCampo().getNome(),
                data,
                ora,
                saved.getCostoTotale()
        );


        emailService.sendEmail(emailTo, oggetto, testo);

        return prenotazioneMapper.toDTO(saved);
    }

//    public PrenotazioneDto effetuaPrenotazione(PrenotazioneDto prenotazioneDto,
//                                               Integer utenteId) throws Exception {
//        Utente utente=utenteRepository.findById(utenteId)
//                .orElseThrow(()->new Exception("Utente non trovato"));
//        Prenotazione prenotazione=prenotazioneMapper.toEntity(prenotazioneDto);
//        prenotazione.setUtenteCreato(utente);
//
//        Prenotazione saved=prenotazioneRepository.save(prenotazione);
//
//        LocalDate data = saved.getDataPrenotazione().toLocalDate();
//        LocalTime ora = saved.getDataPrenotazione().toLocalTime();
//        String emilTo=saved.getUtenteCreato().getEmail();
//        String ogetto="Confermata Prenotazione per "+saved.getUtenteCreato().getCognome() + " "+saved.getUtenteCreato().getNome();
//        String testo="Buongiotno "+saved.getUtenteCreato().getNome()+
//                ", La tua prenotazione per il campo "+ saved.getDisponibilitaCampo()
//                .getCampo().getNome()+
//                ", Nella giornata di " + data + " alle ore " + ora +
//                ", Con il prezzo di "+saved.getCostoTotale()+
//                ", E stat confermata con succ";
//        emailService.sendEmail(emilTo,ogetto,testo);
//        return prenotazioneMapper.toDTO(saved);
//    }


}
