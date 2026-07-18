package it.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dto.AbbonamentoDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;
import it.service.AbbonamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/abbonamento")
@CrossOrigin(origins = "http://localhost:4200")
public class AbbonamentoController
        extends AbstractController<AbbonamentoDto> {



    @Autowired
    private AbbonamentoService abbonamentoService;


    // Cerca per tipologia
    @GetMapping("/tipo/{tipo}")
    @Operation(
            summary = "Trova abbonamenti per tipo",
            description = "Restituisce gli abbonamenti della tipologia indicata"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista abbonamenti recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Tipologia non valida"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findByTipo(
            @PathVariable AbbonamentoTypeEnum tipo) {


        return abbonamentoService.findByTipo(tipo);

    }





    // Cerca per stato
    @GetMapping("/stato/{stato}")
    @Operation(
            summary = "Trova abbonamenti per stato",
            description = "Restituisce gli abbonamenti con lo stato indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista abbonamenti recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Stato non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findByStato(
            @PathVariable StatoAbbonamentoEnum stato) {


        return abbonamentoService.findByStato(stato);

    }



    // Cerca abbonamento di un cliente
    @GetMapping("/utente/{idUtente}")
    @Operation(
            summary = "Trova abbonamento utente",
            description = "Restituisce l'abbonamento associato al cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Abbonamento trovato correttamente"),
            @ApiResponse(responseCode = "404", description = "Abbonamento o utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public AbbonamentoDto findByUtenteId(
            @PathVariable Integer idUtente) {


        return abbonamentoService.findByUtenteId(idUtente);

    }




    // Abbonamenti in scadenza tra due date
    @GetMapping("/scadenza")
    @Operation(
            summary = "Trova abbonamenti in scadenza",
            description = "Restituisce gli abbonamenti con data fine compresa nel periodo indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista abbonamenti recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Intervallo di date non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findInScadenza(
            @RequestParam LocalDate inizio,
            @RequestParam LocalDate fine) {


        return abbonamentoService.findInScadenza(
                inizio,
                fine
        );

    }


    // Abbonamenti scaduti
    @GetMapping("/scaduti")
    @Operation(
            summary = "Trova abbonamenti scaduti",
            description = "Restituisce gli abbonamenti terminati"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista abbonamenti recuperata correttamente"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findScaduti() {


        return abbonamentoService.findScaduti();

    }







    // Abbonamenti validi oggi
    @GetMapping("/attivi")
    @Operation(
            summary = "Trova abbonamenti attivi",
            description = "Restituisce gli abbonamenti validi nella data odierna"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista abbonamenti recuperata correttamente"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findValidiOggi() {


        return abbonamentoService.findValidiOggi();

    }






    // Creazione nuovo abbonamento
    @PostMapping
    @Operation(
            summary = "Crea un nuovo abbonamento",
            description = "Crea un abbonamento calcolando automaticamente periodo e stato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Abbonamento creato correttamente"),
            @ApiResponse(responseCode = "400", description = "Dati dell'abbonamento non validi"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public AbbonamentoDto creaAbbonamento(
            @RequestBody AbbonamentoDto dto) {


        return abbonamentoService.creaAbbonamento(dto);

    }

    @PutMapping("/{id}/rinnova")
    @Operation(
            summary = "Rinnova abbonamento",
            description = "Estende la data di scadenza dell'abbonamento"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Abbonamento rinnovato correttamente"),
            @ApiResponse(responseCode = "404", description = "Abbonamento non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public AbbonamentoDto rinnovaAbbonamento(
            @PathVariable Integer id) {


        return abbonamentoService.rinnovaAbbonamento(id);
    }

}