package progetto.centroSportivo.centroSportivo.ServiceTest;

import it.dto.CorsoDto;
import it.enumerated.GiorniEnum;
import it.model.Corso;
import it.repository.CorsoRepository;
import it.service.CorsoService;
import it.mapper.CorsoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorsoServiceTest {

    //datbase
    @Mock
    private CorsoRepository corsoRepository;

    //mapper
    @Mock
    private CorsoMapper corsoMapper;

    @InjectMocks
    private CorsoService corsoService;

    private Corso corsoEntity;
    private CorsoDto corsoDto;

    @BeforeEach
    void setUp() {
        //classe mock
        corsoEntity = new Corso();
        corsoEntity.setId(1);
        corsoEntity.setNome("Pilates Avanzato");
        corsoEntity.setSport("Pilates");
        corsoEntity.setLivello("Avanzato");
        corsoEntity.setGiorni(GiorniEnum.LUNEDI);
        corsoEntity.setOraInizio(LocalTime.of(18, 0));
        corsoEntity.setOraFine(LocalTime.of(19, 0));
        corsoEntity.setPrezzo(50.0);
        corsoDto = new CorsoDto();
    }

    //choose daya
    @Test
    void trovaPerGiorno_ShouldReturnDtoList_WhenCoursesExist() {
        GiorniEnum giorno = GiorniEnum.LUNEDI;
        List<Corso> entityList = List.of(corsoEntity);
        List<CorsoDto> dtoList = List.of(corsoDto);
        when(corsoRepository.findByGiorni(giorno)).thenReturn(entityList);
        when(corsoMapper.toDTOList(entityList)).thenReturn(dtoList);
        List<CorsoDto> result = corsoService.trovaPerGiorno(giorno);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(corsoRepository, times(1)).findByGiorni(giorno);
        verify(corsoMapper, times(1)).toDTOList(entityList);
    }

    //lista vuora se non ce nessun corso
    @Test
    void trovaPerGiorno_ShouldReturnEmptyList_WhenNoCoursesFound() {
        GiorniEnum giorno = GiorniEnum.DOMENICA;
        when(corsoRepository.findByGiorni(giorno)).thenReturn(Collections.emptyList());
        when(corsoMapper.toDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());
        List<CorsoDto> result = corsoService.trovaPerGiorno(giorno);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(corsoRepository, times(1)).findByGiorni(giorno);
        verify(corsoMapper, times(1)).toDTOList(Collections.emptyList());
    }

    //per sport
    @Test
    void trovaPerSport_ShouldReturnDtoList_WhenSportExists() {
        String sport = "Pilates";
        List<Corso> entityList = List.of(corsoEntity);
        List<CorsoDto> dtoList = List.of(corsoDto);
        when(corsoRepository.findBySportIgnoreCase(sport)).thenReturn(entityList);
        when(corsoMapper.toDTOList(entityList)).thenReturn(dtoList);
        List<CorsoDto> result = corsoService.trovaPerSport(sport);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(corsoRepository, times(1)).findBySportIgnoreCase(sport);
        verify(corsoMapper, times(1)).toDTOList(entityList);
    }

    @Test
    void trovaPerSport_ShouldReturnEmptyList_WhenSportDoesNotExist() {
        String sportInesistente = "Quidditch";
        when(corsoRepository.findBySportIgnoreCase(sportInesistente)).thenReturn(Collections.emptyList());
        when(corsoMapper.toDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());
        List<CorsoDto> result = corsoService.trovaPerSport(sportInesistente);
        assertTrue(result.isEmpty());
        verify(corsoRepository, times(1)).findBySportIgnoreCase(sportInesistente);
        verify(corsoMapper, times(1)).toDTOList(Collections.emptyList());
    }


    //trova corso dopo lora data
    @Test
    void trovaDaOra_ShouldReturnDtoList() {
        LocalTime ora = LocalTime.of(14, 30);
        List<Corso> entityList = List.of(corsoEntity);
        List<CorsoDto> dtoList = List.of(corsoDto);
        when(corsoRepository.findByOraInizioGreaterThanEqual(ora)).thenReturn(entityList);
        when(corsoMapper.toDTOList(entityList)).thenReturn(dtoList);
        List<CorsoDto> result = corsoService.trovaDaOra(ora);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(corsoRepository, times(1)).findByOraInizioGreaterThanEqual(ora);
        verify(corsoMapper, times(1)).toDTOList(entityList);
    }


    //corso per campo
    @Test
    void ottieniCorsiPerCampo_ShouldReturnDtoList_WhenCampoIdIsValid() {
        Integer campoId = 5;
        List<Corso> entityList = List.of(corsoEntity);
        List<CorsoDto> dtoList = List.of(corsoDto);
        when(corsoRepository.findByCampoId(campoId)).thenReturn(entityList);
        when(corsoMapper.toDTOList(entityList)).thenReturn(dtoList);
        List<CorsoDto> result = corsoService.ottieniCorsiPerCampo(campoId);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(corsoRepository, times(1)).findByCampoId(campoId);
        verify(corsoMapper, times(1)).toDTOList(entityList);
    }
}