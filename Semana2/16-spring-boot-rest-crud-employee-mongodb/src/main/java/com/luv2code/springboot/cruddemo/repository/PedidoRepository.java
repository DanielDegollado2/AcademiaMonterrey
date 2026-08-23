package com.luv2code.springboot.cruddemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface PedidoRepository extends MongoRepository<Pedido, String> {
	List<Pedido> findByClienteId(int clienteId);
}
