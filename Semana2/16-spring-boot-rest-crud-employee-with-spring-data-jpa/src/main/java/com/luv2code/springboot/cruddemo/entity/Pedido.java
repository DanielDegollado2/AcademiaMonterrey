package com.luv2code.springboot.cruddemo.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="fecha_creacion")
	private LocalDate fechaCreacion;
	
	@Enumerated(EnumType.STRING)
	@Column(name="estado")
	private EstadoPedido estado;
	
	@Column(name="total")
	private Double total;
	
	@Column(name="direccion_envio")
	private String direccionEnvio;
	
	@Enumerated(EnumType.STRING)
	@Column(name="metodo_pago")
	private TipoPago metodoPago;
	
	@ManyToOne()
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	public Pedido() {
		
	}
	
	public Pedido(LocalDate fechaCreacion, EstadoPedido estado, Double total, String direccionEnvio,
			TipoPago metodoPago, Cliente cliente) {
		super();
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.total = total;
		this.direccionEnvio = direccionEnvio;
		this.metodoPago = metodoPago;
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
