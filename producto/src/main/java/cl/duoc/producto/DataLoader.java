package cl.duoc.producto;

import java.util.Locale;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.producto.model.Producto;
import cl.duoc.producto.services.ProductoServices;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();
    
    @Autowired
    private ProductoServices productoServices;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombre(faker.commerce().productName());
            nuevoProducto.setDescripcion(faker.lorem().sentence(10));
            
            // Solución: Reemplazar coma por punto
            String precioStr = faker.commerce().price(10000, 100000).replace(",", ".");
            nuevoProducto.setPrecio(Double.parseDouble(precioStr));
            
            nuevoProducto.setStock(random.nextInt(100) + 1);
            nuevoProducto.setCategoria(faker.commerce().department());
            productoServices.crearProducto(nuevoProducto);
            System.out.println("Producto creado: " + nuevoProducto.getNombre());
        }
    }
}