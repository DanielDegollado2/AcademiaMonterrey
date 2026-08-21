package com.luv2code.springboot.cruddemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="factura")
public class Factura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="numero_factura")
	private Long numeroFactura;
	
	@Column(name="subtotal")
	private Double subtotal;
	
	@Column(name="impuestos")
	private Double impuestos;
	
	@Column(name="total")
	private Double total;
	
	@OneToOne(optional = false)
	@JoinColumn(name= "id_pedido", unique = true)
	private Pedido pedido;

	public Factura() {
		
	}
	
	public Factura(Long numeroFactura, double subtotal, double impuestos, double total, Pedido pedido) {
		super();
		this.numeroFactura = numeroFactura;
		this.subtotal = subtotal;
		this.impuestos = impuestos;
		this.total = total;
		this.pedido = pedido;
	}

	public int getId() {
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	
}
