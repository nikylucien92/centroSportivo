package it.controller;

import it.dto.UtenteDto;
import it.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UtenteController extends AbstractController<UtenteDto> {

    private final UtenteService utenteService;

    public ResponseEntity<UtenteDto> getByEmail(
            @RequestParam("email") String email) throws Exception {
        return ResponseEntity.ok( utenteService.findByEmail(email));
    }

}
