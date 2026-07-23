package progetto.centroSportivo.centroSportivo.ControllerTest;

import it.controller.PagamentoController;
import it.dto.PagamentoDto;
import it.enumerated.PagamentoStatoEnum;
import it.enumerated.PagamentoTypeEnum;
import it.model.Pagamento;
import it.service.PagamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(Pagamento.class)
@ContextConfiguration(classes = PagamentoController.class)
public class PagamentoControllerTest {
    @Mock
    @MockitoBean
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;


    @Test
    void testGetPagamentoPerUtente() throws Exception {

        Integer utenteId = 1;
        List<PagamentoDto> lista = new ArrayList<>();

        when(pagamentoService.getPagamentoPerUtenteId(utenteId))
                .thenReturn(lista);

        ResponseEntity<List<PagamentoDto>> response =
                pagamentoController.getPagamentoPerUtente(utenteId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(lista, response.getBody());

        verify(pagamentoService, times(1))
                .getPagamentoPerUtenteId(utenteId);
    }

// da rivedere
    @Test
    void testGetPagamentoPerUtente_shouldReturnNull() throws Exception {


        PagamentoDto p1=new PagamentoDto();
        p1.setStato(PagamentoStatoEnum.COMPLETATO);
        PagamentoDto p2=new PagamentoDto();
        p2.setStato(PagamentoStatoEnum.COMPLETATO);

        List<PagamentoDto> lista =List.of(p1,p2);
    }

    @Test
    void testGetPagamentiPerStato() {

        PagamentoStatoEnum stato = PagamentoStatoEnum.ANNULLATO;

        List<PagamentoDto> lista = List.of(
                new PagamentoDto(),
                new PagamentoDto()
        );

        when(pagamentoService.getPagamentiPerStato(stato))
                .thenReturn(lista);

        ResponseEntity<List<PagamentoDto>> response =
                pagamentoController.getPagamentiPerStato(stato);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(lista, response.getBody());

        verify(pagamentoService, times(1))
                .getPagamentiPerStato(stato);
    }

    //da fare
    /*
    @Test
    void testGetPagamentiPerStato_shouldReturnEmpty() {

        PagamentoStatoEnum stato = PagamentoStatoEnum;

        List<PagamentoDto> lista = List.of(
                new PagamentoDto(),
                new PagamentoDto()
        );

        when(pagamentoService.getPagamentiPerStato(stato))
                .thenReturn(lista);

        ResponseEntity<List<PagamentoDto>> response =
                pagamentoController.getPagamentiPerStato(stato);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(lista, response.getBody());

        verify(pagamentoService, times(1))
                .getPagamentiPerStato(stato);
    }
*/

    @Test
    void testGetPagamentiPerGiorno(){

        LocalDate lunedi= LocalDate.of(2026,04,23);

       PagamentoDto pagamentoDto=new PagamentoDto();
       List<PagamentoDto>listaPagamenti=List.of(pagamentoDto);
       when(pagamentoService.getPagamentiPerGiorno(lunedi))
               .thenReturn(listaPagamenti);
        ResponseEntity <List<PagamentoDto>>resp=pagamentoController.getPagamentiPerGiorno(lunedi);

        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(listaPagamenti, resp.getBody());

        verify(pagamentoService, times(1))
                .getPagamentiPerGiorno(lunedi);
        verifyNoMoreInteractions(pagamentoService);
    }

    @Test
    void getPagamenti_ShouldReturnListaPagamenti() {

        Integer userId = 1;
        PagamentoStatoEnum stato = PagamentoStatoEnum.IN_ATTESA;
        LocalDate giorno = LocalDate.of(2024, 5, 10);

        PagamentoDto pagamento = new PagamentoDto();
        List<PagamentoDto> lista = List.of(pagamento);

        when(pagamentoService.getPagamenti(userId, stato, giorno))
                .thenReturn(lista);


        ResponseEntity<List<PagamentoDto>> response =
                pagamentoController.getPagamenti(userId, stato, giorno);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lista, response.getBody());

        verify(pagamentoService, times(1))
                .getPagamenti(userId, stato, giorno);
        verifyNoMoreInteractions(pagamentoService);
    }
}



