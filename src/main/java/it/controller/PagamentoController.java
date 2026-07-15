package it.controller;

import it.dto.PagamentoDto;
import it.enumerated.PagamentoStatoEnum;
import it.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pagamento")
@CrossOrigin(origins = "http://localhost:4200")
public class PagamentoController extends AbstractController<PagamentoDto>{

    private final PagamentoService pagamentoService;

    @GetMapping("/utende/{utenteId}")
    public ResponseEntity<List<PagamentoDto>> getPagamentoPerUtente(
            @PathVariable(name = "utenteId") Integer utenteId){
        return ResponseEntity.ok(pagamentoService.getPagamentoPerUtenteId(utenteId));
    }

    @GetMapping("/stato/{stato}")
    public ResponseEntity<List<PagamentoDto>> getPagamentiPerStato(
            @PathVariable(name = "stato")PagamentoStatoEnum stato){
        return ResponseEntity.ok(pagamentoService.getPagamentiPerStato(stato));
    }

    @GetMapping("/giorno/{giorno}")
    public ResponseEntity<List<PagamentoDto>> getPagamentiPerGiorno(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate giorno) {
        return ResponseEntity.ok(
                pagamentoService.getPagamentiPerGiorno(giorno)
        );
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDto>> getPagamenti(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) PagamentoStatoEnum stato,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yy-MM-dd") LocalDate giorno){
        return ResponseEntity.ok(
                pagamentoService.getPagamenti(
                        userId,stato,giorno)
        );
    }




}
