package cl.duoc.pedido.services;

import java.lang.foreign.Linker.Option;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.repository.PedidoRepository;

@Service
public class PedidoServiceImp implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido crearPedido(Pedido pedido) {
        // Aquí puedes implementar la lógica para crear un pedido
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> obtenerTodosPedidos() {
        // Aquí puedes implementar la lógica para obtener todos los pedidos
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido obtenerPedidoPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        // Aquí puedes implementar la lógica para obtener un pedido por su ID
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    @Override
    
    
    // Aquí puedes implementar los métodos de PedidoService
    // Por ejemplo, si tienes un método para crear un pedido:
    // @Override
    // public Pedido crearPedido(Pedido pedido) {
    //     // Lógica para crear un pedido
    //     return pedidoRepository.save(pedido);
    // }

    // Otros métodos como obtenerPedidos, actualizarPedido, etc.

}
