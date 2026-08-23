package com.luv2code.springboot.cruddemo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.EstadoPedido;
import com.luv2code.springboot.cruddemo.entity.Pedido;
import com.luv2code.springboot.cruddemo.service.PedidoService;


@RestController
@RequestMapping("/api")
public class PedidoRestController {
	private PedidoService pedidoService;
	
	@Autowired
	public PedidoRestController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping("/pedidos")
    public List<Pedido> obtenerTodos() {
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/pedidos/{pedidoId}")
    public Pedido obtenerPedidoPorId(@PathVariable String pedidoId) {

    	Pedido pedido = pedidoService.obtenerPorId(pedidoId);

        if (pedido == null) {
            throw new RuntimeException("No se encontro al pedido con la id - " + pedidoId);
        }

        return pedido;
    }
    
    @GetMapping("/pedidos/cliente/{clienteId}")
    public List<Pedido> obtenerPedidoPorClienteId(@PathVariable String clienteId) {

    	List<Pedido> pedido = pedidoService.obtenerPorClienteId(clienteId);

        return pedido;
    }
    
    @PostMapping("/pedidos")
    public Pedido agregarPedido(@RequestBody Pedido pedido) {

    	Pedido dbPedido = pedidoService.crearPedido(pedido);

        return dbPedido;
    }
    
    @PatchMapping("/pedidos/{id}/estado")
    public Pedido cambiarEstadoPedido(@PathVariable String id, @RequestBody Map<String, String> body) {
        String estadoTexto = body.get("estado");
        EstadoPedido nuevoEstado = EstadoPedido.valueOf(estadoTexto.toUpperCase());

        return pedidoService.cambiarEstado(id, nuevoEstado);
    }
    
    @DeleteMapping("/pedidos/{pedidoId}")
    public String borrarPedido(@PathVariable String pedidoId) {

        Pedido tempPedido = pedidoService.obtenerPorId(pedidoId);

        if (tempPedido == null) {
            throw new RuntimeException("No se encontro un pedido con la id - " + pedidoId);
        }

        pedidoService.borrar(pedidoId);

        return "Se borro el pedido con la id - " + pedidoId;
    }
}
