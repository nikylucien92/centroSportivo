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


}
