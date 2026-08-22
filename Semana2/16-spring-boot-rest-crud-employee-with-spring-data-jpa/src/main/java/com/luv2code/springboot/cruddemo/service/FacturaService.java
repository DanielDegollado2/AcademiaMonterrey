package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Factura;

public interface FacturaService {
	List<Factura> obtenerTodos();
	Factura obtenerPorId(int id);
	Factura obtenerPorPedidoId(int pedidoId);
	Factura generarFactura(int pedidoId);
	void borrar(int id);
}
