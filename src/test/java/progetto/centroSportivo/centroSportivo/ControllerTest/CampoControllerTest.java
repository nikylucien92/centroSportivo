package progetto.centroSportivo.centroSportivo.ControllerTest;

import it.controller.CampoController;
import it.dto.CampoDto;
import it.CentroSportivoApplication;
import it.service.CampoService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(CampoController.class)
@ContextConfiguration(classes = CentroSportivoApplication.class)
public class CampoControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private CampoService campoService;


    private CampoDto campoDto;
    private CampoDto campoDto1;



    @BeforeEach
    void setUp() {


        campoDto = new CampoDto(
                1,
                "Campo Calcio 1",
                "Calcio",
                50.0,
                true,
                null,
                null
        );


        campoDto1 = new CampoDto(
                2,
                "Campo Basket 1",
                "Basket",
                40.0,
                false,
                null,
                null
        );

    }



    @Test
    void read_positive() throws Exception {


        when(campoService.read(1))
                .thenReturn(campoDto);



        mockMvc.perform(
                        get("/campo/read?id=1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome")
                        .value("Campo Calcio 1"));



        verify(campoService)
                .read(1);

    }



    @Test
    void read_negative() throws Exception {


        when(campoService.read(99))
                .thenThrow(
                        new RuntimeException(
                                "Campo non trovato"
                        )
                );



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/read?id=99")
                )
        );



        verify(campoService)
                .read(99);

    }





    @Test
    void insert_positive() throws Exception {


        when(campoService.insert(any(CampoDto.class)))
                .thenReturn(campoDto);



        mockMvc.perform(
                        post("/campo/insert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "id":1,
                                  "nome":"Campo Calcio 1",
                                  "tipologia":"Calcio",
                                  "prezzo":50.0,
                                  "coperto":true
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(campoService)
                .insert(any(CampoDto.class));

    }





    @Test
    void insert_negative() throws Exception {


        when(campoService.insert(any(CampoDto.class)))
                .thenThrow(
                        new RuntimeException()
                );



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        post("/campo/insert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "nome":"Campo errato"
                                }
                                """)
                )
        );



        verify(campoService)
                .insert(any(CampoDto.class));

    }





    @Test
    void update_positive() throws Exception {


        when(campoService.update(any(CampoDto.class)))
                .thenReturn(campoDto);



        mockMvc.perform(
                        put("/campo/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "id":1,
                                  "nome":"Campo Calcio 1",
                                  "tipologia":"Calcio",
                                  "prezzo":50.0,
                                  "coperto":true
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(campoService)
                .update(any(CampoDto.class));

    }





    @Test
    void update_negative() throws Exception {


        when(campoService.update(any(CampoDto.class)))
                .thenThrow(
                        new RuntimeException()
                );



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        put("/campo/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "id":99,
                                  "nome":"Errore"
                                }
                                """)
                )
        );



        verify(campoService)
                .update(any(CampoDto.class));

    }





    @Test
    void delete_positive() throws Exception {


        doNothing()
                .when(campoService)
                .delete(1);



        mockMvc.perform(
                        delete("/campo/delete?id=1")
                )
                .andExpect(status().isOk());



        verify(campoService)
                .delete(1);

    }





    @Test
    void delete_negative() throws Exception {


        doThrow(
                new RuntimeException()
        )
                .when(campoService)
                .delete(99);



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        delete("/campo/delete?id=99")
                )
        );



        verify(campoService)
                .delete(99);

    }





    @Test
    void getAll_positive() throws Exception {


        when(campoService.getAll())
                .thenReturn(List.of(
                        campoDto,
                        campoDto1
                ));


        mockMvc.perform(
                        get("/campo/getall")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome")
                        .value("Campo Calcio 1"))
                .andExpect(jsonPath("$[1].nome")
                        .value("Campo Basket 1"));


        verify(campoService)
                .getAll();

    }





    @Test
    void getAll_negative() throws Exception {


        when(campoService.getAll())
                .thenThrow(
                        new RuntimeException("Errore caricamento campi")
                );


        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/getall")
                )
        );


        verify(campoService)
                .getAll();

    }





    @Test
    void findByNome_positive() throws Exception {


        when(campoService.findByNome("Campo Calcio 1"))
                .thenReturn(campoDto);



        mockMvc.perform(
                        get("/campo/nome/Campo Calcio 1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(campoService)
                .findByNome("Campo Calcio 1");

    }





    @Test
    void findByNome_negative() throws Exception {


        when(campoService.findByNome("Campo inesistente"))
                .thenThrow(
                        new RuntimeException()
                );



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/nome/Campo inesistente")
                )
        );



        verify(campoService)
                .findByNome("Campo inesistente");

    }





    @Test
    void findByTipologia_positive() throws Exception {


        when(campoService.findByTipologia("Calcio"))
                .thenReturn(List.of(campoDto));


        mockMvc.perform(
                        get("/campo/tipologia/Calcio")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome")
                        .value("Campo Calcio 1"));


        verify(campoService)
                .findByTipologia("Calcio");

    }





    @Test
    void findByTipologia_negative() throws Exception {


        when(campoService.findByTipologia("Piscina"))
                .thenThrow(
                        new RuntimeException("Tipologia inesistente")
                );


        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/tipologia/Piscina")
                )
        );


        verify(campoService)
                .findByTipologia("Piscina");

    }





    @Test
    void findByCoperto_positive() throws Exception {


        when(campoService.findByCoperto(true))
                .thenReturn(
                        List.of(campoDto)
                );



        mockMvc.perform(
                        get("/campo/coperto/true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));



        verify(campoService)
                .findByCoperto(true);

    }





    @Test
    void findByCoperto_negative() throws Exception {


        when(campoService.findByCoperto(false))
                .thenThrow(
                        new RuntimeException("Errore ricerca copertura")
                );


        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/coperto/false")
                )
        );


        verify(campoService)
                .findByCoperto(false);

    }





    @Test
    void findByTipologiaAndCoperto_positive() throws Exception {


        when(
                campoService.findByTipologiaAndCoperto(
                        "Calcio",
                        true
                )
        )
                .thenReturn(
                        List.of(campoDto)
                );



        mockMvc.perform(
                        get("/campo/tipologia-coperto/Calcio/true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));



        verify(campoService)
                .findByTipologiaAndCoperto(
                        "Calcio",
                        true
                );

    }





    @Test
    void findByTipologiaAndCoperto_negative() throws Exception {


        when(
                campoService.findByTipologiaAndCoperto(
                        "Basket",
                        false
                )
        )
                .thenThrow(
                        new RuntimeException(
                                "Errore ricerca filtro"
                        )
                );


        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/tipologia-coperto/Basket/false")
                )
        );


        verify(campoService)
                .findByTipologiaAndCoperto(
                        "Basket",
                        false
                );

    }





    @Test
    void existsByNome_positive() throws Exception {


        when(campoService.existsByNome("Campo Calcio 1"))
                .thenReturn(true);


        mockMvc.perform(
                        get("/campo/esiste/Campo Calcio 1")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));


        verify(campoService)
                .existsByNome("Campo Calcio 1");

    }




    @Test
    void existsByNome_negative() throws Exception {


        when(campoService.existsByNome("Errore"))
                .thenThrow(
                        new RuntimeException(
                                "Errore controllo esistenza"
                        )
                );


        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/campo/esiste/Errore")
                )
        );


        verify(campoService)
                .existsByNome("Errore");

    }

}
