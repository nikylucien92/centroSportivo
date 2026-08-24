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
    @Column(name="id_abbonamento")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="tipo_abbonamento")
    private AbbonamentoTypeEnum tipo;

    @Column(name="data_inizio")
    private LocalDate dataInizio;

    @Column(name="data_fine")
    private LocalDate dataFine;

    @Column(name="prezzo")
    private BigDecimal prezzo;

    @Enumerated(EnumType.STRING)
    @Column(name="stato")
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

