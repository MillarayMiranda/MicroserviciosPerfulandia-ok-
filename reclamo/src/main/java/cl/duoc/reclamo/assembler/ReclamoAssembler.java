package cl.duoc.reclamo.assembler;

import cl.duoc.reclamo.controller.ReclamoController;
import cl.duoc.reclamo.model.Reclamo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReclamoAssembler implements RepresentationModelAssembler<Reclamo, EntityModel<Reclamo>> {

    @Override
    public EntityModel<Reclamo> toModel(Reclamo reclamo) {
        return EntityModel.of(reclamo,
                linkTo(methodOn(ReclamoController.class).obtenerPorId(reclamo.getId())).withSelfRel(),
                linkTo(methodOn(ReclamoController.class).obtenerTodos()).withRel("todos-los-reclamos"),
                linkTo(methodOn(ReclamoController.class).eliminarReclamo(reclamo.getId())).withRel("eliminar-reclamo")
        );
    }
}
