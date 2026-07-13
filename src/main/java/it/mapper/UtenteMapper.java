package it.mapper;

import it.dto.UtenteDto;
import it.model.Utente;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class UtenteMapper extends AbstractConverter<Utente, UtenteDto> {
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Utente toEntity(UtenteDto dto) {
        return mapper.map(dto, Utente.class);
    }

    @Override
    public UtenteDto toDTO(Utente entity) {
        return mapper.map(entity, UtenteDto.class);
    }
}
