
package it.controller;

//import it.enumerated.AbbonamentoTypeEnum;
//import it.enumerated.GiorniEnum;
//import it.enumerated.PagamentoStatoEnum;
//import it.enumerated.PagamentoTypeEnum;
import it.model.*;
import it.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
//import java.math.BigDecimal;
//import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.LocalTime;
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

		creaAdmin();
		creaUtenti();
		//creaCampi();
		//creaDisponibilita();
		//creaPrenotazioni();
		//creaPagamenti();
		//creaCorsi();
		//creaAbbonamenti();

		System.out.println("Database inizializzato correttamente, creazione solo di ADMIN");
	}


	// ADMIN


	private void creaAdmin() {

		String email = "pignatiello.nicol@gmail.it";

		if (utenteRepository.findByEmail(email).isPresent()) {
			return;
		}

		Utente admin = new Utente();

		admin.setNome("Nicola");
		admin.setCognome("Pignatone");
		admin.setEmail(email);

		admin.setPassword(
				passwordEncoder.encode("Admin123!")
		);

		admin.setTelefono("3331234567");

		admin.setDataRegistrazione(
				LocalDateTime.now()
		);

		admin.setRuolo(RuoloEnum.ADMIN);

		utenteRepository.save(admin);
		}

	private void creaUtenti() {

		creaUtente(
				"Mario",
				"Rossi",
				"mario.rossi@gmail.com",
				"3332345111"
		);

		creaUtente(
				"Luca",
				"Bianchi",
				"luca.bianchi@gmail.com",
				"3332231722"
		);

		creaUtente(
				"Francesco",
				"Verdi",
				"francesco.verdi@gmail.com",
				"32789098533"
		);
		creaUtente(
				"Giuseppe",
				"Esposito",
				"giuseppe.esposito@gmail.com",
				"3334562144"
		);

		creaUtente(
				"Andrea",
				"Romano",
				"andrea.romano@gmail.com",
				"3335589155"
		);
	}


	private Utente creaUtente(
			String nome,
			String cognome,
			String email,
			String telefono
	) {

		if (utenteRepository.findByEmail(email).isPresent()) {
			return utenteRepository.findByEmail(email).get();
		}

		Utente utente = new Utente();

		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);

		utente.setPassword(
				passwordEncoder.encode("Pass7777!")
		);

		utente.setTelefono(telefono);

		utente.setRuolo(RuoloEnum.USER);

		utente.setDataRegistrazione(
				LocalDateTime.now()
		);

		return utenteRepository.save(utente);
	}
}



