package it.mapper;

import it.dto.CampoDto;
import it.dto.CorsoDto;
import it.dto.UtenteDto;
import it.model.Campo;
import it.model.Corso;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CorsoMapper extends AbstractConverter<Corso, CorsoDto>{
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Corso toEntity(CorsoDto dto) {
        return mapper.map(dto, Corso.class);
    }

    @Override
    public CorsoDto toDTO(Corso entity) {
        return mapper.map(entity, CorsoDto.class);
    }
}
