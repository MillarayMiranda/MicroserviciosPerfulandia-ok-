package cl.duoc.inventario.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.duoc.inventario.model.Inventario;

@SpringBootTest
public class TestBD {

    @Autowired
    private InventarioService inventarioService;

    @Test
    public void testBuscarInventario() {
        Inventario inventarioBuscado = inventarioService.findById(1L);
        assertEquals(1, inventarioBuscado.getId());
    }

    @Test
    public void testCrearInventario() {
        Inventario nuevoInventario = new Inventario();
        nuevoInventario.setProductoId(1L);
        nuevoInventario.setSucursalId(1L);
        nuevoInventario.setCantidad(100);

        Inventario inventarioCreado = inventarioService.crearInventario(nuevoInventario);
        Long idCreado = inventarioCreado.getId();

        assertEquals(idCreado, inventarioCreado.getId()); // Verifica que se asignó un ID
    }
    
}
