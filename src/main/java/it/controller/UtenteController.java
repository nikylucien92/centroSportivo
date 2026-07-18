package it.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.dto.UtenteDto;
import it.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utente")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController extends AbstractController<UtenteDto> {

    private final UtenteService utenteService;
    @GetMapping("/email")
    @Operation(
            summary = "Trova utente per email",
            description = "Restituisce l'utente associato all'indirizzo email indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utente trovato correttamente"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "400", description = "Email non valida"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<UtenteDto> getByEmail(
            @RequestParam("email") String email) throws Exception {
        return ResponseEntity.ok( utenteService.findByEmail(email));
    }

    @PutMapping("{idUtente}/telefono")
    @Operation(
            summary = "Modifica numero di telefono utente",
            description = "Aggiorna il numero di telefono dell'utente indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Numero di telefono modificato correttamente"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "400", description = "Numero di telefono non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<UtenteDto> cambiaTelefono(
            @PathVariable Integer idUtente,
            @RequestParam String telefono) throws Exception {

        UtenteDto utenteDto = utenteService.cambioCell(idUtente, telefono);

        return ResponseEntity.ok(utenteDto);
    }


    @PutMapping("{idUtente}/email")
    @Operation(
            summary = "Modifica email utente",
            description = "Aggiorna l'indirizzo email dell'utente indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email modificata correttamente"),
            @ApiResponse(responseCode = "404", description = "Utente non trovato"),
            @ApiResponse(responseCode = "400", description = "Email non valida"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public ResponseEntity<UtenteDto> cambiaEmail(
            @PathVariable Integer idUtente,
            @RequestParam String email) throws Exception {

        UtenteDto utenteDto = utenteService.cambioEmail(idUtente, email);

        return ResponseEntity.ok(utenteDto);
    }

    @PutMapping("/{id}/promuovi")
    @PreAuthorize("hasRole('ADMIN', 'ROOT')")
    public ResponseEntity<UtenteDto> promuoviAdAdmin(@PathVariable Integer id) throws Exception {
        UtenteDto utenteAggiornato = utenteService.upgradeToAdmin(id);
        return ResponseEntity.ok(utenteAggiornato);
    }



}
