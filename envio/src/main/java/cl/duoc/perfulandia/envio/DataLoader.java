package cl.duoc.perfulandia.envio;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.perfulandia.envio.model.Envio;
import cl.duoc.perfulandia.envio.service.EnvioService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();

    @Autowired
    private EnvioService envioservice;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Envio nuevoEnvio = new Envio();
            nuevoEnvio.setDireccionEntrega(faker.address().fullAddress());
            nuevoEnvio.setEstado(faker.options().option("Enviado", "Pendiente", "Entregado"));
            nuevoEnvio.setFechaEnvio(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            nuevoEnvio.setPedidoId((long) (random.nextInt(100) + 1));
            envioservice.Guardar(nuevoEnvio);
            System.out.println("Envio creado: " + nuevoEnvio.getDireccionEntrega());
        }
    }

}
