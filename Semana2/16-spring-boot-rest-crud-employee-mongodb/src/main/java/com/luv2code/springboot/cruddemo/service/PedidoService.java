package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.EstadoPedido;
import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface PedidoService {
	 List<Pedido> obtenerTodos();

	 Pedido obtenerPorId(String id);

	 Pedido crearPedido(Pedido pedido);

	 Pedido cambiarEstado(String id, EstadoPedido nuevoEstado);
	 
	 List<Pedido> obtenerPorClienteId(String clienteId);
	 
	 void borrar(String id);
}
