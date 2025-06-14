package cl.duoc.pedido.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.repository.PedidoRepository;
import cl.duoc.pedido.service.PedidoServiceImpl;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository productRepository;

    @InjectMocks    
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPedido() {
        // 1. Crear formatter para la fecha (si necesitas parsear desde string)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // 2. Crear el objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1);
        pedido.setFecha(LocalDate.parse("01-01-2010", formatter)); // Parsear la fecha
        pedido.setTotal(100.0); 
        pedido.setEstado("PENDIENTE");

        // 3. Simular el comportamiento del repositorio
        when(productRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        // 4. (Opcional) Si estás testeando el servicio:
        Pedido resultado = pedidoService.crear(pedido);
        
        // 5. Verificaciones
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(LocalDate.of(2010, 1, 1), resultado.getFecha());
    }   

    @Test
    public void testObtenerPedidoPorId() {
        // 1. Crear formatter para la fecha (si necesitas parsear desde string)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // 2. Crear el objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1);
        pedido.setFecha(LocalDate.parse("01-01-2010", formatter)); // Parsear la fecha
        pedido.setTotal(100.0); 
        pedido.setEstado("PENDIENTE");

        // 3. Simular el comportamiento del repositorio
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(pedido));
        
        // 4. (Opcional) Si estás testeando el servicio:
        Pedido resultado = pedidoService.obtenerPorId(1L);
        
        // 5. Verificaciones
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(LocalDate.of(2010, 1, 1), resultado.getFecha());
    }

    @Test
    public void testActualizarPedido() {
        // 1. Crear formatter para la fecha (si necesitas parsear desde string)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // 2. Crear el objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1);
        pedido.setFecha(LocalDate.parse("01-01-2010", formatter)); // Parsear la fecha
        pedido.setTotal(100.0); 
        pedido.setEstado("PENDIENTE");

        // 3. Simular el comportamiento del repositorio
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(pedido));
        when(productRepository.save(any(Pedido.class))).thenReturn(pedido);
        
        // 4. (Opcional) Si estás testeando el servicio:
        Pedido resultado = pedidoService.actualizar(1L, pedido);
        
        // 5. Verificaciones
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(LocalDate.of(2010, 1, 1), resultado.getFecha());
    }

    @Test
    public void testObtenerTodosProductos() {
        // 1. Crear formatter para la fecha (si necesitas parsear desde string)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // 2. Crear el objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(1);
        pedido.setFecha(LocalDate.parse("01-01-2010", formatter)); // Parsear la fecha
        pedido.setTotal(100.0); 
        pedido.setEstado("PENDIENTE");

        // 3. Simular el comportamiento del repositorio
        when(productRepository.findAll()).thenReturn(java.util.List.of(pedido));
        
        // 4. (Opcional) Si estás testeando el servicio:
        java.util.List<Pedido> resultado = pedidoService.obtenerTodos();
        
        // 5. Verificaciones
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
    }


        

    



}
