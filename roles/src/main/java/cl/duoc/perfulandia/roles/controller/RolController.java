package cl.duoc.perfulandia.roles.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.perfulandia.roles.model.Rol;
import cl.duoc.perfulandia.roles.services.RolService;




@RestController
@RequestMapping("/roles")
public class RolController {


    @Autowired
    private RolService rolservice;

    @GetMapping
    public ResponseEntity<?> ListarRoles(){
        List<Rol> rol = rolservice.ListarRoles();
        if (rol.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay roles creados");
        } else {
            return ResponseEntity.ok(rol);
        }
    }

    @GetMapping("/{idrol}")
    public ResponseEntity<?> ConsultarUnRol(@PathVariable Integer idrol){
        try {
            Rol rolconsultado = rolservice.ConsultarUnRol(idrol);
            return ResponseEntity.ok(rolconsultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este rol no existe");
        }
    }

    @PostMapping
    public ResponseEntity<?> Guardar(@RequestBody Rol rolcreate){
        try {
            Rol rolnuevo = rolservice.Guardar(rolcreate);
            return ResponseEntity.ok(rolnuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al crear rol");
        }
    }

    @DeleteMapping("/{idrol}")
    public ResponseEntity<String> Eliminar(@PathVariable Integer idrol){
        try {
            Rol rolbuscar = rolservice.ConsultarUnRol(idrol);
            rolservice.Eliminar(idrol);
            return ResponseEntity.status(HttpStatus.OK).body("Rol eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }
    }

    @PutMapping("/{idrol}")
    public ResponseEntity<?> EditarRol(@PathVariable Integer idrol, @RequestBody Rol rol){
        try {
            Rol roledit = rolservice.ConsultarUnRol(idrol);

            roledit.setNombreRol(rol.getNombreRol());

            rolservice.Guardar(roledit);
            return ResponseEntity.ok(roledit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no existe");
        }
    }

}
