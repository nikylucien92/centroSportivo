package it.dto;
import it.model.Prenotazione;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PrenotazioneDto {
    private Integer id;
    private LocalDateTime dataPrenotazione;
    private Integer numeroGiocatori;
    private Double costoTotale;
    private Double quotaPersona;
    private String statoPrenotazione;
    private DisponibilitaCampoDto disponibilitaCampo;
    private UtenteDto utenteCreato;

}
