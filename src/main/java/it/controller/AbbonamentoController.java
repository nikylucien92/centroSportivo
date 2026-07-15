package it.controller;

import it.dto.AbbonamentoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abbonamento")
@CrossOrigin(origins = "http://localhost:4200")
public class AbbonamentoController extends AbstractController<AbbonamentoDto> {
}

// Todo: creare metodo findbystato, findbyid, findbydata, findbytipo
// Todo: creare metodo getabbonamenti che scadono nel mese inserito
