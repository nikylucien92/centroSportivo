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
public class Prenotazione {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataPrenotazione;
    private Integer numeroGiocatori;
    private Double costoTotale;
    private String statoPrenotazione;

    @JoinColumn(name="disponibilita_id")
    @OneToOne
    private DisponibilitaCampo disponibilitaCampo;

    @JoinColumn(name="utente_id_creato")
    @ManyToOne
    private Utente utenteCreato;
}
