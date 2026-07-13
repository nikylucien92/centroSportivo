package it.mapper;

import it.dto.DisponibilitaCampoDto;
import it.dto.UtenteDto;
import it.model.DisponibilitaCampo;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DisponibilitaCampoMapper extends AbstractConverter<DisponibilitaCampo, DisponibilitaCampoDto>{
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public DisponibilitaCampo toEntity(DisponibilitaCampoDto dto) {
        return mapper.map(dto, DisponibilitaCampo.class);
    }

    @Override
    public DisponibilitaCampoDto toDTO(DisponibilitaCampo entity) {
        return mapper.map(entity, DisponibilitaCampoDto.class);
    }
}
