package com.arenacombate.combate;

import java.util.Random;

import com.arenacombate.model.Personaje;

public class AtaqueDefensivo implements EstrategiaDeAtaque {
	private static final double PROBABILIDAD_CONTRAATAQUE = 10;
	private static final double DAÑO_RECIBIDO = 5;
	private static final double DAÑO_EFECTUADO = 8;
	private static final Random rand = new Random();
	
	@Override
	public void ataque(Personaje atacante, Personaje objetivo) {
		if(rand.nextInt(100) <= PROBABILIDAD_CONTRAATAQUE ) {
			atacante.recibirDaño(DAÑO_RECIBIDO);
		}
		
		double bonoFuerza = Math.min(25, atacante.getEstadisticasBase().getFuerza());
		double dañoFinalEfectuado = DAÑO_EFECTUADO + bonoFuerza;
		
		objetivo.recibirDaño(dañoFinalEfectuado);
	}
}
