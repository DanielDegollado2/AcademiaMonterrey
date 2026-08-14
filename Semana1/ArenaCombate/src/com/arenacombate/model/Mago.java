package com.arenacombate.model;

import com.arenacombate.combate.EstrategiaDeAtaque;

public class Mago extends Personaje {

	private EstrategiaDeAtaque estrategiaDeAtaque;
	private static final double REDUCCION_DE_DANO = 0.08;
	
	public Mago(int vida, int nivel, String nombre, Arma arma, EstrategiaDeAtaque estrategiaDeAtaque) {
		super(vida, nivel, nombre, arma);
		this.estrategiaDeAtaque = estrategiaDeAtaque;
	}

	@Override
	public void atacar(Personaje objetivo) {
		this.estrategiaDeAtaque.ataque(this, objetivo);
		
	}

	@Override
	public void usarHabilidad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recibirDaño(double cantidad) {
		double daño = cantidad * (1 - REDUCCION_DE_DANO); 
		double vida = this.getVida();
		
		this.setVida(Math.max(0, vida - daño) );
		
	}

	@Override
	public boolean estaVivo() {
		return this.getVida() > 0;
		
	}

}
