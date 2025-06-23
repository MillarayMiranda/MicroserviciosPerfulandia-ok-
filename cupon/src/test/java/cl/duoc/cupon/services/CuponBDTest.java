package cl.duoc.cupon.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.cupon.model.Cupon;
import cl.duoc.cupon.repository.CuponRepository;
import cl.duoc.cupon.service.CuponService;

@SpringBootTest
@Transactional
public class CuponBDTest {

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private CuponService cuponService;

    @Test
    void crearCupon_DeberiaGenerarFechaCreacionAutomatica() {
        Cupon nuevo = Cupon.builder()
                .codigo("PRUEBA01")
                .descuento(10.0)
                .build();
        
        Cupon guardado = cuponService.crearCupon(nuevo);
        
        assertNotNull(guardado.getFechaCreacion());
        assertEquals(LocalDate.now(), guardado.getFechaCreacion());
    }

    @Test
    void canjearCupon_DeberiaBloquearReuso() {
        Cupon cupon = cuponRepository.save(Cupon.builder()
                .codigo("SOLOUNO")
                .descuento(30.0)
                .build());
        
        assertTrue(cuponService.canjearCupon("SOLOUNO"));
        assertFalse(cuponService.canjearCupon("SOLOUNO"));
        
        Cupon actualizado = cuponRepository.findById(cupon.getId()).get();
        assertEquals("USADO", actualizado.getEstado());
    }

    @Test
    void buscarPorCodigo_DeberiaEncontrarCupon() {
        cuponRepository.save(Cupon.builder()
                .codigo("ESPECIAL")
                .descuento(50.0)
                .fechaExpiracion(LocalDate.now().plusDays(10))
                .build());
        
        Optional<Cupon> encontrado = cuponRepository.findByCodigo("ESPECIAL");
        
        assertTrue(encontrado.isPresent());
        assertEquals(50.0, encontrado.get().getDescuento());
    }
}