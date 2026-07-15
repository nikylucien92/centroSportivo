package it.model;


import it.enumerated.PagamentoTypeEnum;
import it.enumerated.PagamentoStatoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "sportivo")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double prezzo;
    @Enumerated(EnumType.STRING)
    private PagamentoTypeEnum metodo;
    @Enumerated(EnumType.STRING)
    private PagamentoStatoEnum stato;
    private LocalDateTime dataPagamento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "prenotazione_id")
    private Prenotazione prenotazione;
}
