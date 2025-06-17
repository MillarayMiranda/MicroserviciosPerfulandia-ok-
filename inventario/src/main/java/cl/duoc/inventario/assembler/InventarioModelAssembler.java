package cl.duoc.inventario.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import cl.duoc.inventario.controller.InventarioController;
import cl.duoc.inventario.model.Inventario;

@Component
public class InventarioModelAssembler  implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(
            inventario,
            linkTo(methodOn(InventarioController.class).getInventario(null)).withRel("Lista los inventarios"),
            linkTo(methodOn(InventarioController.class).getById(inventario.getId())).withRel("Obtiene un inventario por ID"),
            linkTo(methodOn(InventarioController.class).create(inventario)).withRel("Crea un nuevo inventario"),
            linkTo(methodOn(InventarioController.class).update(inventario.getId(), inventario)).withRel("Actualiza un inventario"),
            linkTo(methodOn(InventarioController.class).delete(inventario.getId())).withRel("Elimina un inventario por ID")
        );
    }

}
