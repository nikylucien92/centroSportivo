package test.ServiceTest;

import it.dto.DisponibilitaCampoDto;
import it.dto.PrenotazioneDto;
import it.mapper.Converter;
import it.mapper.PrenotazioneMapper;
import it.model.Campo;
import it.model.DisponibilitaCampo;
import it.model.Prenotazione;
import it.model.Utente;
import it.repository.DisponibilitaCampoRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PrenotazioneServiceTest {

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private DisponibilitaCampoRepository disponibilitaCampoRepository;

    @Mock
    private Converter<Prenotazione, PrenotazioneDto> converter;

    @Mock
    private PrenotazioneMapper prenotazioneMapper;

    @Mock
    private EmailService emailService;



    @InjectMocks
    private PrenotazioneService prenotazioneService;

    private Prenotazione prenotazione;
    private PrenotazioneDto prenotazioneDto;
    private Utente utente;
    private DisponibilitaCampo disponibilitaCampo;
    private Campo campo;

    @BeforeEach
    void setUp() {

        campo = new Campo();
        campo.setId(1);
        campo.setNome("Campo 1");

        disponibilitaCampo = new DisponibilitaCampo();
        disponibilitaCampo.setId(1);
        disponibilitaCampo.setCampo(campo);

        utente = new Utente();
        utente.setId(1);
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setEmail("mario@test.it");


        campo = new Campo();
        campo.setId(1);
        campo.setNome("Campo A");

        disponibilitaCampo = new DisponibilitaCampo();
        disponibilitaCampo.setId(1);
        disponibilitaCampo.setCampo(campo);
        disponibilitaCampo.setDisponibilita(true);

        prenotazione = new Prenotazione();
        prenotazione.setId(1);
        prenotazione.setUtenteCreato(utente);
        prenotazione.setDisponibilitaCampo(disponibilitaCampo);
        prenotazione.setDataPrenotazione(LocalDateTime.now());
        prenotazione.setCostoTotale(20.0);

        prenotazioneDto = new PrenotazioneDto();
        prenotazioneDto.setId(1);
    }

    @Test
    void trovaPrenotazioniPerData_positive() {

        LocalDate data = LocalDate.now();

        List<Prenotazione> lista =
                List.of(prenotazione);

        List<PrenotazioneDto> listaDto =
                List.of(prenotazioneDto);

        when(prenotazioneRepository.findByDataPrenotazioneBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(lista);

        when(prenotazioneMapper.toDTOList(lista))
                .thenReturn(listaDto);

        List<PrenotazioneDto> result =
                prenotazioneService.trovaPrenotazioniPerData(data);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(prenotazioneRepository)
                .findByDataPrenotazioneBetween(
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        verify(prenotazioneMapper)
                .toDTOList(lista);
    }

    @Test
    void trovaPrenotazioniPerData_negative() {

        LocalDate data = LocalDate.now();

        when(prenotazioneRepository.findByDataPrenotazioneBetween(
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(List.of());

        when(prenotazioneMapper.toDTOList(List.of()))
                .thenReturn(List.of());

        List<PrenotazioneDto> result =
                prenotazioneService.trovaPrenotazioniPerData(data);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }



    @Test
    void effetuaPrenotazione_positive() throws Exception {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneMapper.toEntity(prenotazioneDto))
                .thenReturn(prenotazione);

        when(disponibilitaCampoRepository.findById(1))
                .thenReturn(Optional.of(disponibilitaCampo));

        prenotazioneDto.setDisponibilitaCampo(new DisponibilitaCampoDto());
        prenotazioneDto.getDisponibilitaCampo().setId(1);

        when(prenotazioneRepository.save(any(Prenotazione.class)))
                .thenReturn(prenotazione);

        when(prenotazioneMapper.toDTO(prenotazione))
                .thenReturn(prenotazioneDto);

        PrenotazioneDto result =
                prenotazioneService.effetuaPrenotazione(prenotazioneDto,1);

        assertNotNull(result);

        verify(prenotazioneRepository).save(any(Prenotazione.class));

        verify(emailService).sendEmail(
                anyString(),
                anyString(),
                anyString()
        );
    }

    @Test
    void effetuaPrenotazione_negativeUtenteNonTrovato() {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> prenotazioneService.effetuaPrenotazione(prenotazioneDto,1)
        );

        verify(utenteRepository).findById(1);
    }


    @Test
    void effetuaPrenotazione_negativeDisponibilitaNonTrovata() {

        prenotazioneDto.setDisponibilitaCampo(new DisponibilitaCampoDto());
        prenotazioneDto.getDisponibilitaCampo().setId(1);

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneMapper.toEntity(prenotazioneDto))
                .thenReturn(prenotazione);

        when(disponibilitaCampoRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> prenotazioneService.effetuaPrenotazione(prenotazioneDto,1)
        );
    }


    @Test
    void cancellaPrenotazione_positive() throws Exception {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneRepository.findById(1))
                .thenReturn(Optional.of(prenotazione));

        when(prenotazioneRepository.findByUtenteCreatoId(1))
                .thenReturn(List.of());

        when(prenotazioneMapper.toDTOList(anyList()))
                .thenReturn(List.of());

        List<PrenotazioneDto> result =
                prenotazioneService.cancellaPrenotazione(1,1);

        assertNotNull(result);

        verify(prenotazioneRepository).delete(prenotazione);

        verify(disponibilitaCampoRepository)
                .save(disponibilitaCampo);
    }

    @Test
    void cancellaPrenotazione_negativeUtenteNonTrovato() {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> prenotazioneService.cancellaPrenotazione(1,1)
        );
    }

    @Test
    void cancellaPrenotazione_negativePrenotazioneNonTrovata() {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> prenotazioneService.cancellaPrenotazione(1,1)
        );

        verify(prenotazioneRepository).findById(1);
    }


    @Test
    void cancellaPrenotazione_negativeUtenteDiverso() {

        Utente altroUtente = new Utente();
        altroUtente.setId(2);

        prenotazione.setUtenteCreato(altroUtente);

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneRepository.findById(1))
                .thenReturn(Optional.of(prenotazione));

        assertThrows(
                Exception.class,
                () -> prenotazioneService.cancellaPrenotazione(1,1)
        );
    }

    @Test
    void calcolaSpesaTotale_positive() {

        when(prenotazioneRepository.getTotaleSpesoDaUtente(1))
                .thenReturn(120.0);

        Double result =
                prenotazioneService.calcolaSpesaTotale(1);

        assertNotNull(result);
        assertEquals(120.0, result);

        verify(prenotazioneRepository)
                .getTotaleSpesoDaUtente(1);
    }

    @Test
    void calcolaSpesaTotale_negative() {

        when(prenotazioneRepository.getTotaleSpesoDaUtente(1))
                .thenThrow(new RuntimeException());

        assertThrows(
                RuntimeException.class,
                () -> prenotazioneService.calcolaSpesaTotale(1)
        );
    }

    @Test
    void getListaPrenotazioni_positive() throws Exception {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.of(utente));

        when(prenotazioneRepository.findByUtenteCreatoId(1))
                .thenReturn(List.of(prenotazione));

        when(prenotazioneMapper.toDTO(prenotazione))
                .thenReturn(prenotazioneDto);

        List<PrenotazioneDto> result =
                prenotazioneService.getListaPrenotazioni(1);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(prenotazioneRepository)
                .findByUtenteCreatoId(1);
    }

    @Test
    void getListaPrenotazioni_negative() {

        when(utenteRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> prenotazioneService.getListaPrenotazioni(1)
        );
    }

    @Test
    void getListaPrenotazioniConPaginazione_positive() throws Exception {

        Page<Prenotazione> page =
                new PageImpl<>(List.of(prenotazione));

        Page<PrenotazioneDto> pageDto =
                new PageImpl<>(List.of(prenotazioneDto));

        when(prenotazioneRepository.findByUtenteCreatoId(1))
                .thenReturn(List.of(prenotazione));

        when(prenotazioneRepository.findPrenotazioniByUtente(
                eq(1),
                any(Pageable.class)))
                .thenReturn(page);

        when(prenotazioneMapper.toDTOPage(page))
                .thenReturn(pageDto);

        Page<PrenotazioneDto> result =
                prenotazioneService.getListaPrenotazioniConPaginazione(1);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());

        verify(prenotazioneRepository)
                .findPrenotazioniByUtente(
                        eq(1),
                        any(Pageable.class));
    }

    @Test
    void getListaPrenotazioniConPaginazione_negative() {

        when(prenotazioneRepository.findByUtenteCreatoId(1))
                .thenThrow(new RuntimeException());

        assertThrows(
                RuntimeException.class,
                () -> prenotazioneService.getListaPrenotazioniConPaginazione(1)
        );
    }
    
}