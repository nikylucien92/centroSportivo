package it.controller;

import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.GiorniEnum;
import it.enumerated.PagamentoStatoEnum;
import it.enumerated.PagamentoTypeEnum;
import it.model.*;
import it.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import it.enumerated.*;
import it.model.*;
import it.repository.*;


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

    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {


        if (utenteRepository.count() > 0)
            return;
             creaAdmin();


        /*
         * ==========================
         * CREAZIONE UTENTI
         * ==========================
         */


        Utente mario = creaUtente(
                "Mario",
                "Rossi",
                "mario.rossi@gmail.com"
        );


        Utente luca = creaUtente(
                "Luca",
                "Bianchi",
                "luca.bianchi@gmail.com"
        );


        Utente francesco = creaUtente(
                "Francesco",
                "Verdi",
                "francesco.verdi@gmail.com"
        );


        Utente giuseppe = creaUtente(
                "Giuseppe",
                "Esposito",
                "giuseppe.esposito@gmail.com"
        );


        Utente andrea = creaUtente(
                "Andrea",
                "Romano",
                "andrea.romano@gmail.com"
        );



        utenteRepository.save(mario);
        utenteRepository.save(luca);
        utenteRepository.save(francesco);
        utenteRepository.save(giuseppe);
        utenteRepository.save(andrea);




        /*
         * ==========================
         * CREAZIONE CAMPI
         * ==========================
         */


        Campo padel = creaCampo(
                "Campo 1",
                "Padel",
                25.00
        );


        Campo tennis = creaCampo(
                "Campo 2",
                "Tennis",
                20.00
        );


        Campo calcetto = creaCampo(
                "Campo 3",
                "Calcio a 5",
                50.00
        );


        Campo basket = creaCampo(
                "Campo 4",
                "Basket",
                35.00
        );



        campoRepository.save(padel);
        campoRepository.save(tennis);
        campoRepository.save(calcetto);
        campoRepository.save(basket);





        /*
         * ==========================
         * DISPONIBILITA CAMPI
         * ==========================
         */


        DisponibilitaCampo dispPadel =
                creaDisponibilita(
                        padel,
                        LocalDate.now()
                                .plusDays(1)
                                .atTime(18,0)
                );


        DisponibilitaCampo dispTennis =
                creaDisponibilita(
                        tennis,
                        LocalDate.now()
                                .plusDays(2)
                                .atTime(17,0)
                );


        DisponibilitaCampo dispCalcetto =
                creaDisponibilita(
                        calcetto,
                        LocalDate.now()
                                .plusDays(3)
                                .atTime(20,0)
                );



        disponibilitaRepository.save(dispPadel);
        disponibilitaRepository.save(dispTennis);
        disponibilitaRepository.save(dispCalcetto);






        /*
         * ==========================
         * PRENOTAZIONI
         * ==========================
         */


        Prenotazione pren1 =
                creaPrenotazione(
                        mario,
                        dispPadel,
                        4,
                        40.00
                );


        Prenotazione pren2 =
                creaPrenotazione(
                        luca,
                        dispTennis,
                        2,
                        30.00
                );


        Prenotazione pren3 =
                creaPrenotazione(
                        francesco,
                        dispCalcetto,
                        10,
                        50.00
                );



        prenotazioneRepository.save(pren1);
        prenotazioneRepository.save(pren2);
        prenotazioneRepository.save(pren3);






        /*
         * ==========================
         * PAGAMENTI
         * ==========================
         */


        pagamentoRepository.save(
                creaPagamento(
                        mario,
                        pren1,
                        40.00,
                        PagamentoTypeEnum.CARTA
                )
        );


        pagamentoRepository.save(
                creaPagamento(
                        luca,
                        pren2,
                        30.00,
                        PagamentoTypeEnum.PAYPAL
                )
        );


        pagamentoRepository.save(
                creaPagamento(
                        francesco,
                        pren3,
                        50.00,
                        PagamentoTypeEnum.CONTANTI
                )
        );






        /*
         * ==========================
         * CORSI
         * ==========================
         */


        corsoRepository.save(
                creaCorso(
                        "Corso Base",
                        "Padel",
                        padel
                )
        );


        corsoRepository.save(
                creaCorso(
                        "Corso Avanzato",
                        "Tennis",
                        tennis
                )
        );







        /*
         * ==========================
         * ABBONAMENTI
         * ==========================
         */


        abbonamentoRepository.save(
                creaAbbonamento(
                        mario,
                        AbbonamentoTypeEnum.MENSILE,
                        BigDecimal.valueOf(50)
                )
        );


        abbonamentoRepository.save(
                creaAbbonamento(
                        luca,
                        AbbonamentoTypeEnum.TRIMESTRALE,
                        BigDecimal.valueOf(120)
                )
        );


        abbonamentoRepository.save(
                creaAbbonamento(
                        francesco,
                        AbbonamentoTypeEnum.SEMESTRALE,
                        BigDecimal.valueOf(220)
                )
        );


        abbonamentoRepository.save(
                creaAbbonamentoScaduto(
                        giuseppe,
                        AbbonamentoTypeEnum.MENSILE,
                        BigDecimal.valueOf(50)
                )
        );



        abbonamentoRepository.save(
                creaAbbonamento(
                        andrea,
                        AbbonamentoTypeEnum.ANNUALE,
                        BigDecimal.valueOf(400)
                )
        );




        System.out.println(
                "Database inizializzato correttamente"
        );

    }




    private void creaAdmin(){


        Utente admin = new Utente();

        admin.setNome("Nicola");
        admin.setCognome("Pignatiello");
        admin.setEmail("pignatiello.nicol@gmail.com");

        admin.setPassword(
                passwordEncoder.encode("Admin123!")
        );

        admin.setRuolo(RuoloEnum.ADMIN);


        utenteRepository.save(admin);

    }


    private Utente creaUtente(
            String nome,
            String cognome,
            String email) {


        Utente u = new Utente();

        u.setNome(nome);
        u.setCognome(cognome);
        u.setEmail(email);
        u.setPassword("password");
        u.setTelefono("3333333333");
        u.setDataRegistrazione(
                LocalDateTime.now()
        );

        return u;
    }






    private Campo creaCampo(
            String nome,
            String tipologia,
            Double prezzo) {


        Campo c = new Campo();

        c.setNome(nome);
        c.setTipologia(tipologia);
        c.setPrezzo(prezzo);
        c.setCoperto(true);

        return c;
    }






    private DisponibilitaCampo creaDisponibilita(
            Campo campo,
            LocalDateTime data) {


        DisponibilitaCampo d =
                new DisponibilitaCampo();


        d.setCampo(campo);
        d.setData(data);
        d.setOraInizio(data);
        d.setOraFine(data.plusHours(1));
        d.setStatoDisponibilita(
                "DISPONIBILE"
        );


        return d;
    }






    private Prenotazione creaPrenotazione(
            Utente utente,
            DisponibilitaCampo disponibilita,
            Integer giocatori,
            Double costo) {


        Prenotazione p =
                new Prenotazione();


        p.setUtenteCreato(utente);
        p.setDisponibilitaCampo(disponibilita);
        p.setNumeroGiocatori(giocatori);
        p.setCostoTotale(costo);
        p.setStatoPrenotazione(
                "CONFERMATA"
        );
        p.setDataPrenotazione(
                LocalDateTime.now()
        );

        return p;
    }






    private Pagamento creaPagamento(
            Utente utente,
            Prenotazione prenotazione,
            Double prezzo,
            PagamentoTypeEnum metodo) {


        Pagamento p =
                new Pagamento();


        p.setUtente(utente);
        p.setPrenotazione(prenotazione);
        p.setPrezzo(prezzo);
        p.setMetodo(metodo);
        p.setStato(
                PagamentoStatoEnum.COMPLETATO
        );
        p.setDataPagamento(
                LocalDateTime.now()
        );


        return p;
    }







    private Corso creaCorso(
            String nome,
            String sport,
            Campo campo) {


        Corso c =
                new Corso();


        c.setNome(nome);
        c.setSport(sport);
        c.setLivello("Base");
        c.setGiorni(
                GiorniEnum.LUNEDI
        );
        c.setOraInizio(
                LocalTime.of(18,0)
        );
        c.setOraFine(
                LocalTime.of(19,30)
        );
        c.setPrezzo(80.00);
        c.setCampo(campo);


        return c;
    }







    private Abbonamento creaAbbonamento(
            Utente utente,
            AbbonamentoTypeEnum tipo,
            BigDecimal prezzo) {


        Abbonamento a =
                new Abbonamento();


        a.setUtente(utente);
        a.setTipo(tipo);
        a.setPrezzo(prezzo);


        a.crea();


        return a;
    }


    private Abbonamento creaAbbonamentoScaduto(
            Utente utente,
            AbbonamentoTypeEnum tipo,
            BigDecimal prezzo) {


        Abbonamento a =
                new Abbonamento();


        a.setUtente(utente);
        a.setTipo(tipo);
        a.setPrezzo(prezzo);


        a.setDataInizio(
                LocalDate.now()
                        .minusMonths(2)
        );


        a.setDataFine(
                LocalDate.now()
                        .minusMonths(1)
        );


        a.setStato(
                StatoAbbonamentoEnum.SCADUTO
        );


        return a;
    }

}