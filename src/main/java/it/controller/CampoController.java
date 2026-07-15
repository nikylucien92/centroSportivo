package it.controller;

import it.dto.CampoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campo")
@CrossOrigin(origins = "http://localhost:4200")
public class CampoController extends AbstractController<CampoDto> {
}
 // Todo: creare metodo find per tipologia di campo, prezzo e coperto
