package it.model;

import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sportivo")
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AbbonamentoTypeEnum tipo;

    private LocalDate dataInizio;
    private LocalDate dataFine;
    private BigDecimal prezzo;

    @Enumerated(EnumType.STRING)
    private StatoAbbonamentoEnum stato;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public void crea() {

        if(tipo == null){
            throw new IllegalStateException(
                    "Tipo abbonamento obbligatorio"
            );
        }

        this.dataInizio = LocalDate.now()
                .plusDays(1);


        switch (tipo) {

            case MENSILE:
                this.dataFine = dataInizio.plusMonths(1);
                break;


            case TRIMESTRALE:
                this.dataFine = dataInizio.plusMonths(3);
                break;


            case SEMESTRALE:
                this.dataFine = dataInizio.plusMonths(6);
                break;


            case ANNUALE:
                this.dataFine = dataInizio.plusYears(1);
                break;
        }


        this.stato = StatoAbbonamentoEnum.ATTIVO;
    }

    public void rinnova() {


        switch (tipo) {

            case MENSILE:
                this.dataFine = dataFine.plusMonths(1);
                break;


            case TRIMESTRALE:
                this.dataFine = dataFine.plusMonths(3);
                break;


            case SEMESTRALE:
                this.dataFine = dataFine.plusMonths(6);
                break;


            case ANNUALE:
                this.dataFine = dataFine.plusYears(1);
                break;
        }


        this.stato = StatoAbbonamentoEnum.ATTIVO;
    }
}

