package com.arenacombate.model;

public final class EstadisticasBase {
	private final double fuerza;
	private final double defensa;
	
	public EstadisticasBase(double fuerza, double defensa) {
		this.fuerza = fuerza;
		this.defensa = defensa;
	}

	public double getFuerza() {
		return fuerza;
	}

	public double getDefensa() {
		return defensa;
	}
}
