package com.duoc.sucursal.controller;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.services.SucursalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    @Autowired
    private SucursalServices sucursalService;

    @GetMapping
    public ResponseEntity<?> obtenerTodasSucursales() {
        List<Sucursal> sucursales = sucursalService.obtenerTodasSucursales();
        if(sucursales.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran sucursales registradas");
        } else {
            return ResponseEntity.ok(sucursales);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerSucursalPorId(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.obtenerSucursalPorId(id);
            return ResponseEntity.ok(sucursal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> crearSucursal(@RequestBody Sucursal sucursal) {
        try {
            Sucursal nuevaSucursal = sucursalService.crearSucursal(sucursal);
            return ResponseEntity.ok(nuevaSucursal);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar sucursal");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        try {
            Sucursal sucursalActualizada = sucursalService.actualizarSucursal(id, sucursal);
            return ResponseEntity.ok(sucursalActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no existe");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSucursal(@PathVariable Long id) {
        try {
            sucursalService.eliminarSucursal(id);
            return ResponseEntity.status(HttpStatus.OK).body("Sucursal eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal inexistente");
        }
    }
}