package cl.duoc.reclamo;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.reclamo.model.Reclamo;
import cl.duoc.reclamo.repository.ReclamoRepository;
import cl.duoc.reclamo.service.ReclamoService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final ReclamoService reclamoService;

        private final Faker faker = new Faker(new Locale("es", "CL"));
        private final Random random = new Random();

    DataLoader(ReclamoService reclamoService) {
        this.reclamoService = reclamoService;
    } 

        @Override
        public void run(String... args) throws Exception {  
                for (int i = 0; i < 100; i++) {
                        Reclamo nuevoReclamo = new Reclamo();
                        nuevoReclamo.setTitulo(faker.lorem().sentence(3));
                        nuevoReclamo.setDescripcion(faker.lorem().paragraph(2));
                        nuevoReclamo.setFecha(faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate()); // Asignar fecha aleatoria en el pasado

                        nuevoReclamo.setUsuarioId((long) (random.nextInt(1000) + 1)); // Simula un ID de usuario
                        nuevoReclamo.setIdProducto((long) (random.nextInt(100) + 1)); // Simula un ID de producto
                        nuevoReclamo.setComentarioResolucion(faker.lorem().sentence(5));

                        // Aquí deberías guardar el reclamo en la base de datos usando tu repositorio o servicio
                        reclamoService.crearReclamo(nuevoReclamo); // Asegúrate de que este método exista en tu repositorio

                        System.out.println("Reclamo creado: " + nuevoReclamo.getTitulo());
                }
        }

}
