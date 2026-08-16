package com.arenacombate.model;

import com.arenacombate.combate.EstrategiaDeAtaque;

public class Guerrero extends Personaje{

	private EstrategiaDeAtaque estrategiaDeAtaque;
	
	public Guerrero(double vida, int nivel, String nombre, EstrategiaDeAtaque estrategiaDeAtaque, EstadisticasBase estadisticasBase) {
		super(vida, nivel, nombre, estadisticasBase);
		this.estrategiaDeAtaque = estrategiaDeAtaque;
	}

	@Override
	public void atacar(Personaje objetivo) {
		this.estrategiaDeAtaque.ataque(this, objetivo);
	}

	@Override
	public void usarHabilidad(Personaje objetivo) {
		double daño = this.getEstadisticasBase().getFuerza() * 2;
		objetivo.recibirDaño(daño);
	}
}
