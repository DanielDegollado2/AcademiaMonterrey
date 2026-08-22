package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Cliente;
import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface ClienteService {
	 List<Cliente> obtenerTodos();

	 Cliente obtenerPorId(int id);

	 Cliente guardar(Cliente cliente);

	 void desactivarPorId(int id);
	 
	 Cliente obtenerPorCorreo(String email);
	 
	 List<Pedido> obtenerHistorialPedidos(int clienteId);
	 
	 void borrar(int id);
}
