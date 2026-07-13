package it.mapper;

import it.dto.AbbonamentoDto;
import it.dto.CampoDto;
import it.dto.UtenteDto;
import it.model.Abbonamento;
import it.model.Campo;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CampoMapper extends AbstractConverter<Campo, CampoDto>{
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Campo toEntity(CampoDto dto) {
        return mapper.map(dto, Campo.class);
    }

    @Override
    public CampoDto toDTO(Campo entity) {
        return mapper.map(entity, CampoDto.class);
    }
}
