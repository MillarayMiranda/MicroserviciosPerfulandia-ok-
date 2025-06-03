package cl.duoc.inventario;

import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;


import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.services.InventarioService;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();

    @Autowired
    private InventarioService inventarioService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {

            Inventario nuevoInventario = new Inventario();
            // Generación de datos falsos para el inventario
            nuevoInventario.setCantidad(random.nextInt(100) + 1); // Cantidad entre 1 y 100
            nuevoInventario.setSucursalId((long) (random.nextInt(10) + 1)); // ID de sucursal entre 1 y 10
            nuevoInventario.setProductoId((long) (random.nextInt(50) + 1)); // ID de producto entre 1 y 50
            inventarioService.crearInventario(nuevoInventario);
            System.out.println("Inventario creado: Cantidad=" + nuevoInventario.getCantidad() +
                               ", SucursalID=" + nuevoInventario.getSucursalId() +
                               ", ProductoID=" + nuevoInventario.getProductoId());
        }
    }

}
