package com.arenacombate.model;

import com.arenacombate.combate.EstrategiaDeAtaque;

public class Guerrero extends Personaje{

	private EstrategiaDeAtaque estrategiaDeAtaque;
	private static final double REDUCCION_DE_DANO = 0.15;
	
	public Guerrero(double vida, int nivel, String nombre, Arma arma, EstrategiaDeAtaque estrategiaDeAtaque) {
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
		
		this.setVida(vida - daño);
		
	}

	@Override
	public void estaVivo() {
		// TODO Auto-generated method stub
		
	}

}
