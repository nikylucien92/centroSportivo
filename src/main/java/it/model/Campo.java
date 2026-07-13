package it.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(schema = "sportivo")
public class Campo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id_campo;
    private String nome;
    private String tipologia_campo;
    private Double prezzo_campo;
    private Boolean coperto;

    @OneToMany(mappedBy = "campo")
    private List<DisponibilitaCampo> listaDisponibilità=new ArrayList<>();


    @OneToMany(mappedBy = "campo")
    private List<Corso> listaCorsi=new ArrayList<>();
}
