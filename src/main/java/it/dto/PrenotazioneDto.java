package it.dto;
import it.model.Prenotazione;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private UtenteDto utenteCreato;

}
