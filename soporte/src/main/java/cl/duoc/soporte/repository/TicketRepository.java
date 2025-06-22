package main.java.cl.duoc.soporte.repository;

import cl.duoc.soporte.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}