package cl.duoc.cupon.assembler;

import cl.duoc.cupon.controller.CuponController;
import cl.duoc.cupon.model.Cupon;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CuponAssembler implements RepresentationModelAssembler<Cupon, EntityModel<Cupon>> {

    @Override
    public EntityModel<Cupon> toModel(Cupon cupon) {
        return EntityModel.of(cupon,
            linkTo(methodOn(CuponController.class).obtenerCuponPorId(cupon.getId())).withSelfRel(),
            linkTo(methodOn(CuponController.class).obtenerTodosLosCupones()).withRel("todos-los-cupones"),
            linkTo(methodOn(CuponController.class).canjearCupon(cupon.getCodigo())).withRel("canjear"),
            linkTo(methodOn(CuponController.class).eliminarCupon(cupon.getId())).withRel("eliminar")
        );
    }
}