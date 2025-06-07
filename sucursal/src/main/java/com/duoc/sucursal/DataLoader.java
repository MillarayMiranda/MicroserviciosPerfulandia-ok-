package com.duoc.sucursal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.services.SucursalServices;

import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {
    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    @Autowired
    private SucursalServices sucursalServices;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Sucursal nuevoSucursal = new Sucursal();
            nuevoSucursal.setNombre(faker.company().name());
            nuevoSucursal.setDireccion(faker.address().fullAddress());
            nuevoSucursal.setTelefono(faker.phoneNumber().phoneNumber());

            sucursalServices.crearSucursal(nuevoSucursal);
            System.out.println("Sucursal creada: " + nuevoSucursal.getNombre());
        }
    }
}