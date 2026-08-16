package com.arenacombate.model;

import java.util.Random;

import com.arenacombate.combate.EstrategiaDeAtaque;

public class Arquero extends Personaje {
	private static final int PROBABILIDAD_DE_DISPARO_CERTERO = 20;
	private static final double DAÑO_CRITICO = 30;
	private static final double DAÑO_NORMAL = 15;
	private static final Random random = new Random();
	private EstrategiaDeAtaque estrategiaDeAtaque;
	
	public Arquero(double vida, int nivel, String nombre, Arma arma, EstrategiaDeAtaque estrategiaDeAtaque, EstadisticasBase estadisticasBase) {
		super(vida, nivel, nombre, arma, estadisticasBase);
		this.estrategiaDeAtaque = estrategiaDeAtaque;
	}
	
	@Override
	public
	void atacar(Personaje objetivo) {
		this.estrategiaDeAtaque.ataque(this, objetivo);
	}

	@Override
	public void usarHabilidad(Personaje objetivo) {
		double daño = random.nextInt(100) < PROBABILIDAD_DE_DISPARO_CERTERO ? DAÑO_CRITICO : DAÑO_NORMAL;
		
		objetivo.recibirDaño(daño);
	}

}
