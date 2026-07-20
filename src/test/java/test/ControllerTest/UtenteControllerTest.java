package test.ControllerTest;

import it.CentroSportivoApplication;
import it.controller.UtenteController;
import it.dto.UtenteDto;
import it.enumerated.RuoloEnum;
import it.service.UtenteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UtenteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtenteService utenteService;

    @InjectMocks
    private UtenteController utenteController;

    private UtenteDto sampleUtenteDto;


    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(utenteController)
                .build();


        sampleUtenteDto = new UtenteDto();

        sampleUtenteDto.setId(1);
        sampleUtenteDto.setNome("Mario");
        sampleUtenteDto.setCognome("Rossi");
        sampleUtenteDto.setEmail("mario.rossi@example.com");
        sampleUtenteDto.setTelefono("3333333333");
    }


    @Test
    void findByEmail_ShouldReturnUtente() throws Exception {

        when(utenteService.findByEmail("mario.rossi@example.com"))
                .thenReturn(sampleUtenteDto);


        mockMvc.perform(get("/utente/email")
                        .param("email","mario.rossi@example.com")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mario"));


        verify(utenteService)
                .findByEmail("mario.rossi@example.com");
    }


    @Test
    void cambioTelefono_ShouldReturnUpdatedUser() throws Exception {


        when(utenteService.cambioCell(1,"3333333333"))
                .thenReturn(sampleUtenteDto);


        mockMvc.perform(put("/utente/1/telefono")
                        .param("telefono","3333333333"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mario"));


        verify(utenteService)
                .cambioCell(1,"3333333333");
    }


    @Test
    void promuoviAdAdmin_ShouldReturnUpdatedUser() throws Exception {


        when(utenteService.upgradeToAdmin(1))
                .thenReturn(sampleUtenteDto);


        mockMvc.perform(
                        put("/utente/1/promuovi")
                )
                .andExpect(status().isOk());


        verify(utenteService)
                .upgradeToAdmin(1);
    }

    @Test
    void cambioEmail_ShouldReturnUpdatedUser() throws Exception {


        when(utenteService.cambioEmail(1,"nuova@email.com"))
                .thenReturn(sampleUtenteDto);


        mockMvc.perform(
                        put("/utente/1/email")
                                .param("email","nuova@email.com")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("mario.rossi@example.com"));


        verify(utenteService)
                .cambioEmail(1,"nuova@email.com");
    }
}