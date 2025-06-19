package cl.duoc.factura.config;

import cl.duoc.factura.model.Factura;
import cl.duoc.factura.repository.FacturaRepository;
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
@Profile("dev")
public class FacturaDataLoader {

    private final Faker faker = new Faker(new Locale("es", "CL"));
    private final Random random = new Random();

    @Bean
    CommandLineRunner initFacturaData(FacturaRepository facturaRepository) {
        return args -> {
            if (facturaRepository.count() == 0) {
                for (int i = 0; i < 15; i++) {
                    Factura factura = new Factura();
                    
                    factura.setPedidoId(1000L + i);
                    factura.setUsuarioId(2000L + random.nextInt(5));
                    factura.setFecha(generarFechaAleatoria());
                    factura.setTotal(generarMontoRealista());
                    factura.setMetodoPago(generarMetodoPagoChile());
                    
                    facturaRepository.save(factura);
                    System.out.println("[DataLoader] Factura creada: ID " + factura.getId());
                }
            }
        };
    }

    private LocalDate generarFechaAleatoria() {
        return faker.date()
                .past(365, TimeUnit.DAYS)
                .toInstant()
                .atZone(ZoneId.of("America/Santiago"))
                .toLocalDate();
    }

    private Double generarMontoRealista() {
        double[] montosBase = {4990, 9990, 14990, 19990, 24990, 29990, 39990, 49990};
        int cantidad = 1 + random.nextInt(3);
        double monto = montosBase[random.nextInt(montosBase.length)] * cantidad;
        return (double) Math.round(monto * 1.19);
    }

    private String generarMetodoPagoChile() {
        String[] metodos = {
            "Tarjeta de crédito", 
            "Tarjeta de débito",
            "Transferencia bancaria",
            "WebPay",
            "Cheque",
            "Efectivo"
        };
        return metodos[random.nextInt(metodos.length)];
    }
}
