package com.luv2code.springboot.cruddemo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="cliente")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="contrasena")
	private String contrasena;
	
	@Column(name="telefono")
	private Long telefono;
	
	@Column(name="fecha_registro")
	private LocalDate fechaRegistro;
	
	@Column(name="direccion_envio")
	private String direccionEnvio;
	
	@Column(name="activo")
	private boolean activo;
	
	@OneToMany(mappedBy= "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
		
	}
	
	public Cliente(String nombre, String apellido, String contraseña, Long telefono, LocalDate fechaRegistro,
			String direccionEnvio) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contraseña;
		this.telefono = telefono;
		this.fechaRegistro = fechaRegistro;
		this.direccionEnvio = direccionEnvio;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContraseña() {
		return contrasena;
	}

	public void setContraseña(String contrasena) {
		this.contrasena = contrasena;
	}

	public Long getTelefono() {
		return telefono;
	}

	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDireccionEnvio() {
		return direccionEnvio;
	}

	public void setDireccionEnvio(String direccionEnvio) {
		this.direccionEnvio = direccionEnvio;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public List<Pedido> getPedidos() {
	    return pedidos;
	}	
}
