package it.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "sportivo")
public class DisponibilitaCampo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String stato_disponibilita;
    private LocalDateTime data;
    private LocalDateTime ora_inizio;
    private LocalDateTime ora_fine;

    @OneToOne(mappedBy = "disponibilitaCampo")
    private Prenotazione prenotazioneCampo;

    @ManyToOne
    @JoinColumn(name="campo_id")
    private Campo campo;

}
