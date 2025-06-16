package com.duoc.sucursal.assembler;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.services.SucursalServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursales")
@Tag(name = "Sucursales", description = "Endpoints de las sucursales de Perfulandia")
public class SucursalController {

    @Autowired
    private SucursalServices sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene una lista de todas las sucursales registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "No se encuentran sucursales registradas",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No se encuentran sucursales registradas")))
    })
    public ResponseEntity<?> obtenerTodasSucursales() {
        List<Sucursal> sucursales = sucursalService.obtenerTodasSucursales();
        if(sucursales.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran sucursales registradas");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(sucursales));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Obtiene una sucursal específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Sucursal no encontrada")))
    })
    public ResponseEntity<?> obtenerSucursalPorId(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.obtenerSucursalPorId(id);
            return ResponseEntity.ok(assembler.toModel(sucursal));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada");
        }
    }

    @PostMapping
    @Operation(summary = "Crear sucursal", description = "Registra una nueva sucursal")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "400", description = "Error en los datos de la sucursal",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error en los datos de la sucursal"))),
    })
    public ResponseEntity<?> crearSucursal(@RequestBody Sucursal sucursal) {
        try {
            Sucursal nuevaSucursal = sucursalService.crearSucursal(sucursal);
            return ResponseEntity.ok(assembler.toModel(nuevaSucursal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar sucursal");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sucursal", description = "Actualiza los datos de una sucursal existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Sucursal.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no existe",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Sucursal no existe")))
    })
    public ResponseEntity<?> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalActualizada = sucursalService.actualizarSucursal(id, sucursal);
            return ResponseEntity.ok(assembler);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no existe");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sucursal", description = "Elimina una sucursal por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal eliminada exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Sucursal eliminada"))),
        @ApiResponse(responseCode = "404", description = "Sucursal inexistente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Sucursal inexistente")))
    })
    public ResponseEntity<String> eliminarSucursal(@PathVariable Long id) {
        try {
            sucursalService.eliminarSucursal(id);
            return ResponseEntity.status(HttpStatus.OK).body("Sucursal eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal inexistente");
        }
    }
}