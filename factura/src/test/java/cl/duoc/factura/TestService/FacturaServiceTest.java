package cl.duoc.factura.TestService;


import cl.duoc.factura.model.Factura;
import cl.duoc.factura.repository.FacturaRepository;
import cl.duoc.factura.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacturaServiceTest {

    private FacturaRepository facturaRepository;
    private FacturaService facturaService;

    @BeforeEach
    void setUp() {
        facturaRepository = mock(FacturaRepository.class);
        WebClient.Builder webClientBuilder = mock(WebClient.Builder.class);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(WebClient.builder());

        facturaService = new FacturaService(facturaRepository, webClientBuilder);
    }

    @Test
    void testObtenerTodasFacturas() {
        Factura f1 = new Factura();
        f1.setId(1L);
        Factura f2 = new Factura();
        f2.setId(2L);

        when(facturaRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Factura> resultado = facturaService.obtenerTodasFacturas();

        assertEquals(2, resultado.size());
        verify(facturaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerFacturaPorId() {
        Factura factura = new Factura();
        factura.setId(100L);
        factura.setTotal(19990.0);
        factura.setFecha(LocalDate.now());

        when(facturaRepository.findById(100L)).thenReturn(Optional.of(factura));

        Factura resultado = facturaService.obtenerFacturaPorId(100L);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getId());
    }

    @Test
    void testGuardarFactura() {
        Factura nuevaFactura = new Factura();
        nuevaFactura.setId(200L);
        nuevaFactura.setTotal(24990.0);

        when(facturaRepository.save(nuevaFactura)).thenReturn(nuevaFactura);

        Factura resultado = facturaService.guardarFactura(nuevaFactura);

        assertNotNull(resultado);
        assertEquals(200L, resultado.getId());
        assertEquals(24990.0, resultado.getTotal());
    }

    @Test
    void testObtenerFacturasPorUsuario() {
        Long usuarioId = 3000L;

        Factura f1 = new Factura();
        f1.setId(1L);
        f1.setUsuarioId(usuarioId);

        Factura f2 = new Factura();
        f2.setId(2L);
        f2.setUsuarioId(usuarioId);

        when(facturaRepository.findByUsuarioId(usuarioId)).thenReturn(Arrays.asList(f1, f2));

        List<Factura> resultado = facturaService.obtenerFacturasPorUsuario(usuarioId);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(f -> f.getUsuarioId().equals(usuarioId)));
        verify(facturaRepository, times(1)).findByUsuarioId(usuarioId);
    }
}
