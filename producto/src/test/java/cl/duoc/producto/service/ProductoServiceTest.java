package cl.duoc.producto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
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

    @Test
    public void testBuscarProductoPorId() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Producto Test");
        p.setDescripcion("Descripcion Test");
        p.setPrecio(100.0);
        p.setStock(10);

        when(productoRepository.findById(1L)).thenReturn(java.util.Optional.of(p));
        
        Producto productoBuscado = productoServices.obtenerProductoPorId(1L);
        
        assertEquals("Producto Test", productoBuscado.getNombre());
        verify(productoRepository, times(1)).findById(1L); // Verificación correcta
    }   

    @Test
    public void testActualizarProducto() {
        Producto p = new Producto();
        p.setId(3L);
        p.setNombre("Producto Test");
        p.setDescripcion("Descripcion Test");
        p.setPrecio(100.0);
        p.setStock(10);

        // Mockear la búsqueda del producto existente
        when(productoRepository.findById(3L)).thenReturn(Optional.of(p));
        // Mockear el guardado
        when(productoRepository.save(any(Producto.class))).thenReturn(p);
        
        Producto productoActualizado = productoServices.actualizarProducto(3L, p);
        
        assertEquals("Producto Test", productoActualizado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
        verify(productoRepository, times(1)).findById(3L); // Verificar que se buscó
    }

    @Test
    public void testEliminarProducto() {
        Producto p = new Producto();
        p.setId(2L);
        p.setNombre("Producto Test");
        p.setDescripcion("Descripcion Test");
        p.setPrecio(100.0);
        p.setStock(10);

        // Mockear la existencia del producto
        when(productoRepository.existsById(2L)).thenReturn(true);
        
        productoServices.eliminarProducto(2L);
        
        verify(productoRepository, times(1)).deleteById(2L); // Verificación correcta
    }

    @Test
    public void testObtenerTodosProductos() {
        // No es necesario mockear el método, ya que no tiene lógica interna
        productoServices.obtenerTodosProductos();
        
        verify(productoRepository, times(1)).findAll(); // Verificación correcta
    }



            
}
