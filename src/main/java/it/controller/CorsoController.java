package it.controller;

import it.dto.CorsoDto;
import it.enumerated.GiorniEnum;
import it.model.Corso;
import it.service.CorsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/corso")
@CrossOrigin(origins = "http://localhost:4200")
public class CorsoController extends AbstractController<CorsoDto> {

    private final CorsoService corsoService;


    @GetMapping("/giorno/{giorno}")
    public ResponseEntity<List<CorsoDto>> getCorsoByGiorno(@PathVariable GiorniEnum giorno) {
        return ResponseEntity.ok(corsoService.trovaPerGiorno(giorno));
    }

    @GetMapping("/sport/{sport}")
    public ResponseEntity<List<CorsoDto>> getCorsiBySport(@PathVariable String sport) {
        return ResponseEntity.ok(corsoService.trovaPerSport(sport));
    }

    @GetMapping("/da-ora")
    public ResponseEntity<List<CorsoDto>> getCorsiDaOraInPoi(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalTime ora) {
        return ResponseEntity.ok(corsoService.trovaDaOra(ora));
    }

    @GetMapping("/campo/{campoId}/corsi")
    public ResponseEntity<List<CorsoDto>> getCorsiByCampo(@PathVariable Integer campoId) {
        List<CorsoDto> corsi = corsoService.ottieniCorsiPerCampo(campoId);
        return ResponseEntity.ok(corsi);
    }

}
