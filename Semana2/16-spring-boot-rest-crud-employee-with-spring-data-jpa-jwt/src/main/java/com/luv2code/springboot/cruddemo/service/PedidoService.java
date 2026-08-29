package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.EstadoPedido;
import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface PedidoService {
	 List<Pedido> obtenerTodos();

	 Pedido obtenerPorId(int id);

	 Pedido crearPedido(Pedido pedido);

	 Pedido cambiarEstado(int id, EstadoPedido nuevoEstado);
	 
	 List<Pedido> obtenerPorClienteId(int clienteId);
	 
	 void borrar(int id);
}
