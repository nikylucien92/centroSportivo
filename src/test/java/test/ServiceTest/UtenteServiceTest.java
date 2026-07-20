package test.ServiceTest;

import it.dto.PrenotazioneDto;
import it.dto.UtenteDto;
import it.enumerated.RuoloEnum;
import it.mapper.PrenotazioneMapper;
import it.mapper.UtenteMapper;
import it.model.Prenotazione;
import it.model.Utente;
import it.repository.PrenotazioneRepository;
import it.repository.UtenteRepository;
import it.service.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ExpressionException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtenteServiceTest {

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private UtenteMapper utenteMapper;

    @Mock
    private PrenotazioneMapper prenotazioneMapper;

    @InjectMocks
    private UtenteService utenteService;

    private Utente utenteEntity;
    private UtenteDto utenteDto;
    private Prenotazione prenotazioneEntity;
    private PrenotazioneDto prenotazioneDto;

    @BeforeEach
    void setUp() {
        // Utente Entity e DTO
        utenteEntity = new Utente();
        utenteEntity.setId(1);
        utenteEntity.setNome("Mario");
        utenteEntity.setCognome("Rossi");
        utenteEntity.setEmail("mario.rossi@example.com");
        utenteEntity.setPassword("oldPassword");
        utenteEntity.setTelefono("123456789");
        utenteEntity.setRuolo(RuoloEnum.USER);

        utenteDto = new UtenteDto();
        utenteDto.setId(1);
        utenteDto.setNome("Mario");
        utenteDto.setEmail("mario.rossi@example.com");

        //  Prenotazione Entity e DTO
        prenotazioneEntity = new Prenotazione();
        prenotazioneDto = new PrenotazioneDto();
    }

    //to admin
    @Test
    void upgradeToAdmin_ShouldUpdateRole_WhenUserExists() throws Exception {
        when(utenteRepository.findById(1)).thenReturn(Optional.of(utenteEntity));
        when(utenteRepository.save(any(Utente.class))).thenReturn(utenteEntity);
        when(utenteMapper.toDTO(any(Utente.class))).thenReturn(utenteDto);
        ArgumentCaptor<Utente> utenteCaptor = ArgumentCaptor.forClass(Utente.class);
        UtenteDto result = utenteService.upgradeToAdmin(1);
        assertNotNull(result);
        verify(utenteRepository, times(1)).save(utenteCaptor.capture());
        Utente utenteSalvato = utenteCaptor.getValue();
        // check se e admin
        assertEquals(RuoloEnum.ADMIN, utenteSalvato.getRuolo());
    }

    @Test
    void upgradeToAdmin_ShouldThrowException_WhenUserDoesNotExist() {
        when(utenteRepository.findById(99)).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> utenteService.upgradeToAdmin(99));
        assertEquals("User not found", exception.getMessage());
    }

    //trova per email
    @Test
    void findByEmail_ShouldReturnUtenteWithPrenotazioni_WhenUserExists() throws Exception {
        String email = "mario.rossi@example.com";
        List<Prenotazione> prenotazioni = List.of(prenotazioneEntity);
        List<PrenotazioneDto> prenotazioniDto = List.of(prenotazioneDto);
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utenteEntity));
        when(prenotazioneRepository.findByUtenteCreatoId(1)).thenReturn(prenotazioni);
        when(prenotazioneMapper.toDTOList(prenotazioni)).thenReturn(prenotazioniDto);
        when(utenteMapper.toDTO(utenteEntity)).thenReturn(utenteDto);
        UtenteDto result = utenteService.findByEmail(email);
        assertNotNull(result);
        assertNotNull(result.getListaPrenotazioni());
        assertEquals(1, result.getListaPrenotazioni().size());
    }


    //find by name
    @Test
    void findByName_ShouldReturnUtente_WhenNameExists() throws Exception {
        String nome = "Mario";
        when(utenteRepository.findByNome(nome)).thenReturn(Optional.of(utenteEntity));
        when(utenteMapper.toDTO(utenteEntity)).thenReturn(utenteDto);
        UtenteDto result = utenteService.findByName(nome);
        assertNotNull(result);
        assertEquals("Mario", result.getNome());
    }

    //nome non esiste
    @Test
    void findByName_ShouldThrowExpressionException_WhenNameDoesNotExist() {
        String nomeInesistente = "Luigi";
        when(utenteRepository.findByNome(nomeInesistente)).thenReturn(Optional.empty());
        assertThrows(ExpressionException.class, () -> utenteService.findByName(nomeInesistente));
    }

    //cambio password
    @Test
    void cambiaPassword_ShouldUpdatePassword_WhenUserExists() throws Exception {
        String email = "mario.rossi@example.com";
        String nuovaPassword = "newSecurePassword";
        when(utenteRepository.findByEmail(email)).thenReturn(Optional.of(utenteEntity));
        utenteService.cambiaPassword(email, nuovaPassword);
        assertEquals(nuovaPassword, utenteEntity.getPassword());
        verify(utenteRepository, times(1)).save(utenteEntity);
    }

    //cambio cell
    @Test
    void cambioCell_ShouldUpdatePhone_WhenUserExists() throws Exception {
        String nuovoTelefono = "987654321";
        when(utenteRepository.findById(1)).thenReturn(Optional.of(utenteEntity));
        when(utenteRepository.save(utenteEntity)).thenReturn(utenteEntity);
        when(utenteMapper.toDTO(utenteEntity)).thenReturn(utenteDto);
        utenteService.cambioCell(1, nuovoTelefono);
        assertEquals(nuovoTelefono, utenteEntity.getTelefono());
        verify(utenteRepository, times(1)).save(utenteEntity);
    }

    //cambio email
    @Test
    void cambioEmail_ShouldUpdateEmail_WhenUserExists() throws Exception {
        // GIVEN
        String nuovaEmail = "mario.nuovo@example.com";
        when(utenteRepository.findById(1)).thenReturn(Optional.of(utenteEntity));
        when(utenteRepository.save(utenteEntity)).thenReturn(utenteEntity);
        when(utenteMapper.toDTO(utenteEntity)).thenReturn(utenteDto);
        utenteService.cambioEmail(1, nuovaEmail);
        assertEquals(nuovaEmail, utenteEntity.getEmail());
        verify(utenteRepository, times(1)).save(utenteEntity);
    }
}