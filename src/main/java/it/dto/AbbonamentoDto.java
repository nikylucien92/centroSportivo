package it.dto;

import it.enumerated.AbbonamentoTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbbonamentoDto {

    private Integer id;
    //    private Utente utenteId;
//    private Corso corsoId;
    private AbbonamentoTypeEnum tipo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double prezzo;
    private String stato;
}
