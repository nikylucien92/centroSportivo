package progetto.centroSportivo.centroSportivo.ControllerTest;

import it.dto.PrenotazioneDto;
import it.mapper.Converter;
import it.mapper.PrenotazioneMapper;
import it.model.Prenotazione;
import it.model.Utente;
import it.repository.PrenotazioneRepository;
import it.repository.UtenteRepository;
import it.service.EmailService;
import it.service.PrenotazioneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrenotazioneServiceTest {

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private PrenotazioneMapper prenotazioneMapper;

    @Mock
    private Converter<Prenotazione, PrenotazioneDto> converter;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PrenotazioneService prenotazioneService;

    private Utente utente;
    private Prenotazione prenotazione;
    private PrenotazioneDto prenotazioneDto;

    @BeforeEach
    void setUp() {

        utente = new Utente();
        utente.setId(1);
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setEmail("mario@gmail.com");

        prenotazione = new Prenotazione();
        prenotazione.setId(1);
        prenotazione.setCostoTotale(80.0);
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        prenotazioneDto = new PrenotazioneDto();
    }

    @Test
    void testEffettuaPrenotazione() throws Exception {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneMapper.toEntity(prenotazioneDto))
                .thenReturn(prenotazione);

        when(prenotazioneRepository.save(any(Prenotazione.class)))
                .thenReturn(prenotazione);

        when(prenotazioneMapper.toDTO(prenotazione))
                .thenReturn(prenotazioneDto);

        PrenotazioneDto result =
                prenotazioneService.effetuaPrenotazione(prenotazioneDto, 1);

        assertNotNull(result);

        verify(utenteRepository).findById(1);
        verify(prenotazioneRepository).save(any(Prenotazione.class));
        verify(emailService).sendEmail(
                anyString(),
                anyString(),
                anyString()
        );
    }

    @Test
    void testUtenteNonTrovato() {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                Exception.class,
                () -> prenotazioneService.effetuaPrenotazione(prenotazioneDto, 1)
        );

        assertEquals("Utente non trovato", exception.getMessage());

        verify(prenotazioneRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any(), any(), any());
    }
}
