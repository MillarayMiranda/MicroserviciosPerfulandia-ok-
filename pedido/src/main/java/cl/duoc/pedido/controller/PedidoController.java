package cl.duoc.pedido.controller;

import javax.annotation.processing.Generated;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.pedido.model.Pedido;
import cl.duoc.pedido.services.PedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }                           

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    


}
