package it.security;

import it.model.Utente;
import it.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente=utenteRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(utente.getEmail())
                .password(utente.getPassword())
                .roles(String.valueOf(utente.getRuolo()))
                .build();
    }


}
