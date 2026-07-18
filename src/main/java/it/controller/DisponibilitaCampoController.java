package it.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dto.DisponibilitaCampoDto;
import it.service.DisponibilitaCampoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/disponibilitaCampo")
public class DisponibilitaCampoController {

    private final DisponibilitaCampoService disponibilitaCampoService;

    public DisponibilitaCampoController(DisponibilitaCampoService disponibilitaCampoService) {
        this.disponibilitaCampoService = disponibilitaCampoService;
    }

    @GetMapping("/listaDisponibilita")
    @Operation(
            summary = "Lista disponibilità campi",
            description = "Restituisce tutte le disponibilità dei campi presenti nel sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista disponibilità recuperata correttamente"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<DisponibilitaCampoDto>> getAllDisponibilita() {

        return ResponseEntity.ok(disponibilitaCampoService.getAllDisponibilita());
    }

    @GetMapping("/dataDisponibilita")
    @Operation(
            summary = "Trova disponibilità per data",
            description = "Restituisce le disponibilità dei campi nella data indicata"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilità recuperate correttamente"),
            @ApiResponse(responseCode = "400", description = "Data non valida"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<DisponibilitaCampoDto>> getDisponibilitaByData(
            @RequestParam LocalDate data) throws Exception {

        return ResponseEntity.ok(disponibilitaCampoService.getDisponibilitaByData(data));
    }

    @GetMapping("/campo/{idCampo}")
    @Operation(
            summary = "Trova disponibilità di un campo",
            description = "Restituisce tutte le disponibilità associate al campo indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilità del campo recuperate correttamente"),
            @ApiResponse(responseCode = "404", description = "Campo non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<DisponibilitaCampoDto>> getDisponibilitaByCampo(
            @PathVariable Integer idCampo) throws Exception {

        return ResponseEntity.ok(disponibilitaCampoService.getDisponibilitaByCampo(idCampo));
    }
}
