package cl.duoc.inventario.controller;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.services.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@Validated
@Tag(name = "Inventario", description = "Endpoints para gestionar el inventario de productos en Perfulandia")

public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Nuevo endpoint para buscar por ID de inventario
    @GetMapping("/{id}")
    @Operation (summary = "Obtener inventario por ID", description = "Obtiene un inventario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Inventario no encontrado")))
    })
    public ResponseEntity<Inventario> getById(
            @PathVariable @Positive(message = "El ID debe ser un número positivo") Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Obtener inventario", description = "Obtiene una lista de inventarios filtrados por sucursal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentran inventarios para la sucursal especificada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encuentran inventarios para la sucursal especificada")))
    })
    public ResponseEntity<List<Inventario>> getInventario(
            @RequestParam(required = false) Long sucursalId) {
        
        if (sucursalId != null) {
            List<Inventario> inventarios = inventarioService.findBySucursalId(sucursalId);
            if (inventarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(inventarios);
        } else {
            List<Inventario> inventarios = inventarioService.findAll();
            return ResponseEntity.ok(inventarios);
        }
    }

    @GetMapping("/producto/{id}")
    @Operation(summary = "Obtener inventario por ID de producto", description = "Obtiene el inventario de un producto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado para el producto especificado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Inventario no encontrado para el producto especificado")))
    })
    public ResponseEntity<Inventario> getByProductoId(
            @PathVariable @Positive(message = "El ID de producto debe ser un número positivo") Long id) {
        Inventario inventario = inventarioService.findByProductoId(id);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear inventario", description = "Registra un nuevo inventario de producto en Perfulandia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "400", description = "Error al crear el inventario",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al crear el inventario")))
    })
    public ResponseEntity<Inventario> create(
            @RequestBody @Valid Inventario inventario) {
        try {
            Inventario savedInventario = inventarioService.save(inventario);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInventario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar inventario", description = "Actualiza un inventario existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Inventario.class))),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado para el ID especificado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Inventario no encontrado para el ID especificado"))),
        @ApiResponse(responseCode = "400", description = "Error al actualizar el inventario",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al actualizar el inventario")))
    })
    public ResponseEntity<Inventario> update(
            @PathVariable @Positive(message = "El ID debe ser un número positivo") Long id,
            @RequestBody @Valid Inventario inventario) {
        
        try {
            Inventario updatedInventario = inventarioService.update(id, inventario);
            if (updatedInventario != null) {
                return ResponseEntity.ok(updatedInventario);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar inventario", description = "Elimina un inventario existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Inventario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado para el ID especificado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Inventario no encontrado para el ID especificado"))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el inventario")
    })
    public ResponseEntity<Void> delete(
        @PathVariable @Positive(message = "El ID debe ser un número positivo") Long id) {
        try {
            if (inventarioService.delete(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}