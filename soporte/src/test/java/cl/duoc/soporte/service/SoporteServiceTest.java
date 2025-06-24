package cl.duoc.soporte.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.duoc.soporte.model.Ticket;
import cl.duoc.soporte.repository.TicketRepository;
import cl.duoc.soporte.services.TicketService;

public class SoporteServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks    
    private TicketService soporteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearTicket() {
        Ticket ticket = new Ticket();
        ticket.setEmailCliente("ejemplo@ejemplo.com");
        ticket.setNombreCliente("juan perez");
        ticket.setMotivo("Problema con el pedido");
        ticket.setEstado("ABIERTO");

    }

}
