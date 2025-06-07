package cl.duoc.perfulandia.envio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.perfulandia.envio.model.Envio;
import cl.duoc.perfulandia.envio.service.EnvioService;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    @Autowired
    private EnvioService envioservice;

    @PostMapping
    public ResponseEntity<?> Guardar(@RequestBody Envio enviosave){
        try {
            Envio envionuevo = envioservice.Guardar(enviosave);
            return ResponseEntity.ok(envionuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud");
        }
    }

    @GetMapping
    public ResponseEntity<?> ConsultarTodos(){
        List<Envio> envios = envioservice.ConsultarEnvioTodos();
        if (envios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay envios registrados");
        } else {
            return ResponseEntity.ok(envios);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ConsultarEnvio(@PathVariable Long id){
        try {
            Envio enviobuscado = envioservice.ConsultarEnvio(id);
            return ResponseEntity.ok(enviobuscado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envio no existe");
        }
    }

    @PostMapping
    public ResponseEntity<?> ConsultarEnvio(@PathVariable Long id, @RequestBody Envio envio){
        try {
            Envio envioedit = envioservice.ConsultarEnvio(id);

            envioedit.setDireccionEntrega(envio.getDireccionEntrega());
            envioedit.setEstado(envio.getEstado());

            envioservice.Guardar(envioedit);
            return ResponseEntity.ok(envioedit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud");
        }
    }

}
