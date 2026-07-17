package it.service;

import it.dto.CampoDto;
import it.dto.DisponibilitaCampoDto;
import it.mapper.Converter;
import it.mapper.CorsoMapper;
import it.mapper.DisponibilitaCampoMapper;
import it.model.Campo;
import it.model.DisponibilitaCampo;
import it.repository.CampoRepository;
import it.repository.CorsoRepository;
import it.repository.DisponibilitaCampoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DisponibilitaCampoService extends  AbstractService<DisponibilitaCampo, DisponibilitaCampoDto> {
    private final DisponibilitaCampoMapper disponibilitaCampoMapper;
    private final DisponibilitaCampoRepository disponibilitaCampoRepository;
    private final CampoRepository campoRepository;
    protected DisponibilitaCampoService(JpaRepository<DisponibilitaCampo, Integer> repository, Converter<DisponibilitaCampo,
            DisponibilitaCampoDto> converter, DisponibilitaCampoMapper disponibilitaCampoMapper, DisponibilitaCampoRepository disponibilitaCampoRepository ,CampoRepository campoRepository) {
        super(repository, converter);
        this.disponibilitaCampoMapper = disponibilitaCampoMapper;
        this.disponibilitaCampoRepository = disponibilitaCampoRepository;
        this.campoRepository=campoRepository;
    }

    public List<DisponibilitaCampoDto> getAllDisponibilita() {

        List<DisponibilitaCampo> lista = disponibilitaCampoRepository.findAll();

        return lista.stream()
                .map(disponibilitaCampoMapper::toDTO)
                .toList();
    }

    public List<DisponibilitaCampoDto> getDisponibilitaByData(LocalDate data) throws Exception {

        List<DisponibilitaCampo> lista = disponibilitaCampoRepository.findByData(data);

        if (lista.isEmpty()) {
            throw new Exception("Nessuna disponibilità trovata per la data " + data);
        }

        return lista.stream()
                .map(disponibilitaCampoMapper::toDTO)
                .toList();
    }


    public List<DisponibilitaCampoDto> getDisponibilitaByCampo(Integer idCampo) throws Exception {

        Campo campo = campoRepository.findById(idCampo)
                .orElseThrow(() -> new Exception("Campo non trovato"));

        List<DisponibilitaCampo> lista = disponibilitaCampoRepository.findByCampoId(idCampo);

        return lista.stream()
                .map(disponibilitaCampoMapper::toDTO)
                .toList();
    }

}
