package test.ControllerTest;

import it.controller.CorsoController;
import it.dto.CorsoDto;
import it.enumerated.GiorniEnum;
import it.service.CorsoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class CorsoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CorsoService corsoService;

    @InjectMocks
    private CorsoController corsoController;

    private CorsoDto sampleDto;

@BeforeEach
void setUp() {
    FormattingConversionService conversionService = new FormattingConversionService();
    // Converter per LocalTime
    conversionService.addConverter(String.class, LocalTime.class, LocalTime::parse);
    // Registrar la conversione standard da String ad Enum per Spring
    conversionService.addConverter(String.class, GiorniEnum.class, source -> {
        try {
            return GiorniEnum.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum non valido: " + source);
        }
    });

    mockMvc = MockMvcBuilders.standaloneSetup(corsoController)
            .setConversionService(conversionService)
            .setControllerAdvice(new TestExceptionHandler())
            .build();

    sampleDto = new CorsoDto();
}

    @ControllerAdvice
    static class TestExceptionHandler {
        @ExceptionHandler({
                MethodArgumentTypeMismatchException.class,
                MethodArgumentConversionNotSupportedException.class,
                IllegalArgumentException.class,
                ConversionFailedException.class
        })
        public ResponseEntity<Void> handleConversionExceptions() {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //corsi ne lgiorno
    @Test
    void getCorsoByGiorno_ShouldReturnList() throws Exception {
        GiorniEnum giorno = GiorniEnum.LUNEDI;
        List<CorsoDto> corsi = List.of(sampleDto);
        when(corsoService.trovaPerGiorno(giorno)).thenReturn(corsi);
        mockMvc.perform(get("/corso/giorno/{giorno}", giorno)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
        verify(corsoService, times(1)).trovaPerGiorno(giorno);
    }

    //corsi by sport
    @Test
    void getCorsiBySport_ShouldReturnList() throws Exception {
        String sport = "Tennis";
        List<CorsoDto> corsi = List.of(sampleDto);
        when(corsoService.trovaPerSport(sport)).thenReturn(corsi);
        mockMvc.perform(get("/corso/sport/{sport}", sport)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(corsoService, times(1)).trovaPerSport(sport);
    }

    //corsi da ora in poi 200
    @Test
    void getCorsiDaOraInPoi_ShouldReturnList() throws Exception {
        List<CorsoDto> corsi = List.of(sampleDto);
        when(corsoService.trovaDaOra(any(LocalTime.class))).thenReturn(corsi);
        mockMvc.perform(get("/corso/da-ora")
                        .param("ora", "14:30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(corsoService, times(1)).trovaDaOra(any(LocalTime.class));
    }

    //get corsi
    @Test
    void getCorsiByCampo_ShouldReturnList() throws Exception {
        Integer campoId = 5;
        List<CorsoDto> corsi = List.of(sampleDto);
        when(corsoService.ottieniCorsiPerCampo(campoId)).thenReturn(corsi);
        mockMvc.perform(get("/corso/campo/{campoId}/corsi", campoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        verify(corsoService, times(1)).ottieniCorsiPerCampo(campoId);
    }

    //bad sport
    @Test
    void getCorsiBySport_WhenSportDoesNotExist_ShouldReturnEmptyList() throws Exception {
        String sportInesistente = "Quidditch";
        when(corsoService.trovaPerSport(sportInesistente)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/corso/sport/{sport}", sportInesistente)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    //bad giorno
    @Test
    void getCorsoByGiorno_WithInvalidGiorno_ShouldReturnBadRequest() throws Exception {
        String giornoInvalido = "GiornoInesistente";
        mockMvc.perform(get("/corso/giorno/{giorno}", giornoInvalido)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    //bad date
    @Test
    void getCorsiDaOraInPoi_WithInvalidTimeFormat_ShouldReturnBadRequest() throws Exception {
        String orarioSballato = "not-a-time";
        mockMvc.perform(get("/corso/da-ora")
                        .param("ora", orarioSballato)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}