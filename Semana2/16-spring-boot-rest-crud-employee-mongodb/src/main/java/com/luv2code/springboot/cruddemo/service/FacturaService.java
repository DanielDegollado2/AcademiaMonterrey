package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Factura;

public interface FacturaService {
	List<Factura> obtenerTodos();
	Factura obtenerPorId(String id);
	Factura obtenerPorPedidoId(String pedidoId);
	Factura generarFactura(String pedidoId);
	void borrar(String id);
}