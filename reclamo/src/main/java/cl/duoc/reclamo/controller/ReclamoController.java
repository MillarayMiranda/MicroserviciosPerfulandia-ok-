package cl.duoc.reclamo.controller;

import cl.duoc.reclamo.model.Reclamo;
import cl.duoc.reclamo.service.ReclamoService;
import cl.duoc.reclamo.assembler.ReclamoAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.apache.el.stream.Optional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping("/reclamos")
@Tag(name = "Reclamos", description = "Controlador para gestión de reclamos de clientes")
public class ReclamoController {

    private final ReclamoService reclamoService;
    private final ReclamoAssembler reclamoAssembler;

    public ReclamoController(ReclamoService reclamoService, ReclamoAssembler reclamoAssembler) {
        this.reclamoService = reclamoService;
        this.reclamoAssembler = reclamoAssembler;
    }

    @Operation(summary = "Listar todos los reclamos", description = "Retorna una lista de todos los reclamos registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reclamos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Reclamo.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron reclamos",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json", schema = @Schema(type = "string")))
    })
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<Reclamo> reclamos = reclamoService.obtenerTodos();
            if (reclamos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron reclamos");
            }
            List<EntityModel<Reclamo>> recursos = reclamos.stream()
                    .map(reclamoAssembler::toModel)
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<Reclamo>> modelo = CollectionModel.of(recursos);
            modelo.add(linkTo(methodOn(ReclamoController.class).obtenerTodos()).withSelfRel());

            return ResponseEntity.ok(modelo);
        } catch (Exception e) {
            log.error("Error al listar reclamos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los reclamos: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener reclamo por ID", description = "Devuelve un reclamo específico por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reclamo encontrado",
            content = @Content(schema = @Schema(implementation = Reclamo.class))),
        @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
        
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            java.util.Optional<Reclamo> reclamo = reclamoService.obtenerPorId(id); // Asumiendo que devuelve Reclamo directamente
            if (reclamo != null) {
                return ResponseEntity.ok(reclamo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Reclamo no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error al obtener reclamo {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().body("Error al obtener el reclamo");
        }
    }
    @Operation(summary = "Crear nuevo reclamo", description = "Registra un nuevo reclamo en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reclamo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<?> crearReclamo(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del reclamo",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Reclamo.class),
                    examples = @ExampleObject(
                        name = "EjemploCrearReclamo",
                        summary = "Ejemplo de reclamo",
                        value = """
                            {
                                "titulo": "Producto defectuoso",
                                "descripcion": "El producto llegó roto",
                                "fecha": "2024-05-20",
                                "estado": "PENDIENTE",
                                "tipo": "RECLAMO",
                                "usuarioId": 123,
                                "idProducto": 456
                            }
                            """
                    )
                )
            )
            @RequestBody Reclamo reclamo) {
        try {
            Reclamo nuevo = reclamoService.crearReclamo(reclamo);
            return ResponseEntity.created(
                    linkTo(methodOn(ReclamoController.class).obtenerPorId(nuevo.getId())).toUri()
            ).body(reclamoAssembler.toModel(nuevo));
        } catch (Exception e) {
            log.error("Error al crear reclamo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el reclamo: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar reclamo", description = "Elimina un reclamo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reclamo eliminado"),
        @ApiResponse(responseCode = "404", description = "Reclamo no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReclamo(
            @Parameter(description = "ID del reclamo a eliminar", example = "1")
            @PathVariable Long id) {
        try {
            reclamoService.eliminarReclamo(id);
            return ResponseEntity.ok("Reclamo eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar reclamo {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el reclamo: " + e.getMessage());
        }
    }
}
