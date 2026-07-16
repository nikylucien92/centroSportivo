package it.controller;

import it.dto.AbbonamentoDto;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.enumerated.AbbonamentoTypeEnum;
import it.service.AbbonamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;


@RestController
@RequestMapping("/abbonamento")
@CrossOrigin(origins = "http://localhost:4200")
public class AbbonamentoController
        extends AbstractController<AbbonamentoDto> {



    @Autowired
    private AbbonamentoService abbonamentoService;




    @GetMapping("/tipo/{tipo}")
    @Operation(
            summary = "Trova abbonamenti per tipo",
            description = "Restituisce gli abbonamenti appartenenti al tipo indicato"
    )
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200",
                    description = "Lista recuperata correttamente"),

            @ApiResponse(responseCode = "500",
                    description = "Errore interno del server")
    })
    public List<AbbonamentoDto> findByTipo(
            @PathVariable AbbonamentoTypeEnum tipo){

        return abbonamentoService.findByTipo(tipo);

    }




    @GetMapping("/stato/{stato}")
    @Operation(
            summary = "Trova abbonamenti per stato",
            description = "Restituisce gli abbonamenti con lo stato indicato"
    )
    public List<AbbonamentoDto> findByStato(
            @PathVariable String stato){

        return abbonamentoService.findByStato(stato);

    }





    @GetMapping("/prezzo/{prezzo}")
    @Operation(
            summary = "Trova abbonamenti per prezzo",
            description = "Restituisce gli abbonamenti con il prezzo indicato"
    )
    public List<AbbonamentoDto> findByPrezzo(
            @PathVariable Double prezzo){

        return abbonamentoService.findByPrezzo(prezzo);

    }





    @GetMapping("/prezzo-massimo/{prezzo}")
    @Operation(
            summary = "Trova abbonamenti sotto un prezzo massimo",
            description = "Restituisce gli abbonamenti con prezzo minore o uguale"
    )
    public List<AbbonamentoDto> cercaPerPrezzoMassimo(
            @PathVariable Double prezzo){

        return abbonamentoService.cercaPerPrezzoMassimo(prezzo);

    }

    @GetMapping("/utente/{idUtente}")
    @Operation(
            summary = "Trova abbonamento di un utente",
            description = "Restituisce l'abbonamento associato ad un utente"
    )
    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Abbonamento trovato correttamente"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Abbonamento non trovato"
            )
    })
    public AbbonamentoDto findByUtenteId(
            @PathVariable Integer idUtente){

        return abbonamentoService.findByUtenteId(idUtente);

    }

}
