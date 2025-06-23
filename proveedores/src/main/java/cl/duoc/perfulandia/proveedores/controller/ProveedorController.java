package cl.duoc.perfulandia.proveedores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.perfulandia.proveedores.model.Proveedor;
import cl.duoc.perfulandia.proveedores.service.ProveedorService;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService provservice;

    @PostMapping
    public ResponseEntity<?> Guardar(@RequestBody Proveedor prov){
        try {
            Proveedor newProv = provservice.Guardar(prov);
            return ResponseEntity.ok(newProv);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar proveedor");
        }
    }

    @GetMapping
    public ResponseEntity<?> ListarProveedores(){
        List<Proveedor> provs = provservice.ListarProveedores();
        if (provs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sin proveedores registrados");
        } else {
            return ResponseEntity.ok(provs);
        }
    }
}
