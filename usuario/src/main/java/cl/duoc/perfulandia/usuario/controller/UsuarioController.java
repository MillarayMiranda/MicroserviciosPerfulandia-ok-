package cl.duoc.perfulandia.usuario.controller;

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

import cl.duoc.perfulandia.usuario.DTO.RolDTO;
import cl.duoc.perfulandia.usuario.DTO.RolUsuarioDTO;
import cl.duoc.perfulandia.usuario.model.Usuario;
import cl.duoc.perfulandia.usuario.services.UsuarioService;




@RestController
@RequestMapping("/Usuario")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public ResponseEntity<?> ListarUsuarios(){
        List<Usuario> usuario = usuarioservice.BuscarUsuarios();
        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran usuarios registrados");
        } else {
            return ResponseEntity.ok(usuario);
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> BuscarUnUsuario(@PathVariable Long rut){
        try {
            Usuario usuariobuscado = usuarioservice.BuscarUnUsuario(rut);
            return ResponseEntity.ok(usuariobuscado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @GetMapping("/RolUsuario/{rut}")
    public ResponseEntity<?> DetalleRolUsuario(@PathVariable Long rut){
        try {
            Usuario usuariobuscado = usuarioservice.BuscarUnUsuario(rut);

            RolDTO usuarioRoles = usuarioservice.ConsultarRol(usuariobuscado.getRol());
            RolUsuarioDTO rolUsuario = new RolUsuarioDTO();

            rolUsuario.setRut(usuariobuscado.getRut());
            rolUsuario.setNombre(usuariobuscado.getNombre());
            rolUsuario.setCorreo(usuariobuscado.getCorreo());
            rolUsuario.setPassword(usuariobuscado.getPassword());
            rolUsuario.setNombreRol(usuarioRoles.getNombreRol());

            return ResponseEntity.ok(rolUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra Usuario o rol no existe");
        }
    }

    @PostMapping
    public ResponseEntity<?> Guardar(@RequestBody Usuario usuariosave){
        try {
            Usuario usuarionuevo = usuarioservice.Guardar(usuariosave);
            return ResponseEntity.ok(usuarionuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error al registrar usuario");
        }
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<String> Eliminar(@PathVariable Long rut){
        try {
            Usuario usuariobuscado = usuarioservice.BuscarUnUsuario(rut);
            usuarioservice.Eliminar(rut);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario inexistente");
        }
    }

    @PutMapping("/{rut}")
    public ResponseEntity<?> ActualizarUsuario(@PathVariable Long rut, @RequestBody Usuario usuario){
        try {
            Usuario usuarioedit = usuarioservice.BuscarUnUsuario(rut);

            usuarioedit.setNombre(usuario.getNombre());
            usuarioedit.setCorreo(usuario.getCorreo());
            usuarioedit.setPassword(usuario.getPassword());
            usuarioedit.setDireccion(usuario.getDireccion());
            usuarioedit.setTelefono(usuario.getTelefono());
            usuarioedit.setRol(usuario.getRol());

            usuarioservice.Guardar(usuarioedit);
            return ResponseEntity.ok(usuarioedit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no existe");
        }
    }

}
