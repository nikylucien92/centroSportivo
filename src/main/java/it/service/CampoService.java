package it.service;


import it.dto.CampoDto;
import it.mapper.CampoMapper;
import it.mapper.Converter;
import it.model.Campo;
import it.repository.CampoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;


@Service
public class CampoService extends AbstractService<Campo, CampoDto> {


    private static final Logger log = LoggerFactory.getLogger(CampoService.class);


    private final CampoMapper campoMapper;

    private final CampoRepository campoRepository;


    public CampoService(
            JpaRepository<Campo, Integer> repository,
            Converter<Campo, CampoDto> converter,
            CampoMapper campoMapper,
            CampoRepository campoRepository) {

        super(repository, converter);
        this.campoMapper = campoMapper;
        this.campoRepository = campoRepository;
    }



    public CampoDto findByNome(String nome){

        return campoMapper.toDTO(
                campoRepository.findByNome(nome)
        );

    }



    public List<CampoDto> findByTipologia(String tipologia){

        return campoMapper.toDTOList(
                campoRepository.findByTipologia(tipologia)
        );

    }



    public List<CampoDto> findByCoperto(Boolean coperto){

        return campoMapper.toDTOList(
                campoRepository.findByCoperto(coperto)
        );

    }

    public List<CampoDto> findByTipologiaAndCoperto(
            String tipologia,
            Boolean coperto){

        return campoMapper.toDTOList(
                campoRepository.findByTipologiaAndCoperto(
                        tipologia,
                        coperto
                )
        );

    }



    public boolean existsByNome(String nome){

        return campoRepository.existsByNome(nome);

    }

}