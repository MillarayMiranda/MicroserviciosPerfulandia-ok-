package cl.duoc.perfulandia.usuario;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import cl.duoc.perfulandia.usuario.services.ProductoServices;
import cl.duoc.perfulandia.usuario.model.Usuario;
import cl.duoc.perfulandia.usuario.model.producto;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();
    
    @Autowired
    private UsuarioServices productoServices;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {

            producto nuevoProducto = new producto();
            nuevoProducto.setNombre(faker.commerce().productName());
            nuevoProducto.setDescripcion(faker.lorem().sentence(10));
            nuevoProducto.setPrecio(Double.parseDouble(faker.commerce().price(10000, 100000)));
            nuevoProducto.setStock(random.nextInt(100) + 1);
            productoServices.crearProducto(nuevoProducto);
            System.out.println("Producto creado: " + nuevoProducto.getNombre());
        }
    }
}
