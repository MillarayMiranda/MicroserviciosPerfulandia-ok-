package cl.duoc.pedido.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.pedido.services.PedidoService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public Response listarPedidos(@RequestParam(required = false) String estado) {
        if (estado != null) {
            return pedidoService.obtenerPedidosPorEstado(estado);
        } else {
            return pedidoService.obtenerTodosPedidos();
        }
    }

    
    


  




}

// #### Endpoints
// - POST /pedidos
// - GET /pedidos/usuario/{id}
// - GET /pedidos/{id}
// - PUT /pedidos/{id}/estado