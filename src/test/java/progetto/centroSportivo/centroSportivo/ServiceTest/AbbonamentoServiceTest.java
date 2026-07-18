package progetto.centroSportivo.centroSportivo.ServiceTest;

import it.dto.AbbonamentoDto;
import it.dto.UtenteDto;
import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;
import it.mapper.AbbonamentoMapper;
import it.model.Abbonamento;
import it.model.Utente;
import it.repository.AbbonamentoRepository;
import it.service.AbbonamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AbbonamentoServiceTest {

    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbbonamentoServiceTest.class);


    @Mock
    private AbbonamentoRepository abbonamentoRepository;

    @Mock
    private AbbonamentoMapper abbonamentoMapper;

    @InjectMocks
    private AbbonamentoService abbonamentoService;

    private Abbonamento abbonamento1;

    private AbbonamentoDto abbonamentoDto;


    @BeforeEach
    void setUp() {

        Utente utente = new Utente();
        utente.setId(1);


        UtenteDto utenteDto = new UtenteDto();
        utenteDto.setId(1);


        abbonamento1 = new Abbonamento(
                1,
                AbbonamentoTypeEnum.MENSILE,
                LocalDate.of(2026, 7, 18),
                LocalDate.of(2026, 8, 18),
                BigDecimal.valueOf(50.00),
                StatoAbbonamentoEnum.ATTIVO,
                utente
        );

        abbonamentoDto = new AbbonamentoDto(
                2,
                AbbonamentoTypeEnum.MENSILE,
                LocalDate.of(2026, 7, 18),
                LocalDate.of(2026, 8, 18),
                BigDecimal.valueOf(50.00),
                StatoAbbonamentoEnum.ATTIVO,
                utenteDto
        );

    }

    @Test
    void insert_positive() {

        AbbonamentoDto dto = abbonamentoDto;
        Abbonamento abbonamento = abbonamento1;

        when(abbonamentoMapper.toEntity(dto))
                .thenReturn(abbonamento);

        when(abbonamentoRepository.save(abbonamento))
                .thenReturn(abbonamento);

        when(abbonamentoMapper.toDTO(abbonamento))
                .thenReturn(dto);

        AbbonamentoDto result = abbonamentoService.insert(dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(abbonamentoMapper).toEntity(dto);
        verify(abbonamentoRepository).save(abbonamento);
        verify(abbonamentoMapper).toDTO(abbonamento);
    }

    @Test
    void insert_negative() {

        AbbonamentoDto dto = abbonamentoDto;
        Abbonamento abbonamento = abbonamento1;

        when(abbonamentoMapper.toEntity(dto))
                .thenReturn(abbonamento);

        when(abbonamentoRepository.save(abbonamento))
                .thenThrow(new RuntimeException("Errore salvataggio abbonamento"));

        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.insert(dto)
        );

        verify(abbonamentoMapper)
                .toEntity(dto);

        verify(abbonamentoRepository)
                .save(abbonamento);
    }


    @Test
    void update_positive() {

        AbbonamentoDto dto = abbonamentoDto;
        Abbonamento abbonamento = abbonamento1;

        when(abbonamentoMapper.toEntity(dto))
                .thenReturn(abbonamento);

        when(abbonamentoRepository.save(abbonamento))
                .thenReturn(abbonamento);

        when(abbonamentoMapper.toDTO(abbonamento))
                .thenReturn(dto);

        AbbonamentoDto result = abbonamentoService.update(dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(abbonamentoMapper).toEntity(dto);
        verify(abbonamentoRepository).save(abbonamento);
        verify(abbonamentoMapper).toDTO(abbonamento);
    }

    @Test
    void update_negative() {

        AbbonamentoDto dto = abbonamentoDto;
        Abbonamento abbonamento = abbonamento1;

        when(abbonamentoMapper.toEntity(dto))
                .thenReturn(abbonamento);

        when(abbonamentoRepository.save(abbonamento))
                .thenThrow(new RuntimeException("Errore aggiornamento"));

        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.update(dto)
        );

        verify(abbonamentoMapper)
                .toEntity(dto);

        verify(abbonamentoRepository)
                .save(abbonamento);
    }

    @Test
    void delete_positive() {

        Integer id = 1;

        doNothing()
                .when(abbonamentoRepository)
                .deleteById(id);

        abbonamentoService.delete(id);

        verify(abbonamentoRepository)
                .deleteById(id);
    }

    @Test
    void delete_negative() {

        Integer id = 99;

        doThrow(new RuntimeException("Errore cancellazione"))
                .when(abbonamentoRepository)
                .deleteById(id);


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.delete(id)
        );


        verify(abbonamentoRepository)
                .deleteById(id);
    }

    @Test
    void read_positive() {

        AbbonamentoDto dto = abbonamentoDto;
        Abbonamento abbonamento = abbonamento1;

        when(abbonamentoRepository.findById(1))
                .thenReturn(Optional.of(abbonamento));

        when(abbonamentoMapper.toDTO(abbonamento))
                .thenReturn(dto);

        AbbonamentoDto result = abbonamentoService.read(1);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(abbonamentoRepository)
                .findById(1);

        verify(abbonamentoMapper)
                .toDTO(abbonamento);
    }

    @Test
    void read_negative() {

        Integer id = 99;

        when(abbonamentoRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> abbonamentoService.read(id)
        );

        verify(abbonamentoRepository)
                .findById(id);
    }

    @Test
    void getAll_positive() {

        Abbonamento abbonamento = abbonamento1;
        AbbonamentoDto dto = abbonamentoDto;

        List<Abbonamento> abbonamenti = List.of(abbonamento);
        List<AbbonamentoDto> dtoList = List.of(dto);

        when(abbonamentoRepository.findAll())
                .thenReturn(abbonamenti);

        when(abbonamentoMapper.toDTOList(abbonamenti))
                .thenReturn(dtoList);

        Iterable<AbbonamentoDto> result =
                abbonamentoService.getAll();

        assertNotNull(result);

        List<AbbonamentoDto> resultList = (List<AbbonamentoDto>) result;

        assertEquals(dtoList, resultList);
        assertEquals(1, resultList.size());

        verify(abbonamentoRepository)
                .findAll();

        verify(abbonamentoMapper)
                .toDTOList(abbonamenti);
    }

    @Test
    void getAll_negative(){

        when(abbonamentoRepository.findAll())
                .thenThrow(
                        new RuntimeException("Errore recupero abbonamenti")
                );


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.getAll()
        );


        verify(abbonamentoRepository)
                .findAll();

    }

    @Test
    void findByTipo_positive() {

        List<Abbonamento> abbonamenti =
                List.of(abbonamento1);

        List<AbbonamentoDto> dtoList =
                List.of(abbonamentoDto);


        when(abbonamentoRepository.findByTipo(
                AbbonamentoTypeEnum.MENSILE
        ))
                .thenReturn(abbonamenti);


        when(abbonamentoMapper.toDTOList(abbonamenti))
                .thenReturn(dtoList);


        List<AbbonamentoDto> result =
                abbonamentoService.findByTipo(
                        AbbonamentoTypeEnum.MENSILE
                );


        assertNotNull(result);
        assertEquals(dtoList, result);


        verify(abbonamentoRepository)
                .findByTipo(AbbonamentoTypeEnum.MENSILE);

        verify(abbonamentoMapper)
                .toDTOList(abbonamenti);

    }


    @Test
    void findByTipo_negative() {


        when(abbonamentoRepository.findByTipo(
                AbbonamentoTypeEnum.MENSILE
        ))
                .thenThrow(new RuntimeException("Errore ricerca tipo"));


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.findByTipo(
                        AbbonamentoTypeEnum.MENSILE
                )
        );


        verify(abbonamentoRepository)
                .findByTipo(AbbonamentoTypeEnum.MENSILE);

    }


    @Test
    void findByStato_positive() {


        List<Abbonamento> abbonamenti =
                List.of(abbonamento1);


        List<AbbonamentoDto> dtoList =
                List.of(abbonamentoDto);


        when(abbonamentoRepository.findByStato(
                StatoAbbonamentoEnum.ATTIVO
        ))
                .thenReturn(abbonamenti);


        when(abbonamentoMapper.toDTOList(abbonamenti))
                .thenReturn(dtoList);


        List<AbbonamentoDto> result =
                abbonamentoService.findByStato(
                        StatoAbbonamentoEnum.ATTIVO
                );


        assertNotNull(result);
        assertEquals(dtoList, result);


        verify(abbonamentoRepository)
                .findByStato(StatoAbbonamentoEnum.ATTIVO);

        verify(abbonamentoMapper)
                .toDTOList(abbonamenti);

    }


    @Test
    void findByStato_negative() {


        when(abbonamentoRepository.findByStato(
                StatoAbbonamentoEnum.ATTIVO
        ))
                .thenThrow(new RuntimeException());


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.findByStato(
                        StatoAbbonamentoEnum.ATTIVO
                )
        );

        verify(abbonamentoRepository)
                .findByStato(StatoAbbonamentoEnum.ATTIVO);

    }

    @Test
    void findByUtenteId_positive() {


        when(abbonamentoRepository.findByUtenteId(1))
                .thenReturn(abbonamento1);


        when(abbonamentoMapper.toDTO(abbonamento1))
                .thenReturn(abbonamentoDto);


        AbbonamentoDto result =
                abbonamentoService.findByUtenteId(1);


        assertNotNull(result);
        assertEquals(abbonamentoDto, result);


        verify(abbonamentoRepository)
                .findByUtenteId(1);


        verify(abbonamentoMapper)
                .toDTO(abbonamento1);

    }


    @Test
    void findByUtenteId_notFound(){

        when(abbonamentoRepository.findByUtenteId(99))
                .thenReturn(null);


        AbbonamentoDto result =
                abbonamentoService.findByUtenteId(99);


        assertNull(result);


        verify(abbonamentoRepository)
                .findByUtenteId(99);

    }

    @Test
    void findInScadenza_positive() {


        LocalDate inizio =
                LocalDate.of(2026, 7, 1);

        LocalDate fine =
                LocalDate.of(2026, 8, 31);


        List<Abbonamento> lista =
                List.of(abbonamento1);


        List<AbbonamentoDto> dtoList =
                List.of(abbonamentoDto);


        when(abbonamentoRepository.findByDataFineBetween(
                inizio,
                fine
        ))
                .thenReturn(lista);


        when(abbonamentoMapper.toDTOList(lista))
                .thenReturn(dtoList);


        List<AbbonamentoDto> result =
                abbonamentoService.findInScadenza(
                        inizio,
                        fine
                );


        assertEquals(dtoList, result);

        verify(abbonamentoRepository)
                .findByDataFineBetween(inizio, fine);

        verify(abbonamentoMapper)
                .toDTOList(lista);

    }

    @Test
    void findInScadenza_negative(){

        LocalDate inizio =
                LocalDate.of(2026,7,1);

        LocalDate fine =
                LocalDate.of(2026,8,31);


        when(abbonamentoRepository.findByDataFineBetween(
                inizio,
                fine
        ))
                .thenThrow(
                        new RuntimeException("Errore ricerca scadenze")
                );


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.findInScadenza(
                        inizio,
                        fine
                )
        );


        verify(abbonamentoRepository)
                .findByDataFineBetween(inizio,fine);

    }

    @Test
    void findValidiOggi_positive() {


        LocalDate oggi = LocalDate.now();


        List<Abbonamento> lista =
                List.of(abbonamento1);


        when(
                abbonamentoRepository
                        .findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
                                oggi,
                                oggi
                        )
        )
                .thenReturn(lista);


        when(abbonamentoMapper.toDTOList(lista))
                .thenReturn(List.of(abbonamentoDto));


        List<AbbonamentoDto> result =
                abbonamentoService.findValidiOggi();


        assertEquals(
                List.of(abbonamentoDto),
                result
        );

        verify(abbonamentoRepository)
                .findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
                        oggi,
                        oggi
                );

        verify(abbonamentoMapper)
                .toDTOList(lista);
    }

    @Test
    void findValidiOggi_negative(){

        LocalDate oggi = LocalDate.now();


        when(
                abbonamentoRepository
                        .findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
                                oggi,
                                oggi
                        )
        )
                .thenThrow(
                        new RuntimeException("Errore ricerca validi")
                );


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.findValidiOggi()
        );


        verify(abbonamentoRepository)
                .findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
                        oggi,
                        oggi
                );

    }

    @Test
    void creaAbbonamento_positive() {


        when(abbonamentoRepository.findByUtenteId(1))
                .thenReturn(null);


        when(abbonamentoMapper.toEntity(abbonamentoDto))
                .thenReturn(abbonamento1);


        when(abbonamentoRepository.save(abbonamento1))
                .thenReturn(abbonamento1);


        when(abbonamentoMapper.toDTO(abbonamento1))
                .thenReturn(abbonamentoDto);


        AbbonamentoDto result =
                abbonamentoService.creaAbbonamento(abbonamentoDto);


        assertNotNull(result);
        assertEquals(abbonamentoDto,result);

        assertEquals(
                StatoAbbonamentoEnum.ATTIVO,
                abbonamento1.getStato()
        );


        verify(abbonamentoRepository)
                .findByUtenteId(1);


        verify(abbonamentoRepository)
                .save(abbonamento1);

        verify(abbonamentoMapper)
                .toEntity(abbonamentoDto);

        verify(abbonamentoMapper)
                .toDTO(abbonamento1);

    }


    @Test
    void creaAbbonamento_negativeUtenteGiaPresente() {


        when(abbonamentoRepository.findByUtenteId(1))
                .thenReturn(abbonamento1);


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.creaAbbonamento(
                        abbonamentoDto
                )
        );


        verify(abbonamentoRepository)
                .findByUtenteId(1);

    }

    @Test
    void rinnovaAbbonamento_positive() {


        when(abbonamentoRepository.findById(1))
                .thenReturn(Optional.of(abbonamento1));


        when(abbonamentoRepository.save(abbonamento1))
                .thenReturn(abbonamento1);


        when(abbonamentoMapper.toDTO(abbonamento1))
                .thenReturn(abbonamentoDto);


        AbbonamentoDto result =
                abbonamentoService.rinnovaAbbonamento(1);


        assertNotNull(result);


        verify(abbonamentoRepository)
                .findById(1);


        verify(abbonamentoRepository)
                .save(abbonamento1);

        verify(abbonamentoMapper)
                .toDTO(abbonamento1);

    }


    @Test
    void rinnovaAbbonamento_negative() {


        when(abbonamentoRepository.findById(99))
                .thenReturn(Optional.empty());


        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.rinnovaAbbonamento(99)
        );


        verify(abbonamentoRepository)
                .findById(99);

    }

    @Test
    void aggiornaStatiScaduti_positive() {


        List<Abbonamento> lista =
                new ArrayList<>();


        lista.add(abbonamento1);


        when(abbonamentoRepository.findByDataFineBefore(
                any(LocalDate.class)
        ))
                .thenReturn(lista);


        abbonamentoService.aggiornaStatiScaduti();

        assertEquals(
                StatoAbbonamentoEnum.SCADUTO,
                abbonamento1.getStato()
        );


        verify(abbonamentoRepository)
                .saveAll(lista);

        verify(abbonamentoRepository)
                .findByDataFineBefore(any(LocalDate.class));

    }

    @Test
    void aggiornaStatiScaduti_negativeErroreRepository() {


        when(abbonamentoRepository.findByDataFineBefore(
                any(LocalDate.class)
        ))
                .thenThrow(new RuntimeException("Errore ricerca scaduti"));



        assertThrows(
                RuntimeException.class,
                () -> abbonamentoService.aggiornaStatiScaduti()
        );



        verify(abbonamentoRepository)
                .findByDataFineBefore(any(LocalDate.class));

    }

    @Test
    void creaSenzaTipo_negative(){

        Abbonamento abbonamento =
                new Abbonamento();


        assertThrows(
                IllegalStateException.class,
                () -> abbonamento.crea()
        );

    }

    @Test
    void creaAnnuale_positive(){

        Abbonamento abbonamento =
                new Abbonamento();


        abbonamento.setTipo(
                AbbonamentoTypeEnum.ANNUALE
        );


        abbonamento.crea();


        assertEquals(
                StatoAbbonamentoEnum.ATTIVO,
                abbonamento.getStato()
        );


        assertEquals(
                LocalDate.now()
                        .plusDays(1)
                        .plusYears(1),
                abbonamento.getDataFine()
        );

    }

    @Test
    void creaMensile_positive(){

        Abbonamento abbonamento =
                new Abbonamento();


        abbonamento.setTipo(
                AbbonamentoTypeEnum.MENSILE
        );


        abbonamento.crea();



        assertEquals(
                StatoAbbonamentoEnum.ATTIVO,
                abbonamento.getStato()
        );


        assertEquals(
                LocalDate.now()
                        .plusDays(1)
                        .plusMonths(1),
                abbonamento.getDataFine()
        );

    }

    @Test
    void rinnovaMensile_positive(){

        Abbonamento abbonamento =
                new Abbonamento();


        abbonamento.setTipo(
                AbbonamentoTypeEnum.MENSILE
        );


        abbonamento.setDataFine(
                LocalDate.of(2026,8,18)
        );


        abbonamento.rinnova();


        assertEquals(
                LocalDate.of(2026,9,18),
                abbonamento.getDataFine()
        );


        assertEquals(
                StatoAbbonamentoEnum.ATTIVO,
                abbonamento.getStato()
        );

    }

    @Test
    void rinnovaSenzaDataFine_negative(){

        Abbonamento abbonamento =
                new Abbonamento();


        abbonamento.setTipo(
                AbbonamentoTypeEnum.MENSILE
        );


        assertThrows(
                NullPointerException.class,
                () -> abbonamento.rinnova()
        );

    }

}