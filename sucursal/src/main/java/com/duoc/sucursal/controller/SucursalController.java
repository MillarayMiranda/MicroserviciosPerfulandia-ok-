package com.duoc.sucursal.controller;

import com.duoc.sucursal.model.Sucursal;
import com.duoc.sucursal.services.SucursalServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {

    private final SucursalServices sucursalService;

    public SucursalController(SucursalServices sucursalService) {
        this.sucursalService = sucursalService;
    }

    @PostMapping
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nuevaSucursal = sucursalService.crearSucursal(sucursal);
        return new ResponseEntity<>(nuevaSucursal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Sucursal>> obtenerTodasSucursales() {
        List<Sucursal> sucursales = sucursalService.obtenerTodasSucursales();
        return new ResponseEntity<>(sucursales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable Long id) {
        Sucursal sucursal = sucursalService.obtenerSucursalPorId(id);
        return new ResponseEntity<>(sucursal, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Long id, @RequestBody Sucursal sucursal) {
        Sucursal sucursalActualizada = sucursalService.actualizarSucursal(id, sucursal);
        return new ResponseEntity<>(sucursalActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}