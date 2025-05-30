package cl.duoc.producto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import cl.duoc.producto.model.Producto;
import cl.duoc.producto.repository.ProductoRepository;
import cl.duoc.producto.services.ProductoServicesImpl;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServicesImpl productoServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearProducto() {
        Producto p = new Producto();
        p.setNombre("Producto Test");
        p.setDescripcion("Descripcion Test");
        p.setPrecio(100.0);
        p.setStock(10);

        when(productoRepository.save(any(Producto.class))).thenReturn(p);
        
        Producto productoCreado = productoServices.crearProducto(p);   
        
        assertEquals("Producto Test", productoCreado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class)); // Verificación correcta
    }
        
}
