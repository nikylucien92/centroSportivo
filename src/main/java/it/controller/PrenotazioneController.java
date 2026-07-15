package it.controller;

import it.dto.PrenotazioneDto;
import it.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prenotazione")
@CrossOrigin(origins = "http://localhost:4200")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    @PostMapping
    public ResponseEntity<PrenotazioneDto> createPrenotazione(@RequestBody PrenotazioneDto prenotazioneDto,
                                                              @RequestParam Integer utenteId) throws Exception {
        PrenotazioneDto createdPrenotazione = prenotazioneService.effetuaPrenotazione(prenotazioneDto, utenteId);
        return ResponseEntity.ok(createdPrenotazione);
    }

            @DeleteMapping("/{idUtente}")
            public ResponseEntity <List<PrenotazioneDto>> cancellaPrenotazione(@RequestParam @PathVariable Integer idUtente ,@RequestParam Integer idPrenotazione)throws Exception{
           List<PrenotazioneDto> prenotazioneCancellata= prenotazioneService.cancellaPrenotazione(idUtente,idPrenotazione);
                    return ResponseEntity.ok(prenotazioneCancellata);

            }



}
