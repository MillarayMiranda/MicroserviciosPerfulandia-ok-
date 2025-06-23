package cl.duoc.soporte.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import cl.duoc.soporte.controller.TicketController;
import cl.duoc.soporte.model.Ticket;


@Component
public class SoporteModelAssembler implements RepresentationModelAssembler<Ticket, EntityModel<Ticket>> {

    @Override
    public EntityModel<Ticket> toModel(Ticket ticket) {
        return EntityModel.of(
            ticket,
            linkTo(methodOn(TicketController.class).listarTickets()).withRel("Lista los tickets"),
            linkTo(methodOn(TicketController.class).obtenerTicket(ticket.getId())).withRel("Obtiene un ticket por ID"),
            linkTo(methodOn(TicketController.class).crearTicket(ticket)).withRel("Crea un nuevo ticket"),
            linkTo(methodOn(TicketController.class).actualizarTicket(ticket.getId(), ticket)).withRel("Actualiza un ticket")
);
    }

}
