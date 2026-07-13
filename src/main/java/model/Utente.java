package model;

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

@Table(name="utenti", schema="schema_dev")
public class Utente {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_utente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private LocalDateTime data_registrazione;
}
