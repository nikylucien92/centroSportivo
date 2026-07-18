package progetto.centroSportivo.centroSportivo.ControllerTest;

import it.controller.PrenotazioneController;
import it.dto.PrenotazioneDto;
import it.service.PrenotazioneService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PrenotazioneController.class)
@ContextConfiguration(classes = PrenotazioneController.class)

    public class PrenotazioneControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockitoBean
        private PrenotazioneService prenotazioneService;
        @Autowired
        private ObjectMapper objectMapper;


        private PrenotazioneDto prenotazioneDto;

        @BeforeEach
        void setUp() {

        prenotazioneDto = new PrenotazioneDto();
        prenotazioneDto.setId(1);

    }


    @Test
    void trovaPerData_positive() throws Exception {

        when(prenotazioneService.trovaPrenotazioniPerData(any(LocalDate.class)))
                .thenReturn(List.of(prenotazioneDto));

        mockMvc.perform(get("/prenotazione/data")
                        .param("data","2026-07-18"))
                .andExpect(status().isOk());

        verify(prenotazioneService)
                .trovaPrenotazioniPerData(any(LocalDate.class));

    }
    @Test
    void trovaPerData_negative() throws Exception {

        when(prenotazioneService.trovaPrenotazioniPerData(any(LocalDate.class)))
                .thenThrow(new RuntimeException("Errore ricerca prenotazioni"));

        assertThrows(Exception.class, () ->
                mockMvc.perform(get("/prenotazione/data")
                        .param("data", "2026-07-18"))
        );

        verify(prenotazioneService)
                .trovaPrenotazioniPerData(LocalDate.of(2026,7,18));

    }



        @Test
        void createPrenotazione_positive() throws Exception {

        when(prenotazioneService.effetuaPrenotazione(any(),eq(1)))
                .thenReturn(prenotazioneDto);

        mockMvc.perform(post("/prenotazione")
                        .param("utenteId","1")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(objectMapper.writeValueAsString(prenotazioneDto)))
                .andExpect(status().isOk());

        verify(prenotazioneService)
                .effetuaPrenotazione(any(),eq(1));
    }

    @Test
    void createPrenotazione_negative() throws Exception {

        when(prenotazioneService.effetuaPrenotazione(any(), eq(1)))
                .thenThrow(new Exception());

        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        post("/prenotazione")
                                .param("utenteId", "1")
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(objectMapper.writeValueAsString(prenotazioneDto))
                )
        );

    }

    @Test
    void cancellaPrenotazione_positive() throws Exception {

        when(prenotazioneService.cancellaPrenotazione(1,1))
                .thenReturn(List.of(prenotazioneDto));

        mockMvc.perform(delete("/prenotazione/1")
                        .param("idPrenotazione","1"))
                .andExpect(status().isOk());

        verify(prenotazioneService)
                .cancellaPrenotazione(1,1);

    }
    @Test
    void cancellaPrenotazione_negative() throws Exception {

        doThrow(new RuntimeException("Prenotazione non trovata"))
                .when(prenotazioneService)
                .cancellaPrenotazione(1, 1);

        assertThrows(Exception.class, () ->
                mockMvc.perform(
                        delete("/prenotazione/1")
                                .param("idPrenotazione", "1")
                )
        );

        verify(prenotazioneService)
                .cancellaPrenotazione(1, 1);
    }

    @Test
    void listaPrenotazioniSingoloUtente_positive() throws Exception {

        when(prenotazioneService.getListaPrenotazioniConPaginazione(1))
                .thenReturn(new PageImpl<>(List.of(prenotazioneDto)));

        mockMvc.perform(get("/prenotazione/listaSingoloUtente/1"))
                .andExpect(status().isOk());

    }

    @Test
    void listaPrenotazioniSingoloUtente_negative() throws Exception {

        doThrow(new RuntimeException("Errore"))
                .when(prenotazioneService)
                .getListaPrenotazioniConPaginazione(1);

        assertThrows(ServletException.class, () ->
                mockMvc.perform(
                        get("/prenotazione/listaSingoloUtente/1")
                )
        );

        verify(prenotazioneService)
                .getListaPrenotazioniConPaginazione(1);
    }



    }

