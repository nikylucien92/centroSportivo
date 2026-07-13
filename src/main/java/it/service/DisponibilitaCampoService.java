package it.service;

import it.dto.CampoDto;
import it.dto.DisponibilitaCampoDto;
import it.mapper.Converter;
import it.mapper.CorsoMapper;
import it.mapper.DisponibilitaCampoMapper;
import it.model.Campo;
import it.model.DisponibilitaCampo;
import it.repository.CorsoRepository;
import it.repository.DisponibilitaCampoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DisponibilitaCampoService extends  AbstractService<DisponibilitaCampo, DisponibilitaCampoDto> {
    private final DisponibilitaCampoMapper disponibilitaCampoMapper;
    private final DisponibilitaCampoRepository disponibilitaCampoRepository;
    protected DisponibilitaCampoService(JpaRepository<DisponibilitaCampo, Integer> repository, Converter<DisponibilitaCampo, DisponibilitaCampoDto> converter, DisponibilitaCampoMapper disponibilitaCampoMapper, DisponibilitaCampoRepository disponibilitaCampoRepository) {
        super(repository, converter);
        this.disponibilitaCampoMapper = disponibilitaCampoMapper;
        this.disponibilitaCampoRepository = disponibilitaCampoRepository;
    }
}
