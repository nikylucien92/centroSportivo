package progetto.centroSportivo.centroSportivo.ServiceTest;

import it.dto.PagamentoDto;
import it.enumerated.PagamentoStatoEnum;
import it.mapper.PagamentoMapper;
import it.model.Pagamento;
import it.repository.PagamentoRepository;
import it.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    PagamentoRepository pagamentoRepository;
    @Mock
    PagamentoMapper mapperPagamento;

    @InjectMocks
    PagamentoService servicePagamento;

    @Test
    void getPagamentoUtenteId_shouldReturnListaPagamenti(){

        Integer utenteId=2;

        Pagamento pagamento=new Pagamento();
        pagamento.setId(10);
        PagamentoDto pagamentoDto=new PagamentoDto();
        pagamentoDto.setId(10);

        List<Pagamento>listPagamento=List.of(pagamento);
        List<PagamentoDto>listPagamentoDto=List.of(pagamentoDto);

        when(pagamentoRepository.findByUtenteId(utenteId))
                .thenReturn(listPagamento);

        //mappa listaPagamento a un  Dto
        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        // Act
        List<PagamentoDto> risultato =
                servicePagamento.getPagamentoPerUtenteId(utenteId);

        // Assert
        assertNotNull(risultato);
        assertEquals(1, risultato.size());
        assertEquals(10, risultato.get(0).getId());

        verify(pagamentoRepository).findByUtenteId(utenteId);
        verify(mapperPagamento).toDTOList(listPagamento);


    }

    @Test
    void getPagamentoUtenteId_shouldNotReturnListaPagamenti(){

        Integer utenteId=2;

        Pagamento pagamento=new Pagamento();
        pagamento.setId(9);
        PagamentoDto pagamentoDto=new PagamentoDto();
        pagamentoDto.setId(10);

        List<Pagamento>listPagamento=List.of(pagamento);
        List<PagamentoDto>listPagamentoDto=List.of(pagamentoDto);

        when(pagamentoRepository.findByUtenteId(utenteId))
                .thenReturn(listPagamento);

        //mappa listaPagamento a un  Dto
        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        // Act
        List<PagamentoDto> risultato =
                servicePagamento.getPagamentoPerUtenteId(utenteId);

        // Assert
        assertNotNull(risultato);
        assertEquals(1, risultato.size());
        assertEquals(10, risultato.get(0).getId());

        verify(pagamentoRepository).findByUtenteId(utenteId);
        verify(mapperPagamento).toDTOList(listPagamento);


    }

    @Test
    void getPagamentiPerStato_shoudReturnListaPagamenti(){

        PagamentoStatoEnum pagamentoStatoEnum=PagamentoStatoEnum.IN_ATTESA;
        Pagamento pagamento=new Pagamento();
        pagamento.setId(1);
        pagamento.setStato(pagamentoStatoEnum);

        PagamentoDto pagamentoDto=new PagamentoDto();
        pagamentoDto.setId(1);
        pagamentoDto.setStato(pagamentoStatoEnum);

        List<Pagamento>listPagamento=List.of(pagamento);
        List<PagamentoDto>listPagamentoDto=List.of(pagamentoDto);

        when(pagamentoRepository.findByStato(pagamentoStatoEnum))
                .thenReturn(listPagamento);

        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        List<PagamentoDto> risultato =
                servicePagamento.getPagamentiPerStato(pagamentoStatoEnum);

        assertNotNull(risultato);
        assertEquals(1,risultato.size());
        assertEquals(PagamentoStatoEnum.IN_ATTESA ,risultato.get(0).getStato());

        verify(pagamentoRepository).findByStato(pagamentoStatoEnum);
        verify(mapperPagamento).toDTOList(listPagamento);

    }


    @Test
    void getPagamentiPerStato_shoudReturnNullListaPagamenti(){

        PagamentoStatoEnum pagamentoStatoEnum=PagamentoStatoEnum.IN_ATTESA;
        Pagamento pagamento=new Pagamento();
        pagamento.setId(1);
        pagamento.setStato(pagamentoStatoEnum);

        PagamentoDto pagamentoDto=new PagamentoDto();
        pagamentoDto.setId(1);
        pagamentoDto.setStato(pagamentoStatoEnum);

        List<Pagamento>listPagamento=List.of(pagamento);
        List<PagamentoDto>listPagamentoDto=List.of(pagamentoDto);

        when(pagamentoRepository.findByStato(pagamentoStatoEnum))
                .thenReturn(listPagamento);

        when(mapperPagamento.toDTOList(listPagamento))
                .thenReturn(listPagamentoDto);

        List<PagamentoDto> risultato =
                servicePagamento.getPagamentiPerStato(pagamentoStatoEnum);

        assertNotNull(risultato);
        assertEquals(1,risultato.size());
        assertEquals(PagamentoStatoEnum.IN_ATTESA ,risultato.get(0).getStato());

        verify(pagamentoRepository).findByStato(pagamentoStatoEnum);
        verify(mapperPagamento).toDTOList(listPagamento);

    }

    @Test
    void  getPagamentiPerGiorno_shouldReturnLista(){

        LocalDate giorno=LocalDate.of(2026 ,7 ,20);


        LocalDateTime inizio = giorno.atStartOfDay();
        LocalDateTime fine = giorno.atTime(LocalTime.MAX);

        Pagamento pagamento=new Pagamento();
        pagamento.setId(1);

        PagamentoDto pagamentoDto=new PagamentoDto();
        pagamentoDto.setId(1);

        List<PagamentoDto>listaPagamentiDto=List.of(pagamentoDto);
        List<Pagamento>listaPagamenti=List.of(pagamento);


        when(pagamentoRepository.findByDataPagamentoBetween(inizio,fine)).thenReturn(listaPagamenti);
        when(mapperPagamento.toDTOList(listaPagamenti)).thenReturn(listaPagamentiDto);

        List<PagamentoDto>risultato=servicePagamento.getPagamentiPerGiorno(giorno);

        assertNotNull(risultato);
        // 1 perche compariamo con la lunghezza massima della lista che sarebbe 1
        assertEquals(1,risultato.size());
        assertEquals(1,risultato.get(0).getId());

        verify(pagamentoRepository)
                .findByDataPagamentoBetween(inizio, fine);

        verify(mapperPagamento)
                .toDTOList(listaPagamenti);
    }

    @Test
    void  getPagamentiPerGiorno_shouldReturnEmptyLista(){

        LocalDate giorno=LocalDate.of(2026 ,7 ,20);

        LocalDateTime inizio = giorno.atStartOfDay();
        LocalDateTime fine = giorno.atTime(LocalTime.MAX);

       // Pagamento pagamento=new Pagamento();
      //  pagamento.setId(1);
       // PagamentoDto pagamentoDto=new PagamentoDto();
       // pagamentoDto.setId(1);

        List<PagamentoDto>listaPagamentiDto=List.of();
        List<Pagamento>listaPagamenti=List.of();

        when(pagamentoRepository.findByDataPagamentoBetween(inizio,fine)).thenReturn(listaPagamenti);
        when(mapperPagamento.toDTOList(listaPagamenti)).thenReturn(listaPagamentiDto);

        List<PagamentoDto>risultato=servicePagamento.getPagamentiPerGiorno(giorno);

        assertNotNull(risultato);
        // 1 perche compariamo con la lunghezza massima della lista che sarebbe 1
      assertTrue(risultato.isEmpty());
        verify(pagamentoRepository)
                .findByDataPagamentoBetween(inizio, fine);

        verify(mapperPagamento)
                .toDTOList(listaPagamenti);
    }

   // Test su metodo : getPagamenti , suddiviso in :getPagamentiByUtente ,getPagamentiByStato , getPagamentiByGiorno

    @Test
    void getPagamenti_shouldReturnPagamentiByUtente() {

        Integer userId = 1;

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1);

        PagamentoDto dto = new PagamentoDto();
        dto.setId(1);

        List<Pagamento> lista = List.of(pagamento);
        List<PagamentoDto> listaDto = List.of(dto);

        when(pagamentoRepository.findByUtenteId(userId))
                .thenReturn(lista);

        when(mapperPagamento.toDTOList(lista))
                .thenReturn(listaDto);

        List<PagamentoDto> risultato =
                servicePagamento.getPagamenti(userId, null, null);

        assertNotNull(risultato);
        assertEquals(1, risultato.size());

        verify(pagamentoRepository).findByUtenteId(userId);
        verify(mapperPagamento).toDTOList(lista);
    }

    @Test
    void getPagamenti_shouldReturnPagamentiByStato() {

        PagamentoStatoEnum stato = PagamentoStatoEnum.IN_ATTESA;

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1);

        PagamentoDto dto = new PagamentoDto();
        dto.setId(1);

        List<Pagamento> lista = List.of(pagamento);
        List<PagamentoDto> listaDto = List.of(dto);

        when(pagamentoRepository.findByStato(stato))
                .thenReturn(lista);

        when(mapperPagamento.toDTOList(lista))
                .thenReturn(listaDto);

        List<PagamentoDto> risultato =
                servicePagamento.getPagamenti(null, stato, null);

        assertNotNull(risultato);
        assertEquals(1, risultato.size());

        verify(pagamentoRepository).findByStato(stato);
        verify(mapperPagamento).toDTOList(lista);
    }

    @Test
    void getPagamenti_shouldReturnPagamentiByGiorno() {

        LocalDate giorno = LocalDate.of(2026,7,20);

        LocalDateTime inizio = giorno.atStartOfDay();
        LocalDateTime fine = giorno.atTime(LocalTime.MAX);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(1);

        PagamentoDto dto = new PagamentoDto();
        dto.setId(1);

        List<Pagamento> lista = List.of(pagamento);
        List<PagamentoDto> listaDto = List.of(dto);

        when(pagamentoRepository.findByDataPagamentoBetween(inizio,fine))
                .thenReturn(lista);

        when(mapperPagamento.toDTOList(lista))
                .thenReturn(listaDto);

        List<PagamentoDto> risultato =
                servicePagamento.getPagamenti(null,null,giorno);

        assertNotNull(risultato);
        assertEquals(1, risultato.size());

        verify(pagamentoRepository)
                .findByDataPagamentoBetween(inizio,fine);

        verify(mapperPagamento)
                .toDTOList(lista);
    }


}
