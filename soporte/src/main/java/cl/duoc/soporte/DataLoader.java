package cl.duoc.soporte;

import java.time.ZoneId;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import net.datafaker.Faker;
import cl.duoc.soporte.services.TicketService;
import cl.duoc.soporte.model.Ticket;

@Component
public class DataLoader implements CommandLineRunner {
    private final Faker faker = new Faker(new Locale("es"));
    
    
    @Autowired
    private TicketService ticketService;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Ticket nuevoTicket = new Ticket();
            nuevoTicket.setEmailCliente(faker.internet().emailAddress());
            nuevoTicket.setNombreCliente(faker.name().fullName());
            nuevoTicket.setMotivo(faker.lorem().sentence());
            nuevoTicket.setFechaCreacion(faker.date().past(365, TimeUnit.DAYS).toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate()); 
            nuevoTicket.setEstado(faker.options().option("ABIERTO", "EN_PROCESO", "CERRADO"));

            ticketService.crearTicket(nuevoTicket);
            System.out.println("Ticket creado: " + nuevoTicket.getId() + " - " + nuevoTicket.getMotivo());
        }
    }



}
