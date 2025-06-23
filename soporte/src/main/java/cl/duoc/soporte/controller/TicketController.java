package cl.duoc.soporte.controller;

import cl.duoc.soporte.assembler.SoporteModelAssembler;
import cl.duoc.soporte.model.Ticket;
import cl.duoc.soporte.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "Endpoints para los tickets de soporte de Perfulandia")
public class TicketController {
    
    @Autowired
    private SoporteModelAssembler assembler;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Crear un nuevo ticket de soporte", description = "Permite crear un ticket de soporte para Perfulandia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type= "string", example = "Ticket creado exitosamente"))), 
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Solicitud incorrecta, datos inválidos"))),
    })
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        Ticket nuevoTicket = ticketService.crearTicket(ticket);
        return ResponseEntity.ok(assembler.toModel(nuevoTicket).getContent());
    }

    @GetMapping
    @Operation(summary = "Listar todos los tickets de soporte", description = "Obtiene todos los tickets de soporte registrados en Perfulandia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tickets obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tickets",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encontraron tickets"))),
    })
    public ResponseEntity<?> listarTickets() {
        List<Ticket> tickets = ticketService.obtenerTodos();
        if (tickets.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(tickets));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un ticket por ID", description = "Permite obtener un ticket de soporte específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Ticket no encontrado"))),
    })
    public ResponseEntity<?> obtenerTicket(@PathVariable Long id) {
        return ticketService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un ticket de soporte", description = "Permite actualizar un ticket de soporte existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Ticket no encontrado"))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos inválidos",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Solicitud incorrecta, datos inválidos"))),
    })
    public ResponseEntity<Ticket> actualizarTicket(
            @PathVariable Long id,
            @RequestBody Ticket ticket) {
        if (!ticketService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ticket.setId(id);
        return ResponseEntity.ok(assembler.toModel(ticketService.actualizarTicket(ticket)).getContent());
    }
}