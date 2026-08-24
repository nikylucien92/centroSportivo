package it.mapper;

import it.dto.PagamentoDto;
import it.dto.UtenteDto;
import it.model.Pagamento;
import it.model.Utente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper extends AbstractConverter<Pagamento, PagamentoDto> {
    final private ModelMapper mapper=new ModelMapper();
    @Override
    public Pagamento toEntity(PagamentoDto dto) {
        return mapper.map(dto, Pagamento.class);
    }

    @Override
    public PagamentoDto toDTO(Pagamento entity) {
        return mapper.map(entity, PagamentoDto.class);
    }
}
