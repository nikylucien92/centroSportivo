package it.controller;

import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.GiorniEnum;
import it.enumerated.PagamentoStatoEnum;
import it.enumerated.PagamentoTypeEnum;
import it.model.*;
import it.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtenteRepository utenteRepository;
    private final CampoRepository campoRepository;
    private final DisponibilitaCampoRepository disponibilitaRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final PagamentoRepository pagamentoRepository;
    private final CorsoRepository corsoRepository;
    private final AbbonamentoRepository abbonamentoRepository;

    @Override
    public void run(String... args) {
        if (utenteRepository.count() > 0) return;

        for (int i = 1; i <= 5; i++) {
            // create user
            Utente u = creaUtente("Utente" + i, "Cognome" + i, "user" + i + "@example.com");
            utenteRepository.save(u);

            // create campo
            String tipo = (i % 2 == 0) ? "Tennis" : "Padel";
            Campo c = creaCampo("Campo " + i, tipo, 20.0 + i * 5);
            campoRepository.save(c);

            // create disponibilita (owned by DisponibilitaCampo.campo)
            LocalDateTime start = LocalDate.now().plusDays(i).atTime(17 + i, 0);
            DisponibilitaCampo d = creaDisponibilita(c, start);
            disponibilitaRepository.save(d);

            // create prenotazione (owns disponibilita/prenotazione one-to-one and references utente)
            Prenotazione p = creaPrenotazione(u, d);
            prenotazioneRepository.save(p);

            // create pagamento (model currently lacks FK fields to link to user/prenotazione)
            Pagamento pay = creaPagamento(u, p, 10.0 * i, PagamentoTypeEnum.CARTA);
            pagamentoRepository.save(pay);

            // create corso linked to campo
            Corso corso = creaCorso("Corso " + i, tipo, c);
            corsoRepository.save(corso);

            Abbonamento ab = creaAbbonamento(
                    u,
                    AbbonamentoTypeEnum.MENSILE,
                    LocalDateTime.now().minusDays(i),
                    LocalDateTime.now().plusMonths(1),
                    50.0 + i * 10,
                    "ATTIVO"
            );
            abbonamentoRepository.save(ab);
        }

        System.out.println("Database popolato con 5 entità e relazioni.");
    }

    private Utente creaUtente(String nome, String cognome, String email) {
        Utente u = new Utente();
        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword("password");
        u.setTelefono("3331234567");
        u.setDataRegistrazione(LocalDateTime.now());
        return u;
    }

    private Campo creaCampo(String nome, String tipo, Double prezzo) {
        Campo c = new Campo();
        c.setNome(nome);
        c.setTipologia(tipo);
        c.setPrezzo(prezzo);
        c.setCoperto(true);
        return c;
    }

    private DisponibilitaCampo creaDisponibilita(Campo campo, LocalDateTime start) {
        DisponibilitaCampo d = new DisponibilitaCampo();
        d.setCampo(campo);
        d.setStatoDisponibilita("DISPONIBILE");
        d.setData(start);
        d.setOraInizio(start);
        d.setOraFine(start.plusHours(1));
        return d;
    }

    private Prenotazione creaPrenotazione(Utente u, DisponibilitaCampo d) {
        Prenotazione p = new Prenotazione();
        p.setUtenteCreato(u);
        p.setDisponibilitaCampo(d);
        p.setNumeroGiocatori(4);
        p.setCostoTotale(40.0);
        p.setStatoPrenotazione("CONFERMATA");
        p.setDataPrenotazione(LocalDateTime.now());
        return p;
    }

    private Pagamento creaPagamento(Utente u, Prenotazione prenotazione, Double prezzo, PagamentoTypeEnum metodo) {
        Pagamento p = new Pagamento();
        p.setUtente(u);
        p.setPrenotazione(prenotazione);
        p.setPrezzo(prezzo);
        p.setMetodo(metodo);
        p.setStato(PagamentoStatoEnum.COMPLETATO);
        p.setDataPagamento(LocalDateTime.now());
        return p;
    }

    private Corso creaCorso(String nome, String sport, Campo campo) {
        Corso c = new Corso();
        c.setNome(nome);
        c.setSport(sport);
        c.setLivello("Base");
        c.setGiorni(GiorniEnum.LUNEDI);
        c.setOraInizio(LocalTime.now().withHour(18).withMinute(0));
        c.setOraFine(LocalTime.now().withHour(19).withMinute(30));
        c.setPrezzo(80.0);
        c.setCampo(campo);
        return c;
    }

    private Abbonamento creaAbbonamento(Utente u, AbbonamentoTypeEnum tipo, LocalDateTime inizio, LocalDateTime fine, Double prezzo, String stato) {
        Abbonamento a = new Abbonamento();
        a.setUtente(u);
        a.setTipo(tipo);
        a.setDataInizio(inizio);
        a.setDataFine(fine);
        a.setPrezzo(prezzo);
        a.setStato(stato);
        return a;
    }
}
