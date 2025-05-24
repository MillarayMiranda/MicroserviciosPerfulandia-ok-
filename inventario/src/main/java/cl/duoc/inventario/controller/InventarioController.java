package cl.duoc.inventario.controller;

import cl.duoc.inventario.model.Inventario;
import cl.duoc.inventario.services.InventarioServices;
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
public class InventarioController {

    private final InventarioServices inventarioService;

    public InventarioController(InventarioServices inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Nuevo endpoint para buscar por ID de inventario
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getById(
            @PathVariable @Positive(message = "El ID debe ser un número positivo") Long id) {
        Inventario inventario = inventarioService.findById(id);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
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
    public ResponseEntity<Inventario> getByProductoId(
            @PathVariable @Positive(message = "El ID de producto debe ser un número positivo") Long id) {
        Inventario inventario = inventarioService.findByProductoId(id);
        if (inventario != null) {
            return ResponseEntity.ok(inventario);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
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