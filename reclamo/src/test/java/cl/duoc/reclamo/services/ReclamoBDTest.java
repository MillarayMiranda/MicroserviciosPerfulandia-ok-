package cl.duoc.reclamo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.reclamo.model.Reclamo;
import cl.duoc.reclamo.repository.ReclamoRepository;
import cl.duoc.reclamo.service.ReclamoService;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReclamoBDTest {

    @Autowired
    private ReclamoService reclamoService;

    @Autowired
    private ReclamoRepository reclamoRepository;

    private Reclamo reclamoExistente;

    @BeforeEach
    void setUp() {
        reclamoExistente = Reclamo.builder()
                .titulo("Producto defectuoso")
                .descripcion("El producto llegó dañado")
                .fecha(LocalDate.now())  // Cambiado de fechaCreacion a fecha
                .usuarioId(1L)
                .build();
        
        reclamoExistente = reclamoRepository.save(reclamoExistente);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaReclamos() {
        List<Reclamo> reclamos = reclamoService.obtenerTodos();
        
        assertFalse(reclamos.isEmpty());
        assertEquals("Producto defectuoso", reclamos.get(0).getTitulo());
    }

    @Test
    void obtenerPorId_DeberiaRetornarReclamoExistente() {
        Optional<Reclamo> encontrado = reclamoService.obtenerPorId(reclamoExistente.getId());
        
        assertTrue(encontrado.isPresent());
        assertEquals("Producto defectuoso", encontrado.get().getTitulo());
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioCuandoNoExiste() {
        Optional<Reclamo> encontrado = reclamoService.obtenerPorId(999L);
        
        assertFalse(encontrado.isPresent());
    }

    @Test
    void crearReclamo_DeberiaGuardarCorrectamente() {
        Reclamo nuevoReclamo = Reclamo.builder()
                .titulo("Nuevo reclamo")
                .descripcion("Descripción del nuevo reclamo")
                .fecha(LocalDate.now())  // Agregado campo fecha
                .usuarioId(2L)
                .build();

        Reclamo creado = reclamoService.crearReclamo(nuevoReclamo);
        
        assertNotNull(creado.getId());
        assertEquals("Nuevo reclamo", creado.getTitulo());
    }

    @Test
    void crearReclamo_DeberiaLanzarExcepcionCuandoDatosInvalidos() {
        Reclamo reclamoInvalido = new Reclamo(); // Falta título y descripción
        
        assertThrows(Exception.class, () -> {
            reclamoService.crearReclamo(reclamoInvalido);
        });
    }

    @Test
    void eliminarReclamo_DeberiaEliminarCorrectamente() {
        reclamoService.eliminarReclamo(reclamoExistente.getId());
        
        assertFalse(reclamoRepository.existsById(reclamoExistente.getId()));
    }

    @Test
    void eliminarReclamo_DeberiaLanzarExcepcionCuandoNoExiste() {
        assertThrows(Exception.class, () -> {
            reclamoService.eliminarReclamo(999L);
        });
    }
}