package it.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CampoDto {

    private Integer id;
    private String nome;
    private String tipologiaCampo;
    private Double prezzoCampo;
    private Boolean coperto;
    private List<DisponibilitaCampoDto> listaDisponibilitaDto;
    private List<CorsoDto> listaCorsiDto;
}
