package cl.duoc.pedido.service;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Override
    public Pedido crear(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido actualizar(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = obtenerPorId(id);

        pedidoExistente.setClienteId(pedidoActualizado.getClienteId());
        pedidoExistente.setFecha(pedidoActualizado.getFecha());
        pedidoExistente.setTotal(pedidoActualizado.getTotal());
        pedidoExistente.setEstado(pedidoActualizado.getEstado());

        return pedidoRepository.save(pedidoExistente);
    }
}
