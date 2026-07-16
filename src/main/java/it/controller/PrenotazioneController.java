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
public class PrenotazioneController extends AbstractController<PrenotazioneDto> {
    private final PrenotazioneService prenotazioneService;

    @PostMapping
    public ResponseEntity<PrenotazioneDto> createPrenotazione(@RequestBody PrenotazioneDto prenotazioneDto,
                                                              @RequestParam Integer utenteId) throws Exception {
        PrenotazioneDto createdPrenotazione = prenotazioneService.effetuaPrenotazione(prenotazioneDto, utenteId);
        return ResponseEntity.ok(createdPrenotazione);
    }

    @DeleteMapping("/{idUtente}")
    public ResponseEntity<List<PrenotazioneDto>> cancellaPrenotazione(@PathVariable Integer idUtente, @RequestParam Integer idPrenotazione) throws Exception {
        List<PrenotazioneDto> prenotazioneCancellata = prenotazioneService.cancellaPrenotazione(idUtente, idPrenotazione);
        return ResponseEntity.ok(prenotazioneCancellata);
    }

    // Todo: aggiungere endpoint per recuperare le prenotazioni di un utente
    // Todo: aggiungere endpoint per cambiare lo stato della prenotazione (es. da "in attesa" a "confermata" oppure "annullata")
    // Todo: cambiare nome all'entity Corso
    // Todo: aggiungere metodo per trovare le prenotazione in base alla data
    // Todo:

    @GetMapping("/{utenteId}/spesa-totale")
    public ResponseEntity<Double> spesaTotale(@PathVariable(name = "utenteId") Integer id) {
        return ResponseEntity.ok(prenotazioneService.calcolaSpesaTotale(id));
    }


}
