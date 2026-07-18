package progetto.centroSportivo.centroSportivo.ControllerTest;

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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UtenteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UtenteService utenteService;

    @InjectMocks
    private UtenteController utenteController;

    private UtenteDto sampleUtenteDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(utenteController)
                .apply(springSecurity())
                .build();
        // Configura un DTO utente di esempio
        sampleUtenteDto = new UtenteDto();
        sampleUtenteDto.setId(1);
        sampleUtenteDto.setNome("Mario");
        sampleUtenteDto.setCognome("Rossi");
        sampleUtenteDto.setEmail("mario.rossi@example.com");
      //  sampleUtenteDto.setRuolo(RuoloEnum.USER);
    }



    //user cerca info
    @Test
    @WithMockUser(roles = "USER")
    void getUtenteById_ShouldReturnUtente() throws Exception {
        Integer utenteId = 1;
        when(utenteService.read(utenteId)).thenReturn(sampleUtenteDto);
        mockMvc.perform(get("/utente/{id}", utenteId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mario"))
                .andExpect(jsonPath("$.cognome").value("Rossi"))
                .andExpect(jsonPath("$.email").value("mario.rossi@example.com"));

        verify(utenteService, times(1)).read(utenteId);
    }

    //all utenti per admin
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUtenti_ShouldReturnList() throws Exception {
        List<UtenteDto> utenti = List.of(sampleUtenteDto);
        when(utenteService.getAll()).thenReturn(utenti);
        mockMvc.perform(get("/utente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(utenteService, times(1)).getAll();
    }

    //admin elimina
    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_WhenUserIsAdmin_ShouldReturn204NoContent() throws Exception {
        Integer utenteIdDaEliminare = 1;
        doNothing().when(utenteService).delete(utenteIdDaEliminare);
        mockMvc.perform(delete("/utente/{id}", utenteIdDaEliminare)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(utenteService, times(1)).delete(utenteIdDaEliminare);
    }

    //user elimina non puo dude
    @Test
    @WithMockUser(roles = "USER")
    void deleteUser_WhenUserIsStandardUser_ShouldReturn403Forbidden() throws Exception {
        Integer utenteIdDaEliminare = 1;
        mockMvc.perform(delete("/utente/{id}", utenteIdDaEliminare)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(utenteService, never()).delete(anyInt());
    }

    //401 easy
    @Test
    void deleteUser_WhenUnauthenticated_ShouldReturn401Unauthorized() throws Exception {
        Integer utenteIdDaEliminare = 1;
        mockMvc.perform(delete("/utente/{id}", utenteIdDaEliminare)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(utenteService, never()).delete(anyInt());
    }
}