package com.luv2code.springboot.cruddemo.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pedidos")
public class Pedido {
	@Id
	private String id;
	
	private LocalDate fechaCreacion;
	
	private EstadoPedido estado;
	
	private Double total;
	
	private String direccionEnvio;
	
	private TipoPago metodoPago;
	
	private String clienteId;

	public Pedido() {
		
	}
	
	public Pedido(LocalDate fechaCreacion, EstadoPedido estado, Double total, String direccionEnvio,
			TipoPago metodoPago, String clienteId) {
		super();
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.total = total;
		this.direccionEnvio = direccionEnvio;
		this.metodoPago = metodoPago;
		this.clienteId = clienteId;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}

	public TipoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(TipoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setCliente(String clienteId) {
		this.clienteId = clienteId;
	}
	
}

