package cl.duoc.perfulandia.envio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.perfulandia.envio.assembler.EnvioModelAssembler;
import cl.duoc.perfulandia.envio.model.Envio;
import cl.duoc.perfulandia.envio.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/envio")
@Tag(name = "Envios", description = "Endpoints de los envios de Perfulandia")
public class EnvioController {

    @Autowired
    private EnvioService envioservice;

    @Autowired
    private EnvioModelAssembler assembler;

    @PostMapping
    @Operation(summary = "Guardar Envio", description = "Guarda un nuevo envio en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envio creado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Envio creado exitosamente"))),
        @ApiResponse(responseCode = "404", description = "Error al registrar el envio",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error al registrar envio"))),
    })
    public ResponseEntity<?> Guardar(@RequestBody Envio enviosave){
        try {
            Envio envionuevo = envioservice.Guardar(enviosave);
            return ResponseEntity.ok(assembler.toModel(envionuevo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud");
        }
    }

    @GetMapping
    @Operation(summary = "Consultar todos los envios", description = "Obtiene una lista de todos los envios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de envios obtenida exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Envio.class))),
        @ApiResponse(responseCode = "404", description = "No hay envios registrados",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "No hay envios registrados"))),
    })
    public ResponseEntity<?> ConsultarTodos(){
        List<Envio> envios = envioservice.ConsultarEnvioTodos();
        if (envios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay envios registrados");
        } else {
            return ResponseEntity.ok(assembler.toCollectionModel(envios));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar envio por ID", description = "Obtiene un envio específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envio encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Envio.class))),   
        @ApiResponse(responseCode = "404", description = "Envio no encontrado",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Envio no existe")))
    })
    public ResponseEntity<?> ConsultarEnvio(@PathVariable Long id){
        try {
            Envio enviobuscado = envioservice.ConsultarEnvio(id);
            return ResponseEntity.ok(assembler.toModel(enviobuscado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envio no existe");
        }
    }

    @PostMapping
    @Operation(summary = "Actualizar envio", description = "Actualiza un envio existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envio actualizado exitosamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Envio.class))),
        @ApiResponse(responseCode = "400", description = "Error en la solicitud",
            content = @Content(mediaType = "application/json",
            schema = @Schema(type = "string", example = "Error en la solicitud")))
    })
    public ResponseEntity<?> ConsultarEnvio(@PathVariable Long id, @RequestBody Envio envio){
        try {
            Envio envioedit = envioservice.ConsultarEnvio(id);

            envioedit.setDireccionEntrega(envio.getDireccionEntrega());
            envioedit.setEstado(envio.getEstado());

            envioservice.Guardar(envioedit);
            return ResponseEntity.ok(assembler.toModel(envioedit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud");
        }
    }

}
