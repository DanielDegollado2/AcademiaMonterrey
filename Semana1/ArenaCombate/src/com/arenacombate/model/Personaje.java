package com.arenacombate.model;

import com.arenacombate.combate.Combatiente;

public abstract class Personaje implements Combatiente, Comparable<Personaje> {
	private Inventario<Item> inventario;
	private Arma arma;
	private double vida;
	private int nivel;
	private String nombre;
	
	public Inventario<Item> getInventario() {
		return inventario;
	}

	public Arma getArma() {
		return arma;
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

	public void setArma(Arma arma) {
		this.arma = arma;
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

	Personaje(double vida, int nivel, String nombre, Arma arma){
		this.vida = vida;
		this.nivel = nivel;
		this.nombre = nombre;
		this.arma = arma;
		this.inventario = new Inventario<>();
	}
	
	public abstract void atacar(Personaje objetivo);
	public abstract void usarHabilidad();
}
