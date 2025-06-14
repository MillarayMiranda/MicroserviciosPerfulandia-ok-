package cl.duoc.pedido.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import cl.duoc.pedido.controller.PedidoController;
import cl.duoc.pedido.model.Pedido;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;


@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(
            pedido,
            linkTo(methodOn(PedidoController.class).obtenerTodosLosPedidos()).withRel("Lista los pedidos"),
            linkTo(methodOn(PedidoController.class).obtenerPedidoPorId(pedido.getId())).withRel("Obtiene un pedido por ID"),
            linkTo(methodOn(PedidoController.class).crearPedido(pedido)).withRel("Crea un nuevo pedido"),
            linkTo(methodOn(PedidoController.class).actualizarPedido(pedido.getId(), pedido)).withRel("Actualiza un pedido")
        );
    }

}
