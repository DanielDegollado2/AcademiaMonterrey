package com.arenacombate.model;

import com.arenacombate.combate.Combatiente;

public abstract class Personaje implements Combatiente, Comparable<Personaje> {
	private Inventario<Item> inventario;
	private double vida;
	private int nivel;
	private String nombre;
	private final EstadisticasBase estadisticasBase;
	
	Personaje(double vida, int nivel, String nombre, EstadisticasBase estadisticasBase){
		this.vida = vida;
		this.nivel = nivel;
		this.nombre = nombre;
		this.inventario = new Inventario<>();
		this.estadisticasBase = estadisticasBase;
	}
	
	public Inventario<Item> getInventario() {
		return inventario;
	}

	public double getVida() {
		return vida;
	}

	public int getNivel() {
		return nivel;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public EstadisticasBase getEstadisticasBase() {
		return estadisticasBase;
	}

	protected void setVida(double vida) {
		this.vida = vida;
	}

	protected void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public int compareTo(Personaje otro) {
		return otro.getNivel() - this.getNivel();
	}
	
	public void curar(double cantidad) {
		this.vida = Math.min(100, this.getVida() + cantidad);
	}
	
	@Override
	public void recibirDaño(double cantidad) {
		double reduccionDeDaño = Math.min(1, this.getEstadisticasBase().getDefensa());
		double daño = cantidad * reduccionDeDaño; 
		double vida = this.getVida();
		
		this.setVida(Math.max(0, vida - daño) );
	}

	@Override
	public boolean estaVivo() {
		return this.getVida() > 0;
	}
	
	public abstract void atacar(Personaje objetivo);
	public abstract void usarHabilidad(Personaje objetivo);
}
