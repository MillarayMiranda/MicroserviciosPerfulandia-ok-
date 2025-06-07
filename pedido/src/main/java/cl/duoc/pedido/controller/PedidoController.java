package cl.duoc.pedido.controller;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gestionar los pedidos de Perfulandia") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encontraron pedidos"))),
    })
    public ResponseEntity<?> obtenerTodosLosPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los pedidos: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Error al crear el pedido",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear el pedido"))),
    })
    public ResponseEntity<?> crearPedido(@Valid @RequestBody Pedido pedido) {
        try {
            if (pedido.getFecha() == null) { // Ahora la fecha se parseará correctamente
                return ResponseEntity.badRequest().body("La fecha es requerida");
            }
            Pedido nuevoPedido = pedidoService.crear(pedido);
            return ResponseEntity.ok(nuevoPedido);
            
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest()
                    .body("Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al crear pedido: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve un pedido específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Pedido no encontrado con ID: {id}"))),
    })
    public ResponseEntity<?> obtenerPedidoPorId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.obtenerPorId(id);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pedido no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el pedido: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido existente con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no pudo ser actualizado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al actualizar el pedido: Pedido no encontrado con ID: {id}"))),
    })
    public ResponseEntity<?> actualizarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedidoActualizado) {
        try {
            Pedido pedido = pedidoService.actualizar(id, pedidoActualizado);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Pedido no encontrado con ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el pedido: " + e.getMessage());
        }
    }
}