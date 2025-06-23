package cl.duoc.perfulandia.envio.assembler;

import javax.swing.text.html.parser.Entity;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import cl.duoc.perfulandia.envio.controller.EnvioController;
import cl.duoc.perfulandia.envio.model.Envio;
import net.datafaker.shaded.snakeyaml.representer.Represent;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(
            envio,
            linkTo(methodOn(EnvioController.class).ConsultarTodos()).withRel("Lista los envios"),
            linkTo(methodOn(EnvioController.class).ConsultarEnvio(envio.getId())).withRel("Obtiene un envio por ID"),
            linkTo(methodOn(EnvioController.class).Guardar(envio)).withRel("Crea un nuevo envio"),
            linkTo(methodOn(EnvioController.class).ConsultarEnvio(envio.getId(), envio)).withRel("Actualiza un envio"),
        );
    }

}
