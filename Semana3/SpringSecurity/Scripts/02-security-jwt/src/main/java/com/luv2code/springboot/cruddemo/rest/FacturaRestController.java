package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Factura;
import com.luv2code.springboot.cruddemo.service.FacturaService;

@RestController
@RequestMapping("/api")
public class FacturaRestController {
	
	private FacturaService facturaService;

	@Autowired
	public FacturaRestController(FacturaService facturaService) {
		this.facturaService = facturaService;
	}
	
	@GetMapping("/facturas")
    public List<Factura> obtenerTodos() {
        return facturaService.obtenerTodos();
    }

    @GetMapping("/facturas/{facturaId}")
    public Factura obtenerFacturaPorId(@PathVariable int facturaId) {

    	Factura factura = facturaService.obtenerPorId(facturaId);

        if (factura == null) {
            throw new RuntimeException("No se encontro la factura con la id - " + facturaId);
        }

        return factura;
    }
    
    
    @GetMapping("/facturas/pedido/{pedidoId}")
    public Factura obtenerFacturaPorPedidoId(@PathVariable int pedidoId) {

    	Factura factura = facturaService.obtenerPorPedidoId(pedidoId);
    	
    	if (factura == null) {
            throw new RuntimeException("No se encontro la factura con la pedidoId - " + pedidoId);
        }

        return factura;
    }
    
    @DeleteMapping("/facturas/{facturaId}")
    public String borrarFactura(@PathVariable int facturaId) {

        Factura tempFactura = facturaService.obtenerPorId(facturaId);

        if (tempFactura == null) {
            throw new RuntimeException("No se encontro una factura con la id - " + facturaId);
        }

        facturaService.borrar(facturaId);

        return "Se borro la factura con la id - " + facturaId;
    }
	
}
