package it.security;

import it.enumerated.RuoloEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private String dataRegistrazione;

}
