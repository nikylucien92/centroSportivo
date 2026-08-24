package it.controller;

import it.dto.CampoDto;
import it.service.CampoService;
import it.service.ServiceDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/campo")
@CrossOrigin(origins = "http://localhost:4200")
public class CampoController extends AbstractController<CampoDto> {


    @Autowired
    private CampoService campoService;

	protected CampoController(ServiceDto<CampoDto> service) {
		super(service);
	}


	@GetMapping("/nome/{nome}")
    @Operation(
            summary = "Trova campo per nome",
            description = "Restituisce un campo tramite il nome indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Campo trovato correttamente"),
            @ApiResponse(responseCode = "400", description = "Nome non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public CampoDto findByNome(
            @PathVariable String nome) {

        return campoService.findByNome(nome);

    }



    @GetMapping("/tipologia/{tipologia}")
    @Operation(
            summary = "Trova campi per tipologia",
            description = "Restituisce tutti i campi di una determinata tipologia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista campi recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Tipologia non valida"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<CampoDto> findByTipologia(
            @PathVariable String tipologia) {

        return campoService.findByTipologia(tipologia);

    }


    @GetMapping("/coperto/{coperto}")
    @Operation(
            summary = "Trova campi coperti o scoperti",
            description = "Restituisce i campi filtrati per copertura"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista campi recuperata correttamente"),
            @ApiResponse(responseCode = "400", description = "Parametro non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<CampoDto> findByCoperto(
            @PathVariable Boolean coperto) {

        return campoService.findByCoperto(coperto);

    }






    @GetMapping("/tipologia-coperto/{tipologia}/{coperto}")
    @Operation(
            summary = "Trova campo per tipologia e copertura",
            description = "Restituisce i campi compatibili con tipologia e copertura indicate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ricerca completata correttamente"),
            @ApiResponse(responseCode = "400", description = "Parametri non validi"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    public List<CampoDto> findByTipologiaAndCoperto(
            @PathVariable String tipologia,
            @PathVariable Boolean coperto) {


        return campoService.findByTipologiaAndCoperto(
                tipologia,
                coperto
        );

    }



    @GetMapping("/esiste/{nome}")
    @Operation(
            summary = "Verifica esistenza campo",
            description = "Controlla se esiste già un campo con il nome indicato"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Controllo completato"),
            @ApiResponse(responseCode = "400", description = "Nome non valido"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })


    public boolean existsByNome(
            @PathVariable String nome) {

        return campoService.existsByNome(nome);

    }

}