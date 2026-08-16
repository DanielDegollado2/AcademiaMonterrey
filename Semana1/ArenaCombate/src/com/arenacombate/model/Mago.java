package com.arenacombate.model;

import java.util.Random;

import com.arenacombate.combate.EstrategiaDeAtaque;
import com.arenacombate.gestor.GestorArena;

public class Mago extends Personaje {
	private static final double VIDA_CURADA = 15;
	private static final double PROBABILIDAD_GANAR = 50;
	private static final Random random = new Random();

	private EstrategiaDeAtaque estrategiaDeAtaque;
	
	public Mago(double vida, int nivel, String nombre, EstrategiaDeAtaque estrategiaDeAtaque, EstadisticasBase estadisticasBase) {
		super(vida, nivel, nombre, estadisticasBase);
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
	
	public void ganarJuego() {
		GestorArena arena = GestorArena.getInstance();
		if(random.nextInt(100) < PROBABILIDAD_GANAR) {
			arena.obtenerPersonajes().stream().forEach(p -> p.setVida(0));
			System.out.println(this.getNombre() + " (Mago) ha ganado el juego! Daño letal a todos los jugadores");
		}else {
			System.out.println(this.getNombre() + " (Mago) no tuvo suerte...");
		}
	}
}
