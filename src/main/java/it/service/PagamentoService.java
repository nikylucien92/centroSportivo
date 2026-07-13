package it.service;

import it.dto.CorsoDto;
import it.dto.PagamentoDto;
import it.mapper.Converter;
import it.mapper.DisponibilitaCampoMapper;
import it.mapper.PagamentoMapper;
import it.model.Corso;
import it.model.Pagamento;
import it.repository.DisponibilitaCampoRepository;
import it.repository.PagamentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService extends AbstractService<Pagamento, PagamentoDto> {
    private final PagamentoMapper pagamentoMapper;
    private final PagamentoRepository pagamentoRepository;
    protected PagamentoService(JpaRepository<Pagamento, Integer> repository, Converter<Pagamento, PagamentoDto> converter, PagamentoMapper pagamentoMapper, PagamentoRepository pagamentoRepository) {
        super(repository, converter);
        this.pagamentoMapper = pagamentoMapper;
        this.pagamentoRepository = pagamentoRepository;
    }
}
