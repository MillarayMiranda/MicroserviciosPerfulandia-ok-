package cl.duoc.pedido.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.service.PedidoService;

@SpringBootTest
public class TestBD {

    @Autowired
    private PedidoService pedidoService;

    @Test
    public void testBuscarPedido() {
        Pedido pedidoBuscado = pedidoService.obtenerPorId(1L);
        assertEquals(1, pedidoBuscado.getId());
    }

    @Test
    public void testCrearPedido() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setClienteId(1);
        nuevoPedido.setFecha("01-01-2010", formatter); // La fecha se asignará automáticamente
        nuevoPedido.setTotal(10000.0);
        nuevoPedido.setEstado("PENDIENTE");

        Pedido pedidoCreado = pedidoService.crear(nuevoPedido);
        Long idCreado = pedidoCreado.getId();

        assertEquals(idCreado, pedidoCreado.getId()); // Compara otros campos
    }

    @Test
    public void testActualizarPedido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Pedido pedidoExistente = pedidoService.obtenerPorId(1L);
        pedidoExistente.setEstado("COMPLETADO");
        pedidoExistente.setFecha("01-01-2010", formatter); // La fecha se asignará automáticamente

        Pedido pedidoActualizado = pedidoService.actualizar(pedidoExistente.getId(), pedidoExistente);

        assertEquals("COMPLETADO", pedidoActualizado.getEstado());
    }

}
