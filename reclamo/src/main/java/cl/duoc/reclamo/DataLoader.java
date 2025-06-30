package cl.duoc.reclamo;

import cl.duoc.reclamo.model.Reclamo;
import cl.duoc.reclamo.service.ReclamoService;
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
    private ReclamoService reclamoService;

    @Override
    public void run(String... args) {
        for (int i = 0; i < 10; i++) {
            Reclamo reclamo = Reclamo.builder()
                    .titulo("Problema con " + faker.commerce().productName())
                    .descripcion(faker.lorem().sentence(10))
                    .fecha(faker.date().past(20, TimeUnit.DAYS).toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate())
                    .usuarioId((long) faker.number().numberBetween(1, 100))
                    .idProducto((long) faker.number().numberBetween(1, 50))
                    .comentarioResolucion(null)
                    .build();

            reclamoService.crearReclamo(reclamo);
            System.out.println("✔ Reclamo creado: " + reclamo.getTitulo() + " | Usuario ID: " + reclamo.getUsuarioId());
        }
    }
}
