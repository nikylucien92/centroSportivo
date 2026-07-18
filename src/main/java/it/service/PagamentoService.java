package it.service;

import it.dto.PagamentoDto;
import it.enumerated.PagamentoStatoEnum;
import it.mapper.Converter;
import it.mapper.PagamentoMapper;
import it.model.Pagamento;
import it.repository.PagamentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PagamentoService extends AbstractService<Pagamento, PagamentoDto> {
    private final PagamentoMapper pagamentoMapper;
    private final PagamentoRepository pagamentoRepository;
    protected PagamentoService(JpaRepository<Pagamento, Integer> repository, Converter<Pagamento, PagamentoDto> converter, PagamentoMapper pagamentoMapper, PagamentoRepository pagamentoRepository) {
        super(repository, converter);
        this.pagamentoMapper = pagamentoMapper;
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<PagamentoDto> getPagamentoPerUtenteId(Integer utenteId){
        return pagamentoMapper.toDTOList(
                pagamentoRepository.findByUtenteId(utenteId)
        );
    }

    public List<PagamentoDto> getPagamentiPerStato(PagamentoStatoEnum stato){
        return pagamentoMapper.toDTOList(
                pagamentoRepository.findByStato(stato)
        );
    }

    public List<PagamentoDto> getPagamentiPerGiorno(LocalDate giorno) {

        LocalDateTime inizio = giorno.atStartOfDay();
        LocalDateTime fine = giorno.atTime(LocalTime.MAX);

        List<Pagamento> pagamenti =
                pagamentoRepository.findByDataPagamentoBetween(inizio, fine);

        return pagamentoMapper.toDTOList(pagamenti);
    }


    public List<PagamentoDto> getPagamenti(
            Integer userId,
            PagamentoStatoEnum stato,
            LocalDate giorno){

        List<Pagamento> pagamenti;

        if (userId !=null){
            pagamenti=pagamentoRepository.findByUtenteId(userId);
        }else if( stato !=null){
            pagamenti=pagamentoRepository.findByStato(stato);
        }else  if (giorno !=null){
            LocalDateTime inizio = giorno.atStartOfDay();
            LocalDateTime fine = giorno.atTime(LocalTime.MAX);

            pagamenti = pagamentoRepository
                    .findByDataPagamentoBetween(inizio, fine);
        }else {
          pagamenti=pagamentoRepository.findAll();
        }
        return pagamentoMapper.toDTOList(pagamenti);
    }


}
