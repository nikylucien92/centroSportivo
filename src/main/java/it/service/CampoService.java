package it.service;

import it.dto.AbbonamentoDto;
import it.dto.CampoDto;
import it.dto.UtenteDto;
import it.mapper.AbbonamentoMapper;
import it.mapper.CampoMapper;
import it.mapper.Converter;
import it.model.Abbonamento;
import it.model.Campo;
import it.repository.AbbonamentoRepository;
import it.repository.CampoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CampoService extends AbstractService<Campo, CampoDto> {
    private final CampoMapper campomapper;
    private final CampoRepository campoRepository;
    protected CampoService(JpaRepository<Campo, Integer> repository,
                           Converter<Campo, CampoDto> converter, CampoMapper campomapper, CampoRepository campoRepository) {
        super(repository, converter);
        this.campomapper = campomapper;
        this.campoRepository = campoRepository;
    }

}
