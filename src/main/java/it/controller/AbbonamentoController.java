package it.controller;

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
    public List<AbbonamentoDto> findScaduti() {


        return abbonamentoService.findScaduti();

    }







    // Abbonamenti validi oggi
    @GetMapping("/attivi")
    @Operation(
            summary = "Trova abbonamenti attivi",
            description = "Restituisce gli abbonamenti validi nella data odierna"
    )
    public List<AbbonamentoDto> findValidiOggi() {


        return abbonamentoService.findValidiOggi();

    }






    // Creazione nuovo abbonamento
    @PostMapping
    @Operation(
            summary = "Crea un nuovo abbonamento",
            description = "Crea un abbonamento calcolando automaticamente periodo e stato"
    )
    public AbbonamentoDto creaAbbonamento(
            @RequestBody AbbonamentoDto dto) {


        return abbonamentoService.creaAbbonamento(dto);

    }

    @PutMapping("/{id}/rinnova")
    @Operation(
            summary = "Rinnova abbonamento",
            description = "Estende la data di scadenza dell'abbonamento"
    )
    public AbbonamentoDto rinnovaAbbonamento(
            @PathVariable Integer id) {


        return abbonamentoService.rinnovaAbbonamento(id);
    }

}