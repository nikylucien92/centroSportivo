package it.dto;

import it.enumerated.PagamentoTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PagamentoDto {

    private Integer id;
    private Double prezzo;
    private PagamentoTypeEnum metodo;
    private String stato;
    private LocalDateTime dataPagamento;
}
