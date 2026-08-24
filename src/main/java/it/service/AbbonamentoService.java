package it.service;

import it.dto.AbbonamentoDto;
import it.mapper.AbbonamentoMapper;
import it.mapper.Converter;
import it.model.Abbonamento;
import it.repository.AbbonamentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.enumerated.AbbonamentoTypeEnum;
import it.enumerated.StatoAbbonamentoEnum;

import java.time.LocalDate;
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

    // Cerca abbonamenti per tipo
    public List<AbbonamentoDto> findByTipo(
            AbbonamentoTypeEnum tipo) {


        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByTipo(tipo)
        );

    }





    // Cerca abbonamenti per stato
    public List<AbbonamentoDto> findByStato(
            StatoAbbonamentoEnum stato) {


        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByStato(stato)
        );

    }





    // Cerca abbonamento di un utente
    public AbbonamentoDto findByUtenteId(
            Integer idUtente) {


        Abbonamento abbonamento =
                abbonamentoRepository.findByUtenteId(idUtente);


        return abbonamentoMapper.toDTO(abbonamento);

    }






    // Abbonamenti in scadenza in un periodo
    public List<AbbonamentoDto> findInScadenza(
            LocalDate inizio,
            LocalDate fine) {


        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByDataFineBetween(
                        inizio,
                        fine
                )
        );

    }





    // Abbonamenti scaduti
    public List<AbbonamentoDto> findScaduti() {


        return abbonamentoMapper.toDTOList(
                abbonamentoRepository.findByDataFineBefore(
                        LocalDate.now()
                )
        );

    }





    // Abbonamenti validi oggi
    public List<AbbonamentoDto> findValidiOggi() {


        LocalDate oggi = LocalDate.now();


        return abbonamentoMapper.toDTOList(
                abbonamentoRepository
                        .findByDataInizioLessThanEqualAndDataFineGreaterThanEqual(
                                oggi,
                                oggi
                        )
        );

    }





    // Creazione nuovo abbonamento
    public AbbonamentoDto creaAbbonamento(
            AbbonamentoDto dto) {


        Abbonamento esistente =
                abbonamentoRepository.findByUtenteId(
                        dto.getUtente().getId()
                );


        if(esistente != null){

            throw new RuntimeException(
                    "L'utente possiede già un abbonamento"
            );
        }



        Abbonamento abbonamento =
                abbonamentoMapper.toEntity(dto);


        abbonamento.crea();


        return abbonamentoMapper.toDTO(
                abbonamentoRepository.save(abbonamento)
        );
    }





    // Aggiornamento automatico stato scaduto
    public void aggiornaStatiScaduti() {


        List<Abbonamento> abbonamenti =
                abbonamentoRepository.findByDataFineBefore(
                        LocalDate.now()
                );


        for (Abbonamento abbonamento : abbonamenti) {


            if (StatoAbbonamentoEnum.ATTIVO
                    .equals(abbonamento.getStato())) {


                abbonamento.setStato(
                        StatoAbbonamentoEnum.SCADUTO
                );

            }
        }


        abbonamentoRepository.saveAll(abbonamenti);
    }

    // Rinnovo abbonamento
    public AbbonamentoDto rinnovaAbbonamento(
            Integer id) {


        Abbonamento abbonamento =
                abbonamentoRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Abbonamento non trovato"
                                )
                        );


        abbonamento.rinnova();


        return abbonamentoMapper.toDTO(
                abbonamentoRepository.save(abbonamento)
        );
    }

}
