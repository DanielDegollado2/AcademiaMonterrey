package com.luv2code.springboot.cruddemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.cruddemo.entity.Factura;
import com.luv2code.springboot.cruddemo.entity.Pedido;
import com.luv2code.springboot.cruddemo.repository.FacturaRepository;
import com.luv2code.springboot.cruddemo.repository.PedidoRepository;

@Service
public class FacturaServiceImpl implements FacturaService {
	private FacturaRepository facturaRepository;
	private PedidoRepository pedidoRepository;
	
	@Autowired
	public FacturaServiceImpl(FacturaRepository facturaRepository, PedidoRepository pedidoRepository) {
		this.facturaRepository = facturaRepository;
		this.pedidoRepository = pedidoRepository;
	}

	@Override
	public List<Factura> obtenerTodos() {
		return facturaRepository.findAll();
	}
	
	@Override
	public Factura obtenerPorId(String id) {
		Optional<Factura> result = facturaRepository.findById(id);

        Factura factura = null;

        if (result.isPresent()) {
        	factura = result.get();
        }
        else {
            throw new RuntimeException("No se encontro la factura con la id - " + id);
        }

        return factura;
	}

	@Override
	public Factura obtenerPorPedidoId(String pedidoId) {
		Optional<Factura> result = facturaRepository.findByPedidoId(pedidoId);

		Factura factura = null;
		
        if (result.isPresent()) {
        	factura = result.get(); 
        }
        else {
            throw new RuntimeException("No se encontro la factura con la id de pedido- " + pedidoId);
        }
		return factura;
	}

	@Override
	public Factura generarFactura(String pedidoId) {
		Optional<Pedido> result = pedidoRepository.findById(pedidoId);
		
		if (result.isPresent()) {
			if (facturaRepository.findByPedidoId(pedidoId).isPresent()) {
		        throw new RuntimeException("El pedido ya tiene una factura generada");
		    }
			
			Pedido pedido = result.get();
			
			double subtotal = pedido.getTotal();
			double tasaImpuesto = 0.16;
			double impuestos = subtotal * tasaImpuesto;
			double total = subtotal + impuestos;

			Factura factura = new Factura();
			factura.setNumeroFactura(System.currentTimeMillis());
			factura.setSubtotal(subtotal);
			factura.setImpuestos(impuestos);
			factura.setTotal(total);
			factura.setPedidoId(pedidoId);
			
			return facturaRepository.save(factura);
	    }
	    else {
	        throw new RuntimeException("No se encontro el pedido con la id- " + pedidoId);
	    }
	}

	@Override
	public void borrar(String id) {
		facturaRepository.deleteById(id);
	}

}
