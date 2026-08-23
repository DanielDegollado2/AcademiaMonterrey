package com.luv2code.springboot.cruddemo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luv2code.springboot.cruddemo.entity.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
	Optional<Cliente> findByCorreo(String correo);
}
