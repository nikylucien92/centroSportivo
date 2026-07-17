package progetto.centroSportivo.centroSportivo.ControllerTest;


import it.controller.AbbonamentoController;
import it.dto.AbbonamentoDto;
import it.dto.UtenteDto;
import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;
import it.CentroSportivoApplication;
import it.service.AbbonamentoService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AbbonamentoController.class)
@ContextConfiguration(classes = CentroSportivoApplication.class)
public class AbbonamentoControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private AbbonamentoService abbonamentoService;


    private AbbonamentoDto abbonamentoDto;

    private AbbonamentoDto abbonamentoDto1;


    @BeforeEach
    void setUp() {


        UtenteDto utenteDto = new UtenteDto();
        utenteDto.setId(1);


        abbonamentoDto = new AbbonamentoDto(
                1,
                AbbonamentoTypeEnum.MENSILE,
                LocalDate.of(2026,7,18),
                LocalDate.of(2026,8,18),
                BigDecimal.valueOf(50),
                StatoAbbonamentoEnum.ATTIVO,
                utenteDto
        );


        abbonamentoDto1 = new AbbonamentoDto(
                2,
                AbbonamentoTypeEnum.ANNUALE,
                LocalDate.of(2026,7,18),
                LocalDate.of(2027,7,18),
                BigDecimal.valueOf(500),
                StatoAbbonamentoEnum.ATTIVO,
                utenteDto
        );

    }

    @Test
    void read_positive() throws Exception {


        when(abbonamentoService.read(1))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        get("/abbonamento/read?id=1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(abbonamentoService)
                .read(1);

    }




    @Test
    void read_negative() throws Exception {


        when(abbonamentoService.read(99))
                .thenThrow(new RuntimeException("Abbonamento non trovato"));



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        get("/abbonamento/read?id=99")
                )
        );



        verify(abbonamentoService)
                .read(99);

    }




    @Test
    void insert_positive() throws Exception {


        when(abbonamentoService.insert(any(AbbonamentoDto.class)))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        post("/abbonamento/insert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "id":1,
                              "tipo":"MENSILE",
                              "dataInizio":"2026-07-18",
                              "dataFine":"2026-08-18",
                              "prezzo":50,
                              "stato":"ATTIVO",
                              "utente":{
                                "id":1
                              }
                            }
                            """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(abbonamentoService)
                .insert(any(AbbonamentoDto.class));

    }




    @Test
    void insert_negative() throws Exception {


        when(abbonamentoService.insert(any(AbbonamentoDto.class)))
                .thenThrow(new RuntimeException("Errore inserimento"));



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        post("/abbonamento/insert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "tipo":"MENSILE"
                            }
                            """)
                )
        );



        verify(abbonamentoService)
                .insert(any(AbbonamentoDto.class));

    }




    @Test
    void update_positive() throws Exception {


        when(abbonamentoService.update(any(AbbonamentoDto.class)))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        put("/abbonamento/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "id":1,
                              "tipo":"MENSILE",
                              "dataInizio":"2026-07-18",
                              "dataFine":"2026-08-18",
                              "prezzo":50,
                              "stato":"ATTIVO",
                              "utente":{
                                "id":1
                              }
                            }
                            """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(abbonamentoService)
                .update(any(AbbonamentoDto.class));

    }




    @Test
    void update_negative() throws Exception {


        when(abbonamentoService.update(any(AbbonamentoDto.class)))
                .thenThrow(new RuntimeException("Errore modifica"));



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        put("/abbonamento/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "id":99,
                              "tipo":"ANNUALE"
                            }
                            """)
                )
        );



        verify(abbonamentoService)
                .update(any(AbbonamentoDto.class));

    }




    @Test
    void delete_positive() throws Exception {


        doNothing()
                .when(abbonamentoService)
                .delete(1);



        mockMvc.perform(
                        delete("/abbonamento/delete?id=1")
                )
                .andExpect(status().isOk());



        verify(abbonamentoService)
                .delete(1);

    }




    @Test
    void delete_negative() throws Exception {


        doThrow(new RuntimeException("Abbonamento non trovato"))
                .when(abbonamentoService)
                .delete(99);



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        delete("/abbonamento/delete?id=99")
                )
        );



        verify(abbonamentoService)
                .delete(99);

    }


    @Test
    void findByTipo_positive() throws Exception {


        when(abbonamentoService.findByTipo(
                AbbonamentoTypeEnum.MENSILE
        ))
                .thenReturn(List.of(abbonamentoDto));


        mockMvc.perform(
                        get("/abbonamento/tipo/MENSILE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].tipo")
                        .value("MENSILE"));


        verify(abbonamentoService)
                .findByTipo(AbbonamentoTypeEnum.MENSILE);

    }



    @Test
    void findByTipo_negative() throws Exception {


        when(abbonamentoService.findByTipo(
                AbbonamentoTypeEnum.MENSILE
        ))
                .thenReturn(new ArrayList<>());


        mockMvc.perform(
                        get("/abbonamento/tipo/MENSILE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));


        verify(abbonamentoService)
                .findByTipo(AbbonamentoTypeEnum.MENSILE);

    }



    @Test
    void findByStato_positive() throws Exception {


        when(abbonamentoService.findByStato(
                StatoAbbonamentoEnum.ATTIVO
        ))
                .thenReturn(List.of(abbonamentoDto));


        mockMvc.perform(
                        get("/abbonamento/stato/ATTIVO")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));


        verify(abbonamentoService)
                .findByStato(StatoAbbonamentoEnum.ATTIVO);

    }

    @Test
    void findByStato_negative() throws Exception {


        when(abbonamentoService.findByStato(
                StatoAbbonamentoEnum.ATTIVO
        ))
                .thenReturn(new ArrayList<>());



        mockMvc.perform(
                        get("/abbonamento/stato/ATTIVO")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));



        verify(abbonamentoService)
                .findByStato(StatoAbbonamentoEnum.ATTIVO);

    }



    @Test
    void findByUtenteId_positive() throws Exception {


        when(abbonamentoService.findByUtenteId(1))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        get("/abbonamento/utente/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));


        verify(abbonamentoService)
                .findByUtenteId(1);

    }

    @Test
    void findByUtenteId_negative() throws Exception {


        when(abbonamentoService.findByUtenteId(99))
                .thenReturn(null);



        mockMvc.perform(
                        get("/abbonamento/utente/99")
                )
                .andExpect(status().isOk());



        verify(abbonamentoService)
                .findByUtenteId(99);

    }



    @Test
    void findInScadenza_positive() throws Exception {


        when(abbonamentoService.findInScadenza(
                LocalDate.of(2026,7,1),
                LocalDate.of(2026,8,31)
        ))
                .thenReturn(List.of(abbonamentoDto));



        mockMvc.perform(
                        get("/abbonamento/scadenza")
                                .param("inizio","2026-07-01")
                                .param("fine","2026-08-31")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));


        verify(abbonamentoService)
                .findInScadenza(
                        LocalDate.of(2026,7,1),
                        LocalDate.of(2026,8,31)
                );

    }

    @Test
    void findInScadenza_negative() throws Exception {


        when(abbonamentoService.findInScadenza(
                LocalDate.of(2026,7,1),
                LocalDate.of(2026,8,31)
        ))
                .thenReturn(new ArrayList<>());



        mockMvc.perform(
                        get("/abbonamento/scadenza")
                                .param("inizio","2026-07-01")
                                .param("fine","2026-08-31")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));



        verify(abbonamentoService)
                .findInScadenza(
                        LocalDate.of(2026,7,1),
                        LocalDate.of(2026,8,31)
                );

    }


    @Test
    void findScaduti_positive() throws Exception {


        when(abbonamentoService.findScaduti())
                .thenReturn(List.of(abbonamentoDto));



        mockMvc.perform(
                        get("/abbonamento/scaduti")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));



        verify(abbonamentoService)
                .findScaduti();

    }

    @Test
    void findScaduti_negative() throws Exception {


        when(abbonamentoService.findScaduti())
                .thenReturn(new ArrayList<>());



        mockMvc.perform(
                        get("/abbonamento/scaduti")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));



        verify(abbonamentoService)
                .findScaduti();

    }



    @Test
    void findValidiOggi_positive() throws Exception {


        when(abbonamentoService.findValidiOggi())
                .thenReturn(List.of(abbonamentoDto));


        mockMvc.perform(
                        get("/abbonamento/attivi")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));


        verify(abbonamentoService)
                .findValidiOggi();

    }

    @Test
    void findValidiOggi_negative() throws Exception {


        when(abbonamentoService.findValidiOggi())
                .thenReturn(new ArrayList<>());



        mockMvc.perform(
                        get("/abbonamento/attivi")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));



        verify(abbonamentoService)
                .findValidiOggi();

    }


    @Test
    void creaAbbonamento_positive() throws Exception {


        when(abbonamentoService.creaAbbonamento(
                any(AbbonamentoDto.class)
        ))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        post("/abbonamento")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                         "id":1,
                         "tipo":"MENSILE",
                         "dataInizio":"2026-07-18",
                         "dataFine":"2026-08-18",
                         "prezzo":50,
                         "stato":"ATTIVO",
                         "utente":{
                            "id":1
                         }
                        }
                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(abbonamentoService)
                .creaAbbonamento(any(AbbonamentoDto.class));

    }


    @Test
    void creaAbbonamento_negative() throws Exception {


        when(abbonamentoService.creaAbbonamento(
                any(AbbonamentoDto.class)
        ))
                .thenThrow(new RuntimeException());



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        post("/abbonamento")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "tipo":"MENSILE"
                                }
                                """)
                )
        );



        verify(abbonamentoService)
                .creaAbbonamento(any(AbbonamentoDto.class));

    }


    @Test
    void rinnovaAbbonamento_positive() throws Exception {


        when(abbonamentoService.rinnovaAbbonamento(1))
                .thenReturn(abbonamentoDto);



        mockMvc.perform(
                        put("/abbonamento/1/rinnova")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));



        verify(abbonamentoService)
                .rinnovaAbbonamento(1);

    }




    @Test
    void rinnovaAbbonamento_negative() throws Exception {


        when(abbonamentoService.rinnovaAbbonamento(99))
                .thenThrow(new RuntimeException());



        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        put("/abbonamento/99/rinnova")
                )
        );



        verify(abbonamentoService)
                .rinnovaAbbonamento(99);

    }







}
