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
		objetivo.recibirDaño(DAÑO_EFECTUADO);
	}
}
