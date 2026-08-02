package it.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "sportivo")
public class DisponibilitaCampo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_disponibilita")
    private Integer id;

    @Column(name="stato_disponibilita")
    private String statoDisponibilita;

    @Column(name="data_disponibilita")
    private LocalDateTime data;

    @Column(name="ora_inizio")
    private LocalDateTime oraInizio;

    @Column(name="ora_fine")
    private LocalDateTime oraFine;

    @Column(name="disponibilita")
    private Boolean disponibilita;

    @OneToMany(mappedBy = "disponibilitaCampo")
    private List<Prenotazione> prenotazioniCampo;

    @ManyToOne
    @JoinColumn(name="campo_id")
    private Campo campo;

}
