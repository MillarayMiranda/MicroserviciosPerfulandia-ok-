package main.java.cl.duoc.soporte.services;

import cl.duoc.soporte.model.Ticket;
import cl.duoc.soporte.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket crearTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> obtenerPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket actualizarTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}