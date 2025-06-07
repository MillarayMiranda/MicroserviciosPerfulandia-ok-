package cl.duoc.pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.ZoneId;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.service.PedidoService;
import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));

    @Autowired
    private PedidoService pedidoService;

    @Override
        public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            Pedido pedido = new Pedido();

            pedido.setClienteId(faker.number().numberBetween(1, 21));
            
            pedido.setFecha(faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate()); // Asignar fecha aleatoria en el pasado

            // Total entre 1000 y 50000
            pedido.setTotal(faker.number().randomDouble(2, 1000, 50000));

            // Estado aleatorio
            pedido.setEstado(faker.options().option("PENDIENTE", "ENVIADO", "ENTREGADO", "CANCELADO"));

            pedidoService.crear(pedido);
        }
    }
    
}
