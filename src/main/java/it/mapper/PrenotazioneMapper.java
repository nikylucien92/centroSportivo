package it.mapper;

import it.dto.PrenotazioneDto;
import it.dto.UtenteDto;
import it.model.Prenotazione;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PrenotazioneMapper extends AbstractConverter<Prenotazione, PrenotazioneDto>{
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Prenotazione toEntity(PrenotazioneDto dto) {
        return mapper.map(dto, Prenotazione.class);
    }

    @Override
    public  PrenotazioneDto toDTO(Prenotazione entity) {
        return mapper.map(entity, PrenotazioneDto.class);
    }

    public Page<PrenotazioneDto> toDTOPage(Page<Prenotazione> prenotazioni) {
        return prenotazioni.map(this::toDTO);
    }
}
