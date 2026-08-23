package com.luv2code.springboot.cruddemo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.cruddemo.entity.EstadoPedido;
import com.luv2code.springboot.cruddemo.entity.Pedido;
import com.luv2code.springboot.cruddemo.repository.PedidoRepository;


@Service
public class PedidoServiceImpl implements PedidoService {
	private PedidoRepository pedidoRepository;
	
	private FacturaService facturaService;
	
	@Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, FacturaService facturaService) {
		this.pedidoRepository = pedidoRepository;
		this.facturaService = facturaService;	
    }
	
	@Override
	public List<Pedido> obtenerTodos() {
		return pedidoRepository.findAll();
	}

	@Override
	public Pedido obtenerPorId(String id) {
		Optional<Pedido> result = pedidoRepository.findById(id);

        Pedido pedido = null;

        if (result.isPresent()) {
        	pedido = result.get();
        }
        else {
            throw new RuntimeException("No se encontro el pedido con la id - " + id);
        }

        return pedido;
	}

	@Override
	public Pedido crearPedido(Pedido pedido) {
		pedido.setEstado(EstadoPedido.PENDIENTE);
	    pedido.setFechaCreacion(LocalDate.now());
	    return pedidoRepository.save(pedido);
	}

	@Override
	@Transactional
	public Pedido cambiarEstado(String id, EstadoPedido nuevoEstado) {
		Optional<Pedido> result = pedidoRepository.findById(id);

        Pedido pedido = null;

        if (result.isPresent()) {
        	pedido = result.get();
        	pedido.setEstado(nuevoEstado);
        	Pedido pedidoActualizado = pedidoRepository.save(pedido);
        	
        	if (nuevoEstado == EstadoPedido.PAGADO) {
                facturaService.generarFactura(id);
            }
        	
        	return pedidoActualizado;
        }
        else {
            throw new RuntimeException("No se encontro el pedido con la id - " + id);
        }
	}

	@Override
	public List<Pedido> obtenerPorClienteId(String clienteId) {
		return pedidoRepository.findByClienteId(clienteId);
	}

	@Override
	public void borrar(String id) {
		pedidoRepository.deleteById(id);
		
	}
}
