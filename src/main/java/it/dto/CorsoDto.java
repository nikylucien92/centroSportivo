package it.dto;

import it.enumerated.GiorniEnum;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CorsoDto {

    private Integer id;
    private String nome;
    private String sport;
    private String livello;
    private GiorniEnum giorni;
    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;
    private Double prezzo;
    private CampoDto campo;
}
