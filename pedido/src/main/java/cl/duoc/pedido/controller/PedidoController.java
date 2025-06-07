package cl.duoc.pedido.controller;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.service.PedidoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        if(pedido.getClienteId() == null || pedido.getFecha() == null || 
        pedido.getTotal() == null || pedido.getEstado() == null) {
            return ResponseEntity.badRequest().build();
        }
        Pedido nuevoPedido = pedidoService.crear(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedidoActualizado) {
        return ResponseEntity.ok(pedidoService.actualizar(id, pedidoActualizado));
    }
}
