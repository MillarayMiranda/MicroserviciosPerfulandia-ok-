package cl.duoc.pedido.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.services.PedidoService;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<?> listarPedidos{
        List<Pedido> pedidos = pedidoService.obtenerTodosPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran pedidos registrados");
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    
    


  




}

// #### Endpoints
// - POST /pedidos
// - GET /pedidos/usuario/{id}
// - GET /pedidos/{id}
// - PUT /pedidos/{id}/estado