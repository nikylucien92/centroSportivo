package it.model;

import it.enumerated.AbbonamentoTypeEnum;
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
public class Abbonamento {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//    private Utente utenteId;
//    private Corso corsoId;
    @Enumerated(EnumType.STRING)
    private AbbonamentoTypeEnum tipo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private Double prezzo;
    private String stato;

}

