package it.model;

import it.enumerated.RuoloEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="utente", schema="sportivo")
public class Utente {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private LocalDateTime dataRegistrazione;
    @Enumerated(EnumType.STRING)
    private RuoloEnum ruolo=RuoloEnum.USER;

    @OneToMany (mappedBy = "utenteCreato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> listaPrenotazioni=new ArrayList<>();
}
//admin solo delete  user per sicurezzer
//