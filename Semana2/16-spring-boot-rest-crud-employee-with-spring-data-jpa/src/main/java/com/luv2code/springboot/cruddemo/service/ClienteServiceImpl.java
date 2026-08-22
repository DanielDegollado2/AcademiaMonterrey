package com.luv2code.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.cruddemo.dao.ClienteRepository;
import com.luv2code.springboot.cruddemo.dao.PedidoRepository;
import com.luv2code.springboot.cruddemo.entity.Cliente;
import com.luv2code.springboot.cruddemo.entity.Pedido;

@Service
public class ClienteServiceImpl implements ClienteService {

	private ClienteRepository clienteRepository;
	private PedidoRepository pedidoRepository;
	
	@Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
		this.clienteRepository = clienteRepository;
		this.pedidoRepository = pedidoRepository;
    }
	
	@Override
	public List<Cliente> obtenerTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente obtenerPorId(int id) {
		Optional<Cliente> result = clienteRepository.findById(id);

        Cliente cliente = null;

        if (result.isPresent()) {
        	cliente = result.get();
        }
        else {
            throw new RuntimeException("No se encontro al cliente con la id - " + id);
        }

        return cliente;
	}

	@Override
	public Cliente guardar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public void desactivarPorId(int id) {
		Optional<Cliente> result = clienteRepository.findById(id);

        if (result.isPresent()) {
        	Cliente cliente = result.get(); 
            cliente.setActivo(false);
            clienteRepository.save(cliente);
        }
        else {
            throw new RuntimeException("No se encontro al cliente con la id - " + id);
        }
	}

	@Override
	public Cliente obtenerPorCorreo(String correo) {
		Optional<Cliente> result = clienteRepository.findByCorreo(correo);

		Cliente cliente = null;
		
        if (result.isPresent()) {
        	cliente = result.get(); 
        }
        else {
            throw new RuntimeException("No se encontro al cliente con el correo - " + correo);
        }
		return cliente;
	}

	@Override
	public List<Pedido> obtenerHistorialPedidos(int clienteId) {
		 if (!clienteRepository.existsById(clienteId)) {
			 throw new RuntimeException("No se encontro al cliente con el id " + clienteId);
		 }
		 
		 return pedidoRepository.findByClienteId(clienteId);
	}

	@Override
	public void borrar(int id) {
		clienteRepository.deleteById(null);;
	}
	
}
