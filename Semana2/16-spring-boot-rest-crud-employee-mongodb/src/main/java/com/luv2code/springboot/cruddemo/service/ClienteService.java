package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Cliente;
import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface ClienteService {
	 List<Cliente> obtenerTodos();

	 Cliente obtenerPorId(String id);

	 Cliente guardar(Cliente cliente);

	 void desactivarPorId(String id);
	 
	 Cliente obtenerPorCorreo(String email);
	 
	 List<Pedido> obtenerHistorialPedidos(String clienteId);
	 
	 void borrar(String id);
}
