package it.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dto.CorsoDto;
import it.enumerated.GiorniEnum;
import it.model.Corso;
import it.service.CorsoService;
import it.service.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/corso")
@CrossOrigin(origins = "http://localhost:4200")
public class CorsoController extends AbstractController<CorsoDto> {

    private final CorsoService corsoService;

	protected CorsoController(ServiceDto<CorsoDto> service, CorsoService corsoService) {
		super(service);
		this.corsoService = corsoService;
	}


	@GetMapping("/giorno/{giorno}")
    @Operation(
            summary = "Trova corsi per giorno",
            description = "Restituisce tutti i corsi programmati nel giorno indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista corsi recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Giorno non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<CorsoDto>> getCorsoByGiorno(@PathVariable GiorniEnum giorno) {
        return ResponseEntity.ok(corsoService.trovaPerGiorno(giorno));
    }

    @GetMapping("/sport/{sport}")
    @Operation(
            summary = "Trova corsi per sport",
            description = "Restituisce tutti i corsi associati allo sport indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista corsi recuperata correttamente"),
            @ApiResponse(responseCode = "404", description = "Sport non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<CorsoDto>> getCorsiBySport(@PathVariable String sport) {
        return ResponseEntity.ok(corsoService.trovaPerSport(sport));
    }

    @GetMapping("/da-ora")
    @Operation(
            summary = "Trova corsi da un determinato orario",
            description = "Restituisce i corsi disponibili a partire dall'orario indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista corsi recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Formato orario non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<CorsoDto>> getCorsiDaOraInPoi(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime ora) {
        return ResponseEntity.ok(corsoService.trovaDaOra(ora));
    }

    @GetMapping("/campo/{campoId}/corsi")
    @Operation(
            summary = "Trova corsi per campo",
            description = "Restituisce tutti i corsi associati al campo indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista corsi recuperata correttamente"),
            @ApiResponse(responseCode = "404", description = "Campo non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<CorsoDto>> getCorsiByCampo(@PathVariable Integer campoId) {
        List<CorsoDto> corsi = corsoService.ottieniCorsiPerCampo(campoId);
        return ResponseEntity.ok(corsi);
    }

}
