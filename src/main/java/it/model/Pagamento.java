package it.model;


import it.enumerated.PagamentoTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sportivo")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double prezzo;
    @Enumerated(EnumType.STRING)
    private PagamentoTypeEnum metodo;
    private String stato;
    private LocalDateTime dataPagamento;
//    private Utente utenteId;
//    private PrenotazioneId;
}
