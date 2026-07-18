package it.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dto.PagamentoDto;
import it.enumerated.PagamentoStatoEnum;
import it.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamento")
@CrossOrigin(origins = "http://localhost:4200")
public class PagamentoController extends AbstractController<PagamentoDto>{

    private final PagamentoService pagamentoService;

    @GetMapping("/utente/{utenteId}")
    @Operation(
            summary = "Trova pagamenti per utente",
            description = "Restituisce tutti i pagamenti associati all'utente indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista pagamenti recuperata correttamente"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<PagamentoDto>> getPagamentoPerUtente(
            @PathVariable(name = "utenteId") Integer utenteId){
        return ResponseEntity.ok(pagamentoService.getPagamentoPerUtenteId(utenteId));
    }

    @GetMapping("/stato/{stato}")
    @Operation(
            summary = "Trova pagamenti per stato",
            description = "Restituisce tutti i pagamenti con lo stato indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista pagamenti recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Stato pagamento non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<PagamentoDto>> getPagamentiPerStato(
            @PathVariable(name = "stato")PagamentoStatoEnum stato){
        return ResponseEntity.ok(pagamentoService.getPagamentiPerStato(stato));
    }

     @GetMapping("/giorno/{giorno}")
     @Operation(
             summary = "Trova pagamenti per giorno",
             description = "Restituisce tutti i pagamenti effettuati nella data indicata"
     )
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Lista pagamenti recuperata correttamente"),
             @ApiResponse(responseCode = "400", description = "Formato data non valido"),
             @ApiResponse(responseCode = "500", description = "Errore interno del server")
     })
    public ResponseEntity<List<PagamentoDto>> getPagamentiPerGiorno(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate giorno) {
        return ResponseEntity.ok(
                pagamentoService.getPagamentiPerGiorno(giorno)
        );
    }


    @GetMapping
    @Operation(
            summary = "Ricerca pagamenti",
            description = "Restituisce i pagamenti filtrati opzionalmente per utente, stato e giorno"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista pagamenti recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Parametri di ricerca non validi"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<List<PagamentoDto>> getPagamenti(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) PagamentoStatoEnum stato,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yy-MM-dd") LocalDate giorno){
        return ResponseEntity.ok(
                pagamentoService.getPagamenti(
                        userId,stato,giorno)
        );
    }




}
