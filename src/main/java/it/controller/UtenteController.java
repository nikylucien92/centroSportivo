package it.controller;

import it.dto.UtenteDto;
import it.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController extends AbstractController<UtenteDto> {

    private final UtenteService utenteService;
    @GetMapping("/email")
    public ResponseEntity<UtenteDto> getByEmail(
            @RequestParam("email") String email) throws Exception {
        return ResponseEntity.ok( utenteService.findByEmail(email));
    }

    @PutMapping("{idUtente}/telefono")
    public ResponseEntity<UtenteDto> cambiaTelefono(
            @PathVariable Integer idUtente,
            @RequestParam String telefono) throws Exception {

        UtenteDto utenteDto = utenteService.cambioCell(idUtente, telefono);

        return ResponseEntity.ok(utenteDto);
    }


    @PutMapping("{idUtente}/email")
    public ResponseEntity<UtenteDto> cambiaEmail(
            @PathVariable Integer idUtente,
            @RequestParam String email) throws Exception {

        UtenteDto utenteDto = utenteService.cambioEmail(idUtente, email);

        return ResponseEntity.ok(utenteDto);
    }

}
