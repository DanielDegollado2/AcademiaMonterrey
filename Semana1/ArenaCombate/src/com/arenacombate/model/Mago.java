package com.arenacombate.model;

import com.arenacombate.combate.EstrategiaDeAtaque;

public class Mago extends Personaje {
	private static final double VIDA_CURADA = 15;

	private EstrategiaDeAtaque estrategiaDeAtaque;
	
	public Mago(double vida, int nivel, String nombre, Arma arma, EstrategiaDeAtaque estrategiaDeAtaque, EstadisticasBase estadisticasBase) {
		super(vida, nivel, nombre, arma, estadisticasBase);
		this.estrategiaDeAtaque = estrategiaDeAtaque;
	}

	@Override
	public void atacar(Personaje objetivo) {
		this.estrategiaDeAtaque.ataque(this, objetivo);
	}

	@Override
	public void usarHabilidad(Personaje objetivo) {
		if(objetivo != this) {
			objetivo.curar(VIDA_CURADA);
		}
	}
}
