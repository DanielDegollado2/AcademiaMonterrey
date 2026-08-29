package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.cruddemo.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
	List<Pedido> findByClienteId(int clienteId);
}
