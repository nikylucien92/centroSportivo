package it.repository;

import it.enumerated.PagamentoStatoEnum;
import it.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento,Integer> {

    List<Pagamento> findByUtenteId(Integer id);

    List<Pagamento> findByStato(PagamentoStatoEnum stato);

    List<Pagamento> findByDataPagamentoBetween(
            LocalDateTime inizio,
            LocalDateTime fine
    );
}
