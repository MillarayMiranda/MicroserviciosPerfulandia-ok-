package cl.duoc.inventario.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.repository.InventarioRepository;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearInventario() {

        Inventario nuevoInventario = new Inventario();
        nuevoInventario.setProductoId(1L);
        nuevoInventario.setSucursalId(1L);
        nuevoInventario.setCantidad(100);

        when(inventarioRepository.save(any(Inventario.class))).thenReturn(nuevoInventario);

        Inventario inventarioCreado = inventarioService.crearInventario(nuevoInventario);
        
        assertEquals(1L, inventarioCreado.getProductoId());
        verify(inventarioRepository, times(1)).save(any(Inventario.class)); 
    }

    @Test
    public void testBuscarInventarioPorId() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(1L);
        inventario.setSucursalId(1L);
        inventario.setCantidad(100);

        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.of(inventario));

        Inventario inventarioBuscado = inventarioService.findById(1L);

        assertEquals(1L, inventarioBuscado.getId());
        verify(inventarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testActualizarInventario() {
        Inventario inventarioExistente = new Inventario();
        inventarioExistente.setId(1L);
        inventarioExistente.setProductoId(1L);
        inventarioExistente.setSucursalId(1L);
        inventarioExistente.setCantidad(100);

        when(inventarioRepository.findById(1L)).thenReturn(java.util.Optional.of(inventarioExistente));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioExistente);

        Inventario inventarioActualizado = new Inventario();
        inventarioActualizado.setId(1L);
        inventarioActualizado.setProductoId(2L);
        inventarioActualizado.setSucursalId(1L);
        inventarioActualizado.setCantidad(200);

        Inventario resultado = inventarioService.update(1L, inventarioActualizado);

        assertEquals(2L, resultado.getProductoId());
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }   

}
