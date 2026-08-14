package com.arenacombate.model;

public class PocionVida implements Item {
	private static final double VIDA_AGREGADA = 25;
	
	@Override
	public void usar(Personaje portador) {
		double nuevaVida = Math.min(100, portador.getVida() + VIDA_AGREGADA);
		portador.setVida(nuevaVida);
	}

}
