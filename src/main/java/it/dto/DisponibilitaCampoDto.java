package it.dto;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DisponibilitaCampoDto {
    private Integer id;
    private String statoDisponibilita;
    private LocalDateTime data;
    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;
    private PrenotazioneDto prenotazioneCampoDto;
    private CampoDto campoDto;
}
