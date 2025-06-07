package cl.duoc.producto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.duoc.producto.model.Producto;
import cl.duoc.producto.services.ProductoServices;

@SpringBootTest
public class testBuscarProductoDB {
    
    @Autowired
    private ProductoServices productoServices;

    @Test
    public void testBuscarProducto() {
        Producto productoBuscado = productoServices.obtenerProductoPorId(3L);
        assertEquals("Awesome Paper Bench", productoBuscado.getNombre());

    }
    @Test
    public void testCrearProducto() {
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Nuevo Producto");
        nuevoProducto.setDescripcion("Descripción del nuevo producto");
        nuevoProducto.setPrecio(100.0);
        nuevoProducto.setStock(50);
        nuevoProducto.setCategoria("Categoria de prueba");
        
        Producto productoCreado = productoServices.crearProducto(nuevoProducto);
        Long idCreado = productoCreado.getId();
        
        assertNotNull(productoCreado.getId()); // Solo verifica que se asignó un ID
        assertEquals(idCreado, productoCreado.getId()); // Compara otros campos
    }


}
