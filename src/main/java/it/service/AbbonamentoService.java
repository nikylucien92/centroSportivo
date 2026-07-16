package it.service;

import it.dto.AbbonamentoDto;
import it.mapper.AbbonamentoMapper;
import it.mapper.Converter;
import it.model.Abbonamento;
import it.repository.AbbonamentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.enumerated.AbbonamentoTypeEnum;
import java.util.List;


@Service
public class AbbonamentoService
        extends AbstractService<Abbonamento, AbbonamentoDto> {


    private final AbbonamentoMapper abbonamentoMapper;

    private final AbbonamentoRepository abbonamentoRepository;



    public AbbonamentoService(
            JpaRepository<Abbonamento, Integer> repository,
            Converter<Abbonamento, AbbonamentoDto> converter,
            AbbonamentoMapper abbonamentoMapper,
            AbbonamentoRepository abbonamentoRepository) {


        super(repository, converter);

        this.abbonamentoMapper = abbonamentoMapper;
        this.abbonamentoRepository = abbonamentoRepository;

    }




    public List<AbbonamentoDto> findByTipo(AbbonamentoTypeEnum tipo){

        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByTipo(tipo)
        );

    }



    public List<AbbonamentoDto> findByStato(String stato){

        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByStato(stato)
        );

    }



    public List<AbbonamentoDto> findByPrezzo(Double prezzo){

        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByPrezzo(prezzo)
        );

    }



    public List<AbbonamentoDto> cercaPerPrezzoMassimo(Double prezzo){

        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByPrezzoLessThanEqual(prezzo)
        );

    }

    public AbbonamentoDto findByUtenteId(Integer idUtente){

        return abbonamentoMapper.toDTO(
                abbonamentoRepository.findByUtenteId(idUtente)
        );

    }

}
