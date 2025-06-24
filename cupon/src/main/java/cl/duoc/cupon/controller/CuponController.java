package cl.duoc.cupon.controller;

import cl.duoc.cupon.assembler.CuponAssembler;
import cl.duoc.cupon.model.Cupon;
import cl.duoc.cupon.service.CuponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/cupones")
@Tag(name = "Cupones", description = "Controlador para gestión de cupones de descuento")
public class CuponController {

    private final CuponService cuponService;
    private final CuponAssembler cuponAssembler;

    public CuponController(CuponService cuponService, CuponAssembler cuponAssembler) {
        this.cuponService = cuponService;
        this.cuponAssembler = cuponAssembler;
    }

    @Operation(summary = "Obtener todos los cupones", description = "Retorna una lista completa de todos los cupones registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de cupones obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Cupon.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron cupones",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encontraron cupones"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al obtener los cupones: <error_message>")))
    })
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosCupones() {
        try {
            List<Cupon> cupones = cuponService.obtenerTodosLosCupones();
            if (cupones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron cupones");
            }

            List<EntityModel<Cupon>> cuponesConLinks = cupones.stream()
                    .map(cuponAssembler::toModel)
                    .collect(Collectors.toList());

            CollectionModel<EntityModel<Cupon>> model = CollectionModel.of(cuponesConLinks);
            model.add(linkTo(methodOn(CuponController.class).obtenerTodosLosCupones()).withSelfRel());
            model.add(linkTo(methodOn(CuponController.class).crearCupon(null)).withRel("crear-cupon"));

            return ResponseEntity.ok(model);
        } catch (Exception e) {
            log.error("Error al obtener cupones: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los cupones: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener cupón por ID", description = "Obtiene un cupón específico según su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cupón encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = Cupon.class))),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Cupón no encontrado con ID: <id>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al obtener el cupón: <error_message>")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCuponPorId(
            @Parameter(description = "ID del cupón", required = true, example = "1")
            @PathVariable Long id) {
        try {
            return cuponService.obtenerCuponPorId(id)
                    .map(cupon -> ResponseEntity.ok(cuponAssembler.toModel(cupon)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error al obtener cupón con ID {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("Error al procesar la solicitud");
        }
    }

    @Operation(summary = "Crear nuevo cupón", description = "Registra un nuevo cupón en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cupón creado exitosamente",
            content = @Content(schema = @Schema(implementation = Cupon.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear el cupón: <error_message>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear el cupón: <error_message>")))
    })
    @PostMapping
    public ResponseEntity<?> crearCupon(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del cupón a crear",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Cupon.class),
                    examples = @ExampleObject(
                        name = "EjemploCrearCupon",
                        summary = "Ejemplo de creación de cupón",
                        value = """
                            {
                                "codigo": "DESCUENTO20",
                                "descuento": 20.0,
                                "estado": "ACTIVO"
                            }
                            """
                    )
                )
            )
            @RequestBody Cupon cupon) {
        try {
            Cupon nuevoCupon = cuponService.crearCupon(cupon);
            return ResponseEntity.created(
                    linkTo(methodOn(CuponController.class).obtenerCuponPorId(nuevoCupon.getId())).toUri()
            ).body(cuponAssembler.toModel(nuevoCupon));
        } catch (Exception e) {
            log.error("Error al crear cupón: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el cupón: " + e.getMessage());
        }
    }

    @Operation(summary = "Canjear cupón", description = "Canjea un cupón cambiando su estado a USADO")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cupón canjeado exitosamente",
            content = @Content(schema = @Schema(implementation = String.class),
            examples = @ExampleObject(value = "Cupón canjeado exitosamente"))),
        @ApiResponse(responseCode = "400", description = "Cupón no disponible para canje",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "El cupón no está disponible para canje"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al canjear el cupón: <error_message>")))
    })
    @PutMapping("/canjear/{codigo}")
    public ResponseEntity<?> canjearCupon(
            @Parameter(description = "Código del cupón a canjear", required = true, example = "DESCUENTO20")
            @PathVariable String codigo) {
        try {
            boolean canjeExitoso = cuponService.canjearCupon(codigo);
            if (canjeExitoso) {
                return ResponseEntity.ok("Cupón canjeado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El cupón no está disponible para canje");
        } catch (Exception e) {
            log.error("Error al canjear cupón {}: {}", codigo, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al canjear el cupón: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón del sistema según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cupón eliminado exitosamente",
            content = @Content(schema = @Schema(implementation = String.class),
            examples = @ExampleObject(value = "Cupón eliminado correctamente"))),
        @ApiResponse(responseCode = "404", description = "Cupón no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Cupón no encontrado con ID: <id>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al eliminar el cupón: <error_message>")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCupon(
            @Parameter(description = "ID del cupón a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            cuponService.eliminarCupon(id);
            return ResponseEntity.ok("Cupón eliminado correctamente");
        } catch (Exception e) {
            log.error("Error al eliminar cupón {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el cupón: " + e.getMessage());
        }
    }
}