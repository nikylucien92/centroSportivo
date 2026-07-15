package it.controller;

import it.dto.CorsoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corso")
@CrossOrigin(origins = "http://localhost:4200")
public class CorsoController extends AbstractController<CorsoDto> {
}
// toDo: creare metodo findby nome, livello, sport, campo , giorno, ora , prezzo