package cl.duoc.producto.assembler;

import org.springframework.stereotype.Component;
import cl.duoc.producto.controller.ProductoController;
import cl.duoc.producto.model.Producto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
   
    @Override
    public EntityModel<Producto> toModel (Producto p){
        return EntityModel.of(
            p,
            linkTo(methodOn(ProductoController.class).listarProductos()).withRel("Lista los productos"),
            linkTo(methodOn(ProductoController.class).obtenerProductoPorId(p.getId())).withRel("Obtiene un producto por ID"),
            linkTo(methodOn(ProductoController.class).crearProducto(p)).withRel("Crea un nuevo producto"),
            linkTo(methodOn(ProductoController.class).actualizarProducto(p.getId(), p)).withRel("Actualiza un producto"),
            linkTo(methodOn(ProductoController.class).eliminarProducto(p.getId())).withRel("Elimina un producto por ID")
        ); 
    }

}