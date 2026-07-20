package test.ServiceTest;

import it.dto.CampoDto;
import it.dto.DisponibilitaCampoDto;
import it.mapper.DisponibilitaCampoMapper;
import it.model.Campo;
import it.model.DisponibilitaCampo;
import it.repository.CampoRepository;
import it.repository.DisponibilitaCampoRepository;
import it.service.DisponibilitaCampoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisponibilitaCampoServiceTest {
    @Mock
    private DisponibilitaCampoRepository disponibilitaCampoRepository;

    @Mock
    private CampoRepository campoRepository;

    @Mock
    private DisponibilitaCampoMapper disponibilitaCampoMapper;

    @InjectMocks
    private DisponibilitaCampoService disponibilitaCampoService;

    private Campo campo;
    private CampoDto campoDto;

    private DisponibilitaCampo disponibilita;
    private DisponibilitaCampoDto disponibilitaCampoDto;

    @BeforeEach
    void setUp() {

        campo = new Campo();
        campo.setId(1);

        campoDto = new CampoDto();
        campoDto.setId(1);

        disponibilita = new DisponibilitaCampo();
        disponibilita.setId(1);
        disponibilita.setData(LocalDate.of(2026,7,20).atStartOfDay());
        disponibilita.setCampo(campo);

        disponibilitaCampoDto = new DisponibilitaCampoDto();
        disponibilitaCampoDto.setId(1);
        disponibilitaCampoDto.setData(LocalDate.of(2026,7,20).atStartOfDay());
        disponibilitaCampoDto.setCampo(campoDto);
    }

    @Test
    void getAllDisponibilita_positive() {

        List<DisponibilitaCampo> lista = List.of(disponibilita);

        when(disponibilitaCampoRepository.findAll())
                .thenReturn(lista);

        when(disponibilitaCampoMapper.toDTO(disponibilita))
                .thenReturn(disponibilitaCampoDto);

        List<DisponibilitaCampoDto> result =
                disponibilitaCampoService.getAllDisponibilita();

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(disponibilitaCampoDto,result.get(0));

        verify(disponibilitaCampoRepository).findAll();
        verify(disponibilitaCampoMapper).toDTO(disponibilita);
    }

    @Test
    void getAllDisponibilita_negative() {

        when(disponibilitaCampoRepository.findAll())
                .thenThrow(new RuntimeException("Errore repository"));

        assertThrows(
                RuntimeException.class,
                () -> disponibilitaCampoService.getAllDisponibilita()
        );

        verify(disponibilitaCampoRepository).findAll();
    }


    @Test
    void getAllDisponibilita_listaVuota() {

        when(disponibilitaCampoRepository.findAll())
                .thenReturn(List.of());

        List<DisponibilitaCampoDto> result =
                disponibilitaCampoService.getAllDisponibilita();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(disponibilitaCampoRepository).findAll();
    }

    @Test
    void getDisponibilitaByData_positive() throws Exception {

        LocalDate data = LocalDate.of(2026,7,20);

        when(disponibilitaCampoRepository.findByData(data))
                .thenReturn(List.of(disponibilita));

        when(disponibilitaCampoMapper.toDTO(disponibilita))
                .thenReturn(disponibilitaCampoDto);

        List<DisponibilitaCampoDto> result =
                disponibilitaCampoService.getDisponibilitaByData(data);

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(disponibilitaCampoDto,result.get(0));

        verify(disponibilitaCampoRepository).findByData(data);
        verify(disponibilitaCampoMapper).toDTO(disponibilita);
    }

    @Test
    void getDisponibilitaByData_negative() {

        LocalDate data = LocalDate.of(2026,7,20);

        when(disponibilitaCampoRepository.findByData(data))
                .thenReturn(List.of());

        Exception exception = assertThrows(
                Exception.class,
                () -> disponibilitaCampoService.getDisponibilitaByData(data)
        );

        assertEquals(
                "Nessuna disponibilità trovata per la data " + data,
                exception.getMessage()
        );

        verify(disponibilitaCampoRepository).findByData(data);
    }


    @Test
    void getDisponibilitaByData_repositoryException() {

        LocalDate data = LocalDate.now();

        when(disponibilitaCampoRepository.findByData(data))
                .thenThrow(new RuntimeException("Errore repository"));

        assertThrows(
                RuntimeException.class,
                () -> disponibilitaCampoService.getDisponibilitaByData(data)
        );

        verify(disponibilitaCampoRepository).findByData(data);
    }


    @Test
    void getDisponibilitaByCampo_positive() throws Exception {

        when(campoRepository.findById(1))
                .thenReturn(Optional.of(campo));

        when(disponibilitaCampoRepository.findByCampoId(1))
                .thenReturn(List.of(disponibilita));

        when(disponibilitaCampoMapper.toDTO(disponibilita))
                .thenReturn(disponibilitaCampoDto);

        List<DisponibilitaCampoDto> result =
                disponibilitaCampoService.getDisponibilitaByCampo(1);

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(disponibilitaCampoDto,result.get(0));

        verify(campoRepository).findById(1);
        verify(disponibilitaCampoRepository).findByCampoId(1);
        verify(disponibilitaCampoMapper).toDTO(disponibilita);
    }

    @Test
    void getDisponibilitaByCampo_negative() {

        when(campoRepository.findById(99))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                Exception.class,
                () -> disponibilitaCampoService.getDisponibilitaByCampo(99)
        );

        assertEquals("Campo non trovato", exception.getMessage());

        verify(campoRepository).findById(99);

        verify(disponibilitaCampoRepository, never())
                .findByCampoId(anyInt());
    }

    @Test
    void getDisponibilitaByCampo_listaVuota() throws Exception {

        when(campoRepository.findById(1))
                .thenReturn(Optional.of(campo));

        when(disponibilitaCampoRepository.findByCampoId(1))
                .thenReturn(List.of());

        List<DisponibilitaCampoDto> result =
                disponibilitaCampoService.getDisponibilitaByCampo(1);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(campoRepository).findById(1);
        verify(disponibilitaCampoRepository).findByCampoId(1);
    }


    @Test
    void getDisponibilitaByCampo_repositoryException() {

        when(campoRepository.findById(1))
                .thenReturn(Optional.of(campo));

        when(disponibilitaCampoRepository.findByCampoId(1))
                .thenThrow(new RuntimeException("Errore repository"));

        assertThrows(
                RuntimeException.class,
                () -> disponibilitaCampoService.getDisponibilitaByCampo(1)
        );

        verify(campoRepository).findById(1);
        verify(disponibilitaCampoRepository).findByCampoId(1);
    }









}
