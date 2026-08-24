
package it.model;

import it.enumerated.RuoloEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(
		name = "utente",
		schema = "sportivo"
)
public class Utente implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_utente")
	private Integer id;


	@Column(name = "nome")
	private String nome;


	@Column(name = "cognome")
	private String cognome;


	@Column(name = "email", nullable = false, unique = true)
	private String email;


	@Column(name = "password", nullable = false)
	private String password;


	@Column(name = "telefono")
	private String telefono;


	@Column(name = "data_registrazione")
	private LocalDateTime dataRegistrazione;


	@Column(name = "ruolo", nullable = false)
	@Enumerated(EnumType.STRING)
	private RuoloEnum ruolo = RuoloEnum.USER;


	@OneToMany(
			mappedBy = "utenteCreato",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Prenotazione> listaPrenotazioni =
			new ArrayList<>();


	/**
	 * RUOLI SPRING SECURITY
	 *
	 * Se ruolo = USER
	 * restituisce:
	 *
	 * ROLE_USER
	 *
	 * Se ruolo = ADMIN
	 * restituisce:
	 *
	 * ROLE_ADMIN
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(
				new SimpleGrantedAuthority(
						"ROLE_" + ruolo.name()
				)
		);
	}


	/**
	 * USERNAME DI SPRING SECURITY
	 *
	 * Nel nostro progetto l'username coincide con l'email.
	 */
	@Override
	public String getUsername() {

		return email;
	}


	/**
	 * PASSWORD UTILIZZATA DA SPRING SECURITY
	 */
	@Override
	public String getPassword() {

		return password;
	}
}

