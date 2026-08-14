package com.arenacombate.model;

public abstract class Item {
	int cantidad;
	abstract void usar(Personaje usuario);
	
	public Item() {
		
	}
}