/*
    // =========================================================
    // UTENTI
    // =========================================================




    // =========================================================
    // CAMPI
    // =========================================================

    private void creaCampi() {

        creaCampo(
                "Campo 1",
                "Padel",
                25.00
        );

        creaCampo(
                "Campo 2",
                "Tennis",
                20.00
        );

        creaCampo(
                "Campo 3",
                "Calcio a 5",
                50.00
        );

        creaCampo(
                "Campo 4",
                "Basket",
                35.00
        );
		creaCampo("Campo 5",
				"Calcio a 11",
				100.00);
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

        return campoRepository.save(c);
    }


    // =========================================================
    // DISPONIBILITA
    // =========================================================

    private void creaDisponibilita() {

        Campo padel = campoRepository.findByNome("Campo 1");

        Campo tennis = campoRepository .findByNome("Campo 2");

        Campo calcetto = campoRepository.findByNome("Campo 3");

		Campo basket=campoRepository.findByNome("Campo 4");

	    Campo calcio=campoRepository.findByNome("Campo 5");

	    creaDisponibilita(
                padel,
                LocalDate.now()
                        .plusDays(1)
                        .atTime(18, 0)
        );

        creaDisponibilita(
                tennis,
                LocalDate.now()
                        .plusDays(2)
                        .atTime(17, 0)
        );

        creaDisponibilita(
                calcetto,
                LocalDate.now()
                        .plusDays(3)
                        .atTime(20, 0)
        );
        creaDisponibilita(
                basket,
                LocalDate.now()
                        .plusDays(3)
                        .atTime(19, 0)
        );
		creaDisponibilita(
				calcio,
				LocalDate.now()
						.plusDays(4)
						.atTime(19, 0));
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

        return disponibilitaRepository.save(d);
    }


    // =========================================================
    // PRENOTAZIONI
    // =========================================================

    private void creaPrenotazioni() {

        Utente mario = utenteRepository
                .findByEmail("mario.rossi@gmail.com")
                .orElseThrow();

        Utente luca = utenteRepository
                .findByEmail("luca.bianchi@gmail.com")
                .orElseThrow();

        Utente francesco = utenteRepository
                .findByEmail("francesco.verdi@gmail.com")
                .orElseThrow();

		Utente peppino=utenteRepository
				.findByEmail("giuseppe.esposito@gmail.com")
				.orElseThrow();

        DisponibilitaCampo dispPadel =
                disponibilitaRepository.findAll()
                        .get(0);

        DisponibilitaCampo dispTennis =
                disponibilitaRepository.findAll()
                        .get(1);

        DisponibilitaCampo dispCalcetto =
                disponibilitaRepository.findAll()
                        .get(2);

		DisponibilitaCampo dispCalcio=
				disponibilitaRepository.findAll()
								.get(3);


        creaPrenotazione(
                mario,
                dispPadel,
                4,
                40.00
        );

        creaPrenotazione(
                luca,
                dispTennis,
                2,
                30.00
        );

        creaPrenotazione(
                francesco,
                dispCalcetto,
                10,
                50.00
        );
		creaPrenotazione(
				francesco,
				dispCalcio,
				22,
				160.00
		);
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

        return prenotazioneRepository.save(p);
    }


    // =========================================================
    // PAGAMENTI
    // =========================================================

    private void creaPagamenti() {

        Utente mario = utenteRepository
                .findByEmail("mario.rossi@gmail.com")
                .orElseThrow();

        Utente luca = utenteRepository
                .findByEmail("luca.bianchi@gmail.com")
                .orElseThrow();

        Utente francesco = utenteRepository
                .findByEmail("francesco.verdi@gmail.com")
                .orElseThrow();


        Prenotazione pren1 =
                prenotazioneRepository.findAll()
                        .get(0);

        Prenotazione pren2 =
                prenotazioneRepository.findAll()
                        .get(1);

        Prenotazione pren3 =
                prenotazioneRepository.findAll()
                        .get(2);


        creaPagamento(
                mario,
                pren1,
                40.00,
                PagamentoTypeEnum.CARTA
        );

        creaPagamento(
                luca,
                pren2,
                30.00,
                PagamentoTypeEnum.PAYPAL
        );

        creaPagamento(
                francesco,
                pren3,
                50.00,
                PagamentoTypeEnum.CONTANTI
        );
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

        return pagamentoRepository.save(p);
    }


    // =========================================================
    // CORSI
    // =========================================================

    private void creaCorsi() {

        Campo padel = campoRepository
                .findByNome("Campo 1");


        Campo tennis = campoRepository
                .findByNome("Campo 2");



        creaCorso(
                "Corso Base",
                "Padel",
                padel
        );

        creaCorso(
                "Corso Avanzato",
                "Tennis",
                tennis
        );
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
                LocalTime.of(18, 0)
        );

        c.setOraFine(
                LocalTime.of(19, 30)
        );

        c.setPrezzo(80.00);
        c.setCampo(campo);

        return corsoRepository.save(c);
    }


    // =========================================================
    // ABBONAMENTI
    // =========================================================

    private void creaAbbonamenti() {

        Utente mario = utenteRepository
                .findByEmail("mario.rossi@gmail.com")
                .orElseThrow();

        Utente luca = utenteRepository
                .findByEmail("luca.bianchi@gmail.com")
                .orElseThrow();

        Utente francesco = utenteRepository
                .findByEmail("francesco.verdi@gmail.com")
                .orElseThrow();

        Utente giuseppe = utenteRepository
                .findByEmail("giuseppe.esposito@gmail.com")
                .orElseThrow();

        Utente andrea = utenteRepository
                .findByEmail("andrea.romano@gmail.com")
                .orElseThrow();


        creaAbbonamento(
                mario,
                AbbonamentoTypeEnum.MENSILE,
                BigDecimal.valueOf(50)
        );

        creaAbbonamento(
                luca,
                AbbonamentoTypeEnum.TRIMESTRALE,
                BigDecimal.valueOf(120)
        );

        creaAbbonamento(
                francesco,
                AbbonamentoTypeEnum.SEMESTRALE,
                BigDecimal.valueOf(220)
        );

        creaAbbonamentoScaduto(
                giuseppe,
                AbbonamentoTypeEnum.MENSILE,
                BigDecimal.valueOf(50)
        );

        creaAbbonamento(
                andrea,
                AbbonamentoTypeEnum.ANNUALE,
                BigDecimal.valueOf(400)
        );
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

        return abbonamentoRepository.save(a);
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
                LocalDate.now().minusMonths(2)
        );

        a.setDataFine(
                LocalDate.now().minusMonths(1)
        );

        a.setStato(
                StatoAbbonamentoEnum.SCADUTO
        );

        return abbonamentoRepository.save(a);
    }
*/

