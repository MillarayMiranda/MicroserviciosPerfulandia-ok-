package cl.duoc.cupon.config;

import cl.duoc.cupon.model.Cupon;
import cl.duoc.cupon.repository.CuponRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("dev") // Solo se ejecuta en perfil "dev"
public class CuponDataLoader {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();

    @Bean
    CommandLineRunner initCuponData(CuponRepository cuponRepository) {
        return args -> {
            if (cuponRepository.count() == 0) {
                for (int i = 0; i < 20; i++) {
                    Cupon cupon = new Cupon();
                    
                    cupon.setCodigo(generarCodigoChileno());
                    cupon.setDescuento(generarDescuentoRealista());
                    cupon.setEstado("ACTIVO");
                    cupon.setFechaCreacion(LocalDate.now());
                    cupon.setFechaExpiracion(generarFechaExpiracion());
                    cupon.setDescripcion(generarDescripcionRealista());
                    
                    cuponRepository.save(cupon);
                    System.out.println("[CuponDataLoader] Cupón creado: " + cupon.getCodigo());
                }
            }
        };
    }

    private String generarCodigoChileno() {
        String[] prefixes = {"DESC", "PROMO", "CUPON", "OFERTA", "BLACK"};
        String[] suffixes = {"CL", "CHILE", "2024", "SANTIAGO", "VERANO"};
        return prefixes[random.nextInt(prefixes.length)] + 
               faker.number().digits(4) + 
               suffixes[random.nextInt(suffixes.length)];
    }

    private Double generarDescuentoRealista() {
        double[] descuentos = {10.0, 15.0, 20.0, 25.0, 30.0, 40.0, 50.0};
        return descuentos[random.nextInt(descuentos.length)];
    }

    private LocalDate generarFechaExpiracion() {
        return LocalDate.now()
                .plusDays(30 + random.nextInt(180)); // Entre 1 y 6 meses
    }

    private String generarDescripcionRealista() {
        String[] productos = {
            "electrónica", 
            "supermercado", 
            "ropa", 
            "zapatos", 
            "libros",
            "muebles",
            "juguetes"
        };
        return "Válido en productos de " + productos[random.nextInt(productos.length)] + 
               " (" + faker.lorem().sentence(3) + ")";
    }
}