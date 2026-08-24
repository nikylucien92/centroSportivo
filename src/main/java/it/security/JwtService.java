
package it.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

	/*
	 * Chiave segreta utilizzata per firmare e verificare il JWT.
	 *
	 * application.properties:
	 *
	 * jwt.secret-key=una-chiave-di-almeno-32-caratteri
	 */
	@Value("${jwt.secret-key}")
	private String secretKey;

	/*
	 * Durata del token in millisecondi.
	 *
	 * Esempio:
	 * 3600000 = 1 ora
	 */
	@Value("${jwt.expiration}")
	private long jwtExpiration;


	/**
	 * GENERAZIONE DEL TOKEN
	 *
	 * Il subject del JWT sarà l'email dell'utente,
	 * perché Utente#getUsername() restituisce l'email.
	 */
	public String generateToken(UserDetails userDetails) {

		return Jwts.builder()

				// Identificativo dell'utente
				.subject(userDetails.getUsername())

				// Data di creazione del token
				.issuedAt(new Date())

				// Data di scadenza
				.expiration(
						new Date(
								System.currentTimeMillis() + jwtExpiration
						)
				)

				// Firma del token
				.signWith(getSigningKey())

				// Creazione del JWT
				.compact();
	}

	/**
	 * ESTRAE IL SUBJECT DAL TOKEN
	 *
	 * Nel nostro caso il subject è l'email.
	 */
	public String extractUsername(String token) {

		return extractClaim(
				token,
				Claims::getSubject
		);
	}

	/**
	 * ESTRAE LA DATA DI SCADENZA
	 */
	public Date extractExpiration(String token) {

		return extractClaim(
				token,
				Claims::getExpiration
		);
	}

	/**
	 * METODO GENERICO PER ESTRARRE UN CLAIM
	 */
	public <T> T extractClaim(
			String token,
			Function<Claims, T> claimsResolver
	) {

		Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);
	}


	/**
	 * ESTRAE TUTTI I CLAIM DEL TOKEN
	 *
	 * verifyWith():
	 * verifica la firma utilizzando la nostra SecretKey.
	 *
	 * parseSignedClaims():
	 * interpreta un JWT firmato.
	 */
	private Claims extractAllClaims(String token) {

		return Jwts.parser()

				.verifyWith(getSigningKey())

				.build()

				.parseSignedClaims(token)

				.getPayload();
	}


	/**
	 * CONTROLLA SE IL TOKEN È SCADUTO
	 */
	private boolean isTokenExpired(String token) {

		return extractExpiration(token)
				.before(new Date());
	}


	/**
	 * VALIDAZIONE COMPLETA DEL TOKEN
	 *
	 * Il token è valido se:
	 *
	 * 1. il JWT è formalmente valido
	 * 2. la firma è corretta
	 * 3. il subject corrisponde all'utente
	 * 4. il token non è scaduto
	 */
	public boolean isTokenValid(
			String token,
			UserDetails userDetails
	) {

		try {

			String username = extractUsername(token);

			return username != null
					&& username.equals(userDetails.getUsername())
					&& !isTokenExpired(token);

		} catch (JwtException | IllegalArgumentException ex) {

			return false;
		}
	}

	/**
	 * CREA LA CHIAVE CRITTOGRAFICA
	 *
	 * La chiave deve essere sufficientemente lunga
	 * per l'algoritmo HMAC utilizzato.
	 */
	private SecretKey getSigningKey() {

		return Keys.hmacShaKeyFor(
				secretKey.getBytes(StandardCharsets.UTF_8)
		);
	}
}
