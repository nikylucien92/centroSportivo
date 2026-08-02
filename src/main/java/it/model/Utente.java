package it.model;

import it.enumerated.RuoloEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name="utente", schema="sportivo")
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "data_registrazione")
    private LocalDateTime dataRegistrazione;

    @Column(name = "ruolo")
    @Enumerated(EnumType.STRING)
    private RuoloEnum ruolo=RuoloEnum.USER;

    @OneToMany (mappedBy = "utenteCreato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> listaPrenotazioni=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((ruolo.name())));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public @Nullable String getPassword() {
        return password;
    }

}
