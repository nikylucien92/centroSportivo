package it.dto;

import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AbbonamentoDto {

    private Integer id;

    private AbbonamentoTypeEnum tipo;

    private LocalDate dataInizio;

    private LocalDate dataFine;

    private BigDecimal prezzo;

    private StatoAbbonamentoEnum stato;

    private UtenteDto utente;
}
