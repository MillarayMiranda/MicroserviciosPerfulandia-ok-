package cl.duoc.factura.TestService;

import cl.duoc.factura.model.Factura;
import cl.duoc.factura.service.FacturaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestBD {

    @Autowired
    private FacturaService facturaService;

    @Test
    public void testCrearFactura() {
        Factura factura = new Factura();
        factura.setPedidoId(1001L);
        factura.setUsuarioId(2002L);
        factura.setFecha(LocalDate.of(2025, 6, 22));
        factura.setTotal(45000.0);
        factura.setMetodoPago("TRANSFERENCIA");

        Factura guardada = facturaService.guardarFactura(factura);

        assertNotNull(guardada.getId(), "El ID no debería ser nulo");
        assertEquals(1001L, guardada.getPedidoId());
        assertEquals(45000.0, guardada.getTotal());
    }

    @Test
    public void testBuscarFacturaPorId() {
        Factura factura = new Factura();
        factura.setPedidoId(3001L);
        factura.setUsuarioId(4002L);
        factura.setFecha(LocalDate.of(2025, 6, 23));
        factura.setTotal(19990.0);
        factura.setMetodoPago("WEBPAY");

        Factura guardada = facturaService.guardarFactura(factura);
        Factura buscada = facturaService.obtenerFacturaPorId(guardada.getId());

        assertNotNull(buscada);
        assertEquals(guardada.getId(), buscada.getId());
        assertEquals("WEBPAY", buscada.getMetodoPago());
    }

    @Test
    public void testActualizarFactura() {
        Factura factura = new Factura();
        factura.setPedidoId(4001L);
        factura.setUsuarioId(5002L);
        factura.setFecha(LocalDate.of(2025, 6, 24));
        factura.setTotal(25000.0);
        factura.setMetodoPago("EFECTIVO");

        Factura guardada = facturaService.guardarFactura(factura);

        // Actualizar campos
        guardada.setMetodoPago("TARJETA");
        guardada.setTotal(30000.0);

        Factura actualizada = facturaService.actualizarFactura(guardada.getId(), guardada);

        assertEquals("TARJETA", actualizada.getMetodoPago());
        assertEquals(30000.0, actualizada.getTotal());
    }

    @Test
    public void testEliminarFactura() {
        Factura factura = new Factura();
        factura.setPedidoId(6001L);
        factura.setUsuarioId(7002L);
        factura.setFecha(LocalDate.of(2025, 6, 25));
        factura.setTotal(15000.0);
        factura.setMetodoPago("DEBITO");

        Factura guardada = facturaService.guardarFactura(factura);
        Long id = guardada.getId();

        facturaService.eliminarFactura(id);

        // Verificar que fue eliminada correctamente
        Exception ex = assertThrows(RuntimeException.class, () -> {
            facturaService.obtenerFacturaPorId(id);
        });

        assertTrue(ex.getMessage().contains("Factura no encontrada con ID"));
    }
}
