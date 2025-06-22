package main.java.cl.duoc.soporte.controller;

import cl.duoc.soporte.model.Ticket;
import cl.duoc.soporte.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        Ticket nuevoTicket = ticketService.crearTicket(ticket);
        return ResponseEntity.ok(nuevoTicket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listarTickets() {
        return ResponseEntity.ok(ticketService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicket(@PathVariable Long id) {
        return ticketService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(
            @PathVariable Long id,
            @RequestBody Ticket ticket) {
        if (!ticketService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        return ResponseEntity.ok(ticketService.actualizarTicket(ticket));
    }
}