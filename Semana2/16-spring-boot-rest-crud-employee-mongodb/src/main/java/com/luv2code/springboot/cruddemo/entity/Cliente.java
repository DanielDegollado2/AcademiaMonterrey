package com.luv2code.springboot.cruddemo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "clientes")
public class Cliente {
	@Id
	private String id;

	private String nombre;
	
	private String apellido;
	
	private String correo;
	
	private String contrasena;
	
	private Long telefono;
	
	private LocalDate fechaRegistro;
	
	private String direccionEnvio;
	
	private boolean activo;
	
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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
	
}

