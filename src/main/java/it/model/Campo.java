package it.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Campo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_campo;
    private String nome;
    private String tipologia_campo;
    private Double prezzo_campo;
    private Boolean coperto;
    private String stato_campo;
}
