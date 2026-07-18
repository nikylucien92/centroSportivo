package it.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityFilter {

    @Bean
    @Profile("dev")
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        return  http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth->auth
                                //per vedere gli utenti o eliminarli devi essere ADMIN.
                                // blocato l'intero endpoint /user solo per gli ADMIN.
                                .requestMatchers("/user/**").hasRole("ADMIN")

                                // Chiunque può vedere i campi e i corsi disponibili
                                .requestMatchers(HttpMethod.GET, "/campo/**", "/corso/**").permitAll()

                                // Solo l'ADMIN può creare, modificare o eliminare corsi e campi della palestra
                                .requestMatchers("/campo/**", "/corso/**").hasRole("ADMIN")

                                // Uno USER può vedere gli abbonamenti e inviare un pagamento (POST)
                                .requestMatchers(HttpMethod.GET, "/abbonamento/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/pagamento/**").hasRole("USER")
                                // Solo l'ADMIN gestisce i pagamenti globali o crea/modifica i piani di abbonamento
                                .requestMatchers("/pagamento/**", "/abbonamento/**").hasRole("ADMIN")

                                // La lettura è libera per vedere le disponibilità
                                .requestMatchers(HttpMethod.GET, "/prenotazione/**").permitAll()
                                // user può creare la sua prenotazione
                                .requestMatchers(HttpMethod.POST, "/prenotazione/**").hasRole("USER")
                                // Solo l'ADMIN può modificare o eliminare le prenotazioni
                                .requestMatchers("/prenotazione/**").hasRole("ADMIN")

                                // Qualsiasi richiesta DELETE residua nel sistema è rigorosamente per ADMIN
                                .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                                // Qualsiasi altra cosa richiede l'autenticazione generica
                                .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults())
                .build();
    }


    // Questo filtro si attiva SOLO se il profilo "sic" NON è attivo (!sic)
    @Bean
    @Profile("!dev")
    public SecurityFilterChain securityFilterChainDisabled(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        // Questo dice a Spring di NON criptare e accettare le password in chiaro.
        return NoOpPasswordEncoder.getInstance();
    }
}
