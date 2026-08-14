package com.arenacombate.combate;

import com.arenacombate.model.Personaje;

public interface EstrategiaDeAtaque {
	void ataque(Personaje atacante, Personaje objetivo);
}
