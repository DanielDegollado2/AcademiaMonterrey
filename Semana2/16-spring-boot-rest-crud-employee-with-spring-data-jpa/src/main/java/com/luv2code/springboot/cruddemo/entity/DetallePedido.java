package com.luv2code.springboot.cruddemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="detalle_pedido")
public class DetallePedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="precio_unitario")
	private Double precioUnitario;
	
	@Column(name="subtotal")
	private Double subtotal;
	
	@ManyToOne()
	@JoinColumn(name="id_pedido")
	private Pedido pedido;
	
	@ManyToOne()
	@JoinColumn(name="id_producto")
	private Producto producto;

	public DetallePedido() {
		
	}
	
	public DetallePedido(Integer cantidad, Double precioUnitario, Double subtotal, Pedido pedido, Producto producto) {
		super();
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.pedido = pedido;
		this.producto = producto;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
}
