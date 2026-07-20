package test.ControllerTest;

import it.controller.DisponibilitaCampoController;
import it.dto.DisponibilitaCampoDto;
import it.service.DisponibilitaCampoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DisponibilitaCampoControllerTest {

    private MockMvc mockMvc;
    private DisponibilitaCampoService disponibilitaCampoService;

    @BeforeEach
    void setUp() {
        disponibilitaCampoService = Mockito.mock(DisponibilitaCampoService.class);

        DisponibilitaCampoController controller =
                new DisponibilitaCampoController(disponibilitaCampoService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    //creato dto mock
    private DisponibilitaCampoDto creaDto() {
        DisponibilitaCampoDto dto = new DisponibilitaCampoDto();
        dto.setId(1);
        dto.setStatoDisponibilita("LIBERO");
        dto.setData(LocalDateTime.of(2025, 1, 10, 0, 0));
        dto.setOraInizio(LocalDateTime.of(2025, 1, 10, 10, 0));
        dto.setOraFine(LocalDateTime.of(2025, 1, 10, 11, 0));
        return dto;
    }

    //get all disponibilita
    @Test
    void getAllDisponibilita_ok() throws Exception {
        Mockito.when(disponibilitaCampoService.getAllDisponibilita())
                .thenReturn(List.of(creaDto()));
        mockMvc.perform(get("/disponibilitaCampo/listaDisponibilita"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statoDisponibilita").value("LIBERO"));
        verify(disponibilitaCampoService, times(1)).getAllDisponibilita();
    }

    //se e vuota la lista
    @Test
    void getAllDisponibilita_listaVuota_ok() throws Exception {
        Mockito.when(disponibilitaCampoService.getAllDisponibilita())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/disponibilitaCampo/listaDisponibilita"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(disponibilitaCampoService, times(1)).getAllDisponibilita();
    }

    //test by data
    @Test
    void getDisponibilitaByData_ok() throws Exception {
        LocalDate data = LocalDate.of(2025, 1, 10);
        Mockito.when(disponibilitaCampoService.getDisponibilitaByData(data))
                .thenReturn(List.of(creaDto()));
        mockMvc.perform(get("/disponibilitaCampo/dataDisponibilita")
                        .param("data", "2025-01-10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        verify(disponibilitaCampoService, times(1)).getDisponibilitaByData(data);
    }

    //manca param data nella req
    @Test
    void getDisponibilitaByData_parametroMancante_badRequest() throws Exception {
        mockMvc.perform(get("/disponibilitaCampo/dataDisponibilita"))
                .andExpect(status().isBadRequest());
    }

    //data foramt sbaglaita
    @Test
    void getDisponibilitaByData_formatoDataErrato_badRequest() throws Exception {
        mockMvc.perform(get("/disponibilitaCampo/dataDisponibilita")
                        .param("data", "10/01/2025"))
                .andExpect(status().isBadRequest());
    }

    //disponibilta per campo
    @Test
    void getDisponibilitaByCampo_ok() throws Exception {
        Mockito.when(disponibilitaCampoService.getDisponibilitaByCampo(1))
                .thenReturn(List.of(creaDto()));
        mockMvc.perform(get("/disponibilitaCampo/campo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statoDisponibilita").value("LIBERO"));
        verify(disponibilitaCampoService, times(1)).getDisponibilitaByCampo(1);
    }


    //test id sbaglitao
    @Test
    void getDisponibilitaByCampo_idTipoErrato_badRequest() throws Exception {
        mockMvc.perform(get("/disponibilitaCampo/campo/abc"))
                .andExpect(status().isBadRequest());
    }
}