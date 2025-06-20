package cl.duoc.factura.assembler;

import cl.duoc.factura.controller.FacturaController;
import cl.duoc.factura.model.Factura;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FacturaAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>> {

    @Override
    public EntityModel<Factura> toModel(Factura factura) {
        return EntityModel.of(factura,
            linkTo(methodOn(FacturaController.class).obtenerFactura(factura.getId())).withSelfRel(),
            linkTo(methodOn(FacturaController.class).obtenerTodasLasFacturas()).withRel("facturas"),
            linkTo(methodOn(FacturaController.class).actualizarFactura(factura.getId(), null)).withRel("actualizar"),
            linkTo(methodOn(FacturaController.class).eliminarFactura(factura.getId())).withRel("eliminar")
        );
    }
}