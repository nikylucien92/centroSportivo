package it.service;

import it.dto.AbbonamentoDto;
import it.mapper.AbbonamentoMapper;
import it.mapper.Converter;
import it.model.Abbonamento;
import it.repository.AbbonamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class AbbonamentoService extends AbstractService<Abbonamento, AbbonamentoDto> {
    private final AbbonamentoMapper abbonamentoMapper;
    private final AbbonamentoRepository abbonamentoRepository;
    protected AbbonamentoService(JpaRepository<Abbonamento, Integer> repository,
                                 Converter<Abbonamento, AbbonamentoDto> converter, AbbonamentoMapper abbonamentoMapper, AbbonamentoRepository abbonamentoRepository) {
        super(repository, converter);
        this.abbonamentoMapper = abbonamentoMapper;
        this.abbonamentoRepository = abbonamentoRepository;
    }

}
