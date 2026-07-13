package it.model;

import it.enumerated.GiorniEnum;
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
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String sport;
    private String livello;
    @Enumerated(EnumType.STRING)
    private GiorniEnum giorni;
    private LocalDateTime oraInizio;
    private LocalDateTime oraFine;
    private Double prezzo;

    @ManyToOne
    @JoinColumn(name="campo_id")
    private Campo campo;

}
