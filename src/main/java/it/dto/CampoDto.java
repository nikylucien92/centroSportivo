package it.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<DisponibilitaCampoDto> listaDisponibilita;
    @JsonIgnore
    private List<CorsoDto> listaCorsi;
}
