
package it.security;


import it.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

	private final UtenteRepository utenteRepository;


	/**
	 * Spring Security utilizza questo metodo durante il login
	  e durante la validazione del JWT.
	 */

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {

		return utenteRepository
				.findByEmail(email)
				.orElseThrow(() ->
						new UsernameNotFoundException(
								"Utente non trovato con email: " + email
						)
				);
	}
}

