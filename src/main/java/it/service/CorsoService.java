package it.service;

import it.dto.CorsoDto;
import it.mapper.CampoMapper;
import it.mapper.Converter;
import it.mapper.CorsoMapper;
import it.model.Corso;
import it.repository.CampoRepository;
import it.repository.CorsoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CorsoService extends AbstractService<Corso, CorsoDto> {
    private final CorsoMapper corsoMapper;
    private final CorsoRepository corsoRepository;
    protected CorsoService(JpaRepository<Corso, Integer> repository, Converter<Corso, CorsoDto> converter, CorsoMapper corsoMapper, CorsoRepository corsoRepository) {
        super(repository, converter);
        this.corsoMapper = corsoMapper;
        this.corsoRepository = corsoRepository;
    }
}
