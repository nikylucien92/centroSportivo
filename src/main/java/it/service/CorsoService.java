package it.service;

import it.dto.CorsoDto;
import it.enumerated.GiorniEnum;
import it.mapper.CampoMapper;
import it.mapper.Converter;
import it.mapper.CorsoMapper;
import it.model.Corso;
import it.repository.CampoRepository;
import it.repository.CorsoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
public class CorsoService extends AbstractService<Corso, CorsoDto> {
    private final CorsoMapper corsoMapper;
    private final CorsoRepository corsoRepository;
    protected CorsoService(JpaRepository<Corso, Integer> repository, Converter<Corso, CorsoDto> converter, CorsoMapper corsoMapper, CorsoRepository corsoRepository) {
        super(repository, converter);
        this.corsoMapper = corsoMapper;
        this.corsoRepository = corsoRepository;
    }

    public List<CorsoDto> trovaPerSport(String sport){
        List<Corso> spors = corsoRepository.findBySportIgnoreCase(sport);
        return corsoMapper.toDTOList(spors);
    }

    public List<CorsoDto> trovaPerGiorno(GiorniEnum giorno){
        List<Corso> byGiorni = corsoRepository.findByGiorni(giorno);
        return corsoMapper.toDTOList(byGiorni);
    }

    public List<CorsoDto> trovaDaOra(LocalTime ora){
        List<Corso> oraInizio = corsoRepository.findByOraInizioGreaterThanEqual(ora);
        return corsoMapper.toDTOList(oraInizio);
    }

    public List<CorsoDto> ottieniCorsiPerCampo(Integer campoId) {
        List<Corso> corsi = corsoRepository.findByCampoId(campoId);
        return corsoMapper.toDTOList(corsi);
    }
}
