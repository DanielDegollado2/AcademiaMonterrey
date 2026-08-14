package com.arenacombate.model;

public class PocionVida extends Item {
	private static final double VIDA_AGREGADA = 25;
	
	@Override
	void usar(Personaje portador) {
		double nuevaVida = Math.min(100, portador.getVida() + VIDA_AGREGADA);
		portador.setVida(nuevaVida);
	}

}
