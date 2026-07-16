package it.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CampoDto {

    private Integer id;
    private String nome;
    private String tipologia;
    private Double prezzo;
    private Boolean coperto;
    private List<DisponibilitaCampoDto> listaDisponibilita;
    private List<CorsoDto> listaCorsi;
}
