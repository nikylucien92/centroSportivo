package it.mapper;

import it.dto.AbbonamentoDto;
import it.dto.UtenteDto;
import it.model.Abbonamento;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AbbonamentoMapper extends AbstractConverter<Abbonamento, AbbonamentoDto > {
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Abbonamento toEntity(AbbonamentoDto dto) {
        return mapper.map(dto, Abbonamento.class);
    }

    @Override
    public AbbonamentoDto toDTO(Abbonamento entity) {
        return mapper.map(entity, AbbonamentoDto.class);
    }

}
