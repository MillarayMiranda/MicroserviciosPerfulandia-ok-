package cl.duoc.cupon.service;

import cl.duoc.cupon.model.Cupon;
import cl.duoc.cupon.service.CuponService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es"));

    @Autowired
    private CuponService cuponService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Cupon cupon = Cupon.builder()
                    .codigo("CUPON-" + faker.number().digits(6))
                    .descripcion(faker.commerce().productName())
                    .descuento((double) faker.number().numberBetween(5, 50))
                    .fechaExpiracion(faker.date().future(60, TimeUnit.DAYS)
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .estado(faker.options().option("ACTIVO", "INACTIVO"))
                    .build();

            cuponService.crearCupon(cupon);
            System.out.println("Cupón creado: " + cupon.getCodigo() + " - " + cupon.getDescripcion());
        }
    }
}

