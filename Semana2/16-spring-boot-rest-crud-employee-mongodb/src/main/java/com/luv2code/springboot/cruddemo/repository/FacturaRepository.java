package com.luv2code.springboot.cruddemo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luv2code.springboot.cruddemo.entity.Factura;

public interface FacturaRepository extends MongoRepository<Factura, String> {
	Optional<Factura> findByPedidoId(int pedidoId);
}
