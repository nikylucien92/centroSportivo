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
    @Column(name="id_pagamento")
    private Integer id;

    @Column(name="prezzo")
    private Double prezzo;

    @Enumerated(EnumType.STRING)
    @Column(name="metodo_pagamento")
    private PagamentoTypeEnum metodo;

    @Enumerated(EnumType.STRING)
    @Column(name="stato_pagamento")
    private PagamentoStatoEnum stato;

    @Column(name="data_pagamento")
    private LocalDateTime dataPagamento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    @OneToOne
    @JoinColumn(name = "prenotazione_id")
    private Prenotazione prenotazione;
}
