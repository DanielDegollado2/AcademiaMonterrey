package com.arenacombate.model;

public class PocionVida implements Item {
	private static final double VIDA_AGREGADA = 25;
	
	@Override
	public void usar(Personaje portador) {
		portador.curar(VIDA_AGREGADA);
	}

}
