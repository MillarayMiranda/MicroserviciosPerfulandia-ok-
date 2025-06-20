package cl.duoc.factura.controller;

import cl.duoc.factura.assembler.FacturaAssembler;
import cl.duoc.factura.dto.FacturaUsuarioDTO;
import cl.duoc.factura.dto.UsuarioDTO;
import cl.duoc.factura.model.Factura;
import cl.duoc.factura.service.FacturaService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/facturas")
@Tag(name = "Facturas", description = "Controlador para gestión de facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final FacturaAssembler facturaAssembler;

    public FacturaController(FacturaService facturaService, FacturaAssembler facturaAssembler) {
        this.facturaService = facturaService;
        this.facturaAssembler = facturaAssembler;
    }

    @Operation(summary = "Obtener todas las facturas", description = "Retorna una lista completa de todas las facturas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron facturas",
        content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encontraron facturas"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al obtener las facturas: <error_message>")))
    })
    @GetMapping
    public ResponseEntity<?> obtenerTodasLasFacturas() {
        try {
            List<Factura> facturas = facturaService.obtenerTodasFacturas();
            if (facturas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron facturas");
            }

            // CORRECTO: lista de EntityModel<Factura>
            List<EntityModel<Factura>> facturasConLinks = facturas.stream()
                    .map(facturaAssembler::toModel)
                    .collect(Collectors.toList());

            // CORRECTO: usar EntityModel<Factura> como tipo genérico
            CollectionModel<EntityModel<Factura>> model = CollectionModel.of(facturasConLinks);
            model.add(linkTo(methodOn(FacturaController.class).obtenerTodasLasFacturas()).withSelfRel());
            model.add(linkTo(methodOn(FacturaController.class).crearFactura(null)).withRel("crear-factura"));

            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las facturas: " + e.getMessage());
        }
    }

    @Operation(summary = "Obtener factura por ID", description = "Obtiene una factura específica según su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura encontrada exitosamente",
            content = @Content(schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Factura no encontrada con ID: <id>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al obtener la factura: <error_message>")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFactura(
            @Parameter(description = "ID de la factura", required = true, example = "1")
            @PathVariable Long id) {
        try {
            Factura factura = facturaService.obtenerFacturaPorId(id);
            return ResponseEntity.ok(facturaAssembler.toModel(factura));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la factura");
        }
    }

    @Operation(summary = "Crear una nueva factura", description = "Registra una nueva factura en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Factura creada exitosamente",
            content = @Content(schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear la factura: <error_message>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear la factura: <error_message>")))
    })
    @PostMapping
    public ResponseEntity<?> crearFactura(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la factura a crear",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = Factura.class),
                    examples = @ExampleObject(
                        name = "EjemploCrearFactura",
                        summary = "Ejemplo de creación de factura",
                        value = """
                            {
                                "pedidoId": 789,
                                "usuarioId": 101,
                                "fecha": "2023-12-15",
                                "total": 29990.99,
                                "metodoPago": "WEBPAY"
                            }
                            """
                    )
                )
            )
            @RequestBody Factura factura) {
        try {
            Factura nuevaFactura = facturaService.guardarFactura(factura);
            return ResponseEntity.created(
                    linkTo(methodOn(FacturaController.class).obtenerFactura(nuevaFactura.getId())).toUri()
            ).body(facturaAssembler.toModel(nuevaFactura));
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la factura: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Aquí puedes agregar los demás métodos (como actualizar, eliminar, obtener con usuario, etc.)

    @Operation(summary = "Actualizar factura", description = "Actualiza los datos de una factura existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Factura.class))),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "string", example = "Factura no encontrada con ID: <id>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "string", example = "Error al actualizar la factura: <error_message>")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarFactura(
            @Parameter(description = "ID de la factura a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados de la factura",
                required = true,
                content = @Content(schema = @Schema(implementation = Factura.class))
            )
            @RequestBody Factura facturaActualizada) {
        try {
            Factura factura = facturaService.actualizarFactura(id, facturaActualizada);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la factura: " + e.getMessage());
        }
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura del sistema según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Factura eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Factura no encontrada con ID: <id>"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al eliminar la factura: <error_message>")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFactura(
            @Parameter(description = "ID de la factura a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        try {
            facturaService.eliminarFactura(id);
            return ResponseEntity.ok("Factura eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la factura: " + e.getMessage());
        }
    }

    @Operation(
    summary = "Obtener factura junto al usuario",
    description = "Obtiene una factura por su ID e incluye los datos del usuario asociado")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Factura y usuario encontrados exitosamente",
            content = @Content(schema = @Schema(implementation = FacturaUsuarioDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró la factura o el usuario no está registrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "No se encontró la factura o el usuario no está registrado"))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(mediaType = "application/json",
                schema = @Schema(type = "string", example = "Error al obtener la factura con usuario: <error_message>")
            )
        )
    })
    @GetMapping("/factura-usuario/{id}")
    public ResponseEntity<?> obtenerFacturaConUsuario(
        @Parameter(description = "ID de la factura", required = true, example = "1")
        @PathVariable Long id
    ) {
        try {
            Factura factura = facturaService.obtenerFacturaPorId(id);
            UsuarioDTO usuario = facturaService.obtenerUsuario(factura.getUsuarioId());

            FacturaUsuarioDTO dto = new FacturaUsuarioDTO();
            dto.setIdFactura(factura.getId());
            dto.setFecha(factura.getFecha());
            dto.setTotal(factura.getTotal());
            dto.setNombreUsuario(usuario.getNombre());
            dto.setRutUsuario(usuario.getRut());
            dto.setEmailUsuario(usuario.getEmail());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontró la factura o el usuario no está registrado");
        }
    }

}
