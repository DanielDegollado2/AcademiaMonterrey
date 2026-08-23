package com.luv2code.springboot.cruddemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facturas")
public class Factura {
	@Id
	private String id;
	
	private Long numeroFactura;
	
	private Double subtotal;
	
	private Double impuestos;
	
	private Double total;
	
	private String pedidoId;

	public Factura() {
		
	}
	
	public Factura(Long numeroFactura, double subtotal, double impuestos, double total, String pedidoId) {
		super();
		this.numeroFactura = numeroFactura;
		this.subtotal = subtotal;
		this.impuestos = impuestos;
		this.total = total;
		this.pedidoId = pedidoId;
	}

	public String getId() {
		return id;
	}

	public Long getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getPedido() {
		return pedidoId;
	}

	public void setPedido(String pedidoId) {
		this.pedidoId = pedidoId;
	}
	
	
}

