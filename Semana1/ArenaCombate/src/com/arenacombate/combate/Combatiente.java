package com.arenacombate.combate;

public interface Combatiente {
	void recibirDaño(double cantidad);
	void curar(double cantidad);
	boolean estaVivo();
}
