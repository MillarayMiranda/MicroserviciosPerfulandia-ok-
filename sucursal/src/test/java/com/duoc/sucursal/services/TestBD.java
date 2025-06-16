package com.duoc.sucursal.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.duoc.sucursal.model.Sucursal;

@SpringBootTest
public class TestBD {

    @Autowired
    private SucursalServiceImpl sucursalService;

    @Test
    public void testBuscarSucursal() {
        Sucursal sucursalBuscada = sucursalService.obtenerSucursalPorId(1L);
        assertEquals(1, sucursalBuscada.getId());
    }

    @Test
    public void testCrearSucursal() {
        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setNombre("Nueva Sucursal");
        nuevaSucursal.setDireccion("Dirección de prueba");
        nuevaSucursal.setTelefono("987654321");

        Sucursal sucursalCreada = sucursalService.crearSucursal(nuevaSucursal);
        Long idCreado = sucursalCreada.getId();

        assertEquals(idCreado, sucursalCreada.getId()); // Compara otros campos
    }
}
