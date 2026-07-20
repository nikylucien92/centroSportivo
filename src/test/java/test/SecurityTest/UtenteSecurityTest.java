package test.SecurityTest;


import it.controller.UtenteController;
import it.dto.UtenteDto;
import it.service.UtenteService;
import it.CentroSportivoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UtenteController.class)
@ContextConfiguration(classes = CentroSportivoApplication.class)
@EnableMethodSecurity(prePostEnabled = true)
class UtenteSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UtenteService utenteService;

    private UtenteDto sampleUtenteDto;

    @BeforeEach
    void setUp() {
        sampleUtenteDto = new UtenteDto();
        sampleUtenteDto.setId(1);
        sampleUtenteDto.setNome("Mario");
        sampleUtenteDto.setCognome("Rossi");
        sampleUtenteDto.setEmail("mario.rossi@example.com");
        sampleUtenteDto.setTelefono("3291234567");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getByEmail_Positive() throws Exception {
        // Testa il recupero di un utente per email con successo
        String email = "mario.rossi@example.com";
        when(utenteService.findByEmail(email)).thenReturn(sampleUtenteDto);

        mockMvc.perform(get("/utente/email")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mario"))
                .andExpect(jsonPath("$.cognome").value("Rossi"))
                .andExpect(jsonPath("$.email").value(email));

        verify(utenteService, times(1)).findByEmail(email);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getByEmail_InvalidEmail() throws Exception {
        // Testa il comportamento con un'email vuota (nessun utente trovato)
        String email = "";
        when(utenteService.findByEmail(email)).thenReturn(null);

        mockMvc.perform(get("/utente/email")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(utenteService, times(1)).findByEmail(email);
    }

    @Test
    @WithMockUser(roles = "USER")
    void cambiaTelefono_Positive() throws Exception {
        // Testa la modifica del numero di telefono con successo
        Integer idUtente = 1;
        String nuovoTelefono = "3299876543";
        sampleUtenteDto.setTelefono(nuovoTelefono);

        when(utenteService.cambioCell(idUtente, nuovoTelefono))
                .thenReturn(sampleUtenteDto);

        mockMvc.perform(put("/utente/{idUtente}/telefono", idUtente)
                        .param("telefono", nuovoTelefono)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono").value(nuovoTelefono));

        verify(utenteService, times(1)).cambioCell(idUtente, nuovoTelefono);
    }

    @Test
    @WithMockUser(roles = "USER")
    void cambiaTelefono_InvalidPhone() throws Exception {
        // Testa la modifica del telefono con un numero non valido
        Integer idUtente = 1;
        String telefono = "invalid";
        when(utenteService.cambioCell(idUtente, telefono))
                .thenReturn(sampleUtenteDto);

        mockMvc.perform(put("/utente/{idUtente}/telefono", idUtente)
                        .param("telefono", telefono)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(utenteService, times(1)).cambioCell(idUtente, telefono);
    }

    @Test
    @WithMockUser(roles = "USER")
    void cambiaEmail_Positive() throws Exception {
        // Testa la modifica dell'email con successo
        Integer idUtente = 1;
        String nuovaEmail = "mario.newnew@example.com";
        sampleUtenteDto.setEmail(nuovaEmail);

        when(utenteService.cambioEmail(idUtente, nuovaEmail))
                .thenReturn(sampleUtenteDto);

        mockMvc.perform(put("/utente/{idUtente}/email", idUtente)
                        .param("email", nuovaEmail)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(nuovaEmail));

        verify(utenteService, times(1)).cambioEmail(idUtente, nuovaEmail);
    }

    @Test
    @WithMockUser(roles = "USER")
    void cambiaEmail_InvalidEmail() throws Exception {
        // Testa la modifica dell'email con un formato non valido
        Integer idUtente = 1;
        String email = "invalid-email";
        when(utenteService.cambioEmail(idUtente, email))
                .thenReturn(sampleUtenteDto);

        mockMvc.perform(put("/utente/{idUtente}/email", idUtente)
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(utenteService, times(1)).cambioEmail(idUtente, email);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void promuoviAdAdmin_WithAdminRole_Success() throws Exception {
        // Testa la promozione di un utente a ADMIN con ruolo ADMIN (autorizzato)
        Integer idUtente = 1;
        when(utenteService.upgradeToAdmin(idUtente))
                .thenReturn(sampleUtenteDto);

        mockMvc.perform(put("/utente/{id}/promuovi", idUtente)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mario"));

        verify(utenteService, times(1)).upgradeToAdmin(idUtente);
    }

    @Test
    @WithMockUser(roles = "ROOT")
    void promuoviAdAdmin_WithRootRole_Success() throws Exception {
        // Testa la promozione di un utente a ADMIN con ruolo ROOT (autorizzato)
        Integer idUtente = 2;
        UtenteDto utenteDaPromuovere = new UtenteDto();
        utenteDaPromuovere.setId(2);
        utenteDaPromuovere.setNome("Giovanni");

        when(utenteService.upgradeToAdmin(idUtente))
                .thenReturn(utenteDaPromuovere);

        mockMvc.perform(put("/utente/{id}/promuovi", idUtente)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Giovanni"));

        verify(utenteService, times(1)).upgradeToAdmin(idUtente);
    }

    @Test
    @WithMockUser(roles = "USER")
    void promuoviAdAdmin_WithoutAdminRole_Forbidden() throws Exception {
        // Testa che un utente con ruolo USER non può promuovere altri utenti (403 Forbidden)
        Integer idUtente = 1;

        mockMvc.perform(put("/utente/{id}/promuovi", idUtente)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isForbidden());

        verify(utenteService, never()).upgradeToAdmin(anyInt());
    }

    @Test
    void promuoviAdAdmin_Unauthenticated_Unauthorized() throws Exception {
        // Testa che un utente non autenticato non può accedere all'endpoint (401 Unauthorized)
        Integer idUtente = 1;

        mockMvc.perform(put("/utente/{id}/promuovi", idUtente)
                        .contentType(MediaType.APPLICATION_JSON)
                        // obligatorio includere csrf in spring security per ol put
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(utenteService, never()).upgradeToAdmin(anyInt());
    }
}