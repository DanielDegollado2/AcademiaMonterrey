package com.luv2code.springboot.cruddemo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.cruddemo.entity.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {
	Optional<Factura> findByPedidoId(int pedidoId);
}
