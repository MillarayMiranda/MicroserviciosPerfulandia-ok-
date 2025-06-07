package cl.duoc.pedido.service;

import cl.duoc.pedido.model.Pedido;
import java.util.List;

public interface PedidoService {
    List<Pedido> obtenerTodos();
    Pedido obtenerPorId(Long id);
    Pedido crear(Pedido pedido);
    Pedido actualizar(Long id, Pedido pedido);
}
