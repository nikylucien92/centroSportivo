package progetto.centroSportivo.centroSportivo.ServiceTest;

import it.dto.CampoDto;
import it.mapper.CampoMapper;
import it.model.Campo;
import it.repository.CampoRepository;
import it.service.CampoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CampoServiceTest {


    @Mock
    private CampoRepository campoRepository;


    @Mock
    private CampoMapper campoMapper;


    @InjectMocks
    private CampoService campoService;


    private Campo campo1;

    private CampoDto campoDto;



    @BeforeEach
    void setUp() {


        campo1 = new Campo(
                1,
                "Campo Calcio 1",
                "Calcio",
                50.0,
                true,
                new ArrayList<>(),
                new ArrayList<>()
        );


        campoDto = new CampoDto(
                1,
                "Campo Calcio 1",
                "Calcio",
                50.0,
                true,
                new ArrayList<>(),
                new ArrayList<>()
        );

    }

    @Test
    void insert_positive() {

        CampoDto dto = campoDto;
        Campo campo = campo1;

        when(campoMapper.toEntity(dto))
                .thenReturn(campo);

        when(campoRepository.save(campo))
                .thenReturn(campo);

        when(campoMapper.toDTO(campo))
                .thenReturn(dto);

        CampoDto result = campoService.insert(dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(campoMapper).toEntity(dto);
        verify(campoRepository).save(campo);
        verify(campoMapper).toDTO(campo);
    }

    @Test
    void insert_negative() {

        CampoDto dto = campoDto;
        Campo campo = campo1;

        when(campoMapper.toEntity(dto))
                .thenReturn(campo);

        when(campoRepository.save(campo))
                .thenThrow(new RuntimeException("Errore salvataggio campo"));

        assertThrows(
                RuntimeException.class,
                () -> campoService.insert(dto)
        );

        verify(campoMapper)
                .toEntity(dto);

        verify(campoRepository)
                .save(campo);
    }


    @Test
    void update_positive() {

        CampoDto dto = campoDto;
        Campo campo = campo1;

        when(campoMapper.toEntity(dto))
                .thenReturn(campo);

        when(campoRepository.save(campo))
                .thenReturn(campo);

        when(campoMapper.toDTO(campo))
                .thenReturn(dto);

        CampoDto result = campoService.update(dto);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(campoMapper).toEntity(dto);
        verify(campoRepository).save(campo);
        verify(campoMapper).toDTO(campo);
    }

    @Test
    void update_negative() {

        CampoDto dto = campoDto;
        Campo campo = campo1;

        when(campoMapper.toEntity(dto))
                .thenReturn(campo);

        when(campoRepository.save(campo))
                .thenThrow(new RuntimeException("Errore aggiornamento"));

        assertThrows(
                RuntimeException.class,
                () -> campoService.update(dto)
        );

        verify(campoMapper)
                .toEntity(dto);

        verify(campoRepository)
                .save(campo);
    }

    @Test
    void delete_positive() {

        Integer id = 1;

        doNothing()
                .when(campoRepository)
                .deleteById(id);

        campoService.delete(id);

        verify(campoRepository)
                .deleteById(id);
    }

    @Test
    void delete_negative() {

        Integer id = 99;

        doThrow(new RuntimeException("Errore cancellazione"))
                .when(campoRepository)
                .deleteById(id);


        assertThrows(
                RuntimeException.class,
                () -> campoService.delete(id)
        );


        verify(campoRepository)
                .deleteById(id);
    }


    @Test
    void read_positive() {

        CampoDto dto = campoDto;
        Campo campo = campo1;

        when(campoRepository.findById(1))
                .thenReturn(Optional.of(campo));

        when(campoMapper.toDTO(campo))
                .thenReturn(dto);

        CampoDto result = campoService.read(1);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(campoRepository)
                .findById(1);

        verify(campoMapper)
                .toDTO(campo);
    }

    @Test
    void read_negative() {

        Integer id = 99;

        when(campoRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> campoService.read(id)
        );

        verify(campoRepository)
                .findById(id);
    }


    @Test
    void findByNome_positive(){


        when(campoRepository.findByNome("Campo Calcio 1"))
                .thenReturn(campo1);


        when(campoMapper.toDTO(campo1))
                .thenReturn(campoDto);



        CampoDto result =
                campoService.findByNome("Campo Calcio 1");



        assertNotNull(result);

        assertEquals(
                campoDto,
                result
        );


        verify(campoRepository)
                .findByNome("Campo Calcio 1");


        verify(campoMapper)
                .toDTO(campo1);

    }



    @Test
    void findByNome_negative(){

        when(campoRepository.findByNome("Campo inesistente"))
                .thenThrow(
                        new RuntimeException("Errore ricerca campo")
                );


        assertThrows(
                RuntimeException.class,
                () -> campoService.findByNome("Campo inesistente")
        );


        verify(campoRepository)
                .findByNome("Campo inesistente");

    }

    @Test
    void findByTipologia_positive(){


        List<Campo> lista =
                List.of(campo1);


        List<CampoDto> dtoList =
                List.of(campoDto);



        when(campoRepository.findByTipologia("Calcio"))
                .thenReturn(lista);



        when(campoMapper.toDTOList(lista))
                .thenReturn(dtoList);



        List<CampoDto> result =
                campoService.findByTipologia("Calcio");



        assertNotNull(result);

        assertEquals(
                dtoList,
                result
        );



        verify(campoRepository)
                .findByTipologia("Calcio");


        verify(campoMapper)
                .toDTOList(lista);

    }





    @Test
    void findByTipologia_negative(){


        when(campoRepository.findByTipologia("Basket"))
                .thenThrow(new RuntimeException(
                        "Errore ricerca tipologia"
                ));



        assertThrows(
                RuntimeException.class,
                () -> campoService.findByTipologia("Basket")
        );



        verify(campoRepository)
                .findByTipologia("Basket");

    }





    @Test
    void findByCoperto_positive(){


        List<Campo> lista =
                List.of(campo1);


        List<CampoDto> dtoList =
                List.of(campoDto);



        when(campoRepository.findByCoperto(true))
                .thenReturn(lista);



        when(campoMapper.toDTOList(lista))
                .thenReturn(dtoList);



        List<CampoDto> result =
                campoService.findByCoperto(true);



        assertEquals(
                dtoList,
                result
        );



        verify(campoRepository)
                .findByCoperto(true);


        verify(campoMapper)
                .toDTOList(lista);

    }





    @Test
    void findByCoperto_negative(){

        when(campoRepository.findByCoperto(false))
                .thenThrow(
                        new RuntimeException("Errore ricerca")
                );


        assertThrows(
                RuntimeException.class,
                () -> campoService.findByCoperto(false)
        );


        verify(campoRepository)
                .findByCoperto(false);

    }



    @Test
    void findByTipologiaAndCoperto_positive(){


        List<Campo> lista =
                List.of(campo1);


        List<CampoDto> dtoList =
                List.of(campoDto);



        when(
                campoRepository.findByTipologiaAndCoperto(
                        "Calcio",
                        true
                )
        )
                .thenReturn(lista);



        when(campoMapper.toDTOList(lista))
                .thenReturn(dtoList);




        List<CampoDto> result =
                campoService.findByTipologiaAndCoperto(
                        "Calcio",
                        true
                );



        assertEquals(
                dtoList,
                result
        );



        verify(campoRepository)
                .findByTipologiaAndCoperto(
                        "Calcio",
                        true
                );


        verify(campoMapper)
                .toDTOList(lista);

    }







    @Test
    void findByTipologiaAndCoperto_negative(){


        when(
                campoRepository.findByTipologiaAndCoperto(
                        "Basket",
                        false
                )
        )
                .thenThrow(
                        new RuntimeException(
                                "Errore filtro campo"
                        )
                );



        assertThrows(
                RuntimeException.class,
                () -> campoService.findByTipologiaAndCoperto(
                        "Basket",
                        false
                )
        );



        verify(campoRepository)
                .findByTipologiaAndCoperto(
                        "Basket",
                        false
                );

    }







    @Test
    void existsByNome_positive(){


        when(campoRepository.existsByNome(
                "Campo Calcio 1"
        ))
                .thenReturn(true);



        boolean result =
                campoService.existsByNome(
                        "Campo Calcio 1"
                );



        assertTrue(result);



        verify(campoRepository)
                .existsByNome(
                        "Campo Calcio 1"
                );

    }







    @Test
    void existsByNome_negative(){

        when(campoRepository.existsByNome("Campo Errore"))
                .thenThrow(
                        new RuntimeException("Errore controllo esistenza")
                );


        assertThrows(
                RuntimeException.class,
                () -> campoService.existsByNome("Campo Errore")
        );


        verify(campoRepository)
                .existsByNome("Campo Errore");

    }

    @Test
    void getAll_positive() {

        Campo campo = campo1;
        CampoDto dto = campoDto;

        List<Campo> campi = List.of(campo);
        List<CampoDto> dtoList = List.of(dto);

        when(campoRepository.findAll())
                .thenReturn(campi);

        when(campoMapper.toDTOList(campi))
                .thenReturn(dtoList);

        Iterable<CampoDto> result =
                campoService.getAll();

        assertNotNull(result);

        List<CampoDto> resultList = (List<CampoDto>) result;

        assertEquals(dtoList, resultList);
        assertEquals(1, resultList.size());

        verify(campoRepository)
                .findAll();

        verify(campoMapper)
                .toDTOList(campi);
    }

    @Test
    void getAll_negative(){

        when(campoRepository.findAll())
                .thenThrow(
                        new RuntimeException("Errore recupero campi")
                );


        assertThrows(
                RuntimeException.class,
                () -> campoService.getAll()
        );


        verify(campoRepository)
                .findAll();

    }





}