package com.duoc.sucursal.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.repository.SucursalRepository;

public class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalServiceImpl sucursalService;

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test   
    public void testCrearSucursal() {
        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setNombre("Sucursal Test");
        nuevaSucursal.setDireccion("123 Test St");
        nuevaSucursal.setTelefono("123456789");

        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(nuevaSucursal);

        Sucursal sucursalCreada = sucursalService.crearSucursal(nuevaSucursal);

        assertEquals("Sucursal Test", sucursalCreada.getNombre());
       verify(sucursalRepository, times(1)).save(any(Sucursal.class)); // Verificación correcta}
    }

    @Test
    public void testBuscarSucursalPorId() {
        Sucursal sucursal = new Sucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Test");
        sucursal.setDireccion("123 Test St");
        sucursal.setTelefono("123456789");

        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(sucursal));

        Sucursal sucursalBuscada = sucursalService.obtenerSucursalPorId(1L);

        assertEquals("Sucursal Test", sucursalBuscada.getNombre());
        verify(sucursalRepository, times(1)).findById(1L); // Verificación correcta
    }

    @Test
    public void testActualizarSucursal() {
        Sucursal sucursalExistente = new Sucursal();
        sucursalExistente.setId(1L);
        sucursalExistente.setNombre("Sucursal Test");
        sucursalExistente.setDireccion("123 Test St");
        sucursalExistente.setTelefono("123456789");

        when(sucursalRepository.findById(1L)).thenReturn(java.util.Optional.of(sucursalExistente));
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalExistente);

        sucursalExistente.setNombre("Sucursal Actualizada");
        Sucursal sucursalActualizada = sucursalService.actualizarSucursal(1L, sucursalExistente);

        assertEquals("Sucursal Actualizada", sucursalActualizada.getNombre());
        verify(sucursalRepository, times(1)).save(any(Sucursal.class)); // Verificación correcta
    }


}
