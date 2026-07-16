package it.dto;

import it.enumerated.AbbonamentoTypeEnum;
import it.model.Utente;
import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AbbonamentoDto {

    private Integer id;

    private AbbonamentoTypeEnum tipo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double prezzo;
    private String stato;

    private UtenteDto utente;
}
