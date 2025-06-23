package cl.duoc.factura;

import cl.duoc.factura.model.Factura;

import cl.duoc.factura.service.FacturaService;
import net.datafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Configuration
@Profile("dev")
public class FacturaDataLoader implements CommandLineRunner {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();

    @Autowired
    private FacturaService facturaService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 50; i++) {
            Factura factura = new Factura();
            factura.setPedidoId(1000L + i);
            factura.setUsuarioId(2000L + random.nextInt(5));
            factura.setFecha(faker.date()
                    .past(365, TimeUnit.DAYS)
                    .toInstant()
                    .atZone(ZoneId.of("America/Santiago"))
                    .toLocalDate());
            factura.setTotal(faker.number().randomDouble(2, 4990, 49990)); // Monto entre 4990 y 49990
            factura.setMetodoPago(faker.options().option(
                    "Tarjeta de crédito",
                    "Tarjeta de débito",
                    "Transferencia bancaria",
                    "WebPay",
                    "Cheque",
                    "Efectivo"
            ));        
            facturaService.guardarFactura(factura);
            System.out.println("[DataLoader] Factura creada: ID " + factura.getId());
        }
        }
}
