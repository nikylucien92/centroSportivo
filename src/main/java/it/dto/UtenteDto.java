package it.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UtenteDto {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private LocalDateTime dataRegistrazione;
    private List<PrenotazioneDto> listaPrenotazioniDto;
}
