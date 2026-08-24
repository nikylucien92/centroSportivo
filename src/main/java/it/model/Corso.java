package it.model;

import it.enumerated.GiorniEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sportivo" ,name = "corso")
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_corso")
    private Integer id;

    @Column(name="nome")
    private String nome;

    @Column(name="sport")
    private String sport;

    @Column(name="livello")
    private String livello;

    @Enumerated(EnumType.STRING)
    @Column(name="giorno_corso")
    private GiorniEnum giorni;

    @Column(name="ora_inizio")
    private LocalTime oraInizio;

    @Column(name="ora_fine")
    private LocalTime oraFine;

    @Column(name="prezzo")
    private Double prezzo;

    @ManyToOne
    @JoinColumn(name="campo_id")
    private Campo campo;

}
