package com.arenacombate.model;

import java.util.ArrayList;
import java.util.List;

public class Inventario<T extends Item> {
	private List<T> items = new ArrayList<>();
	
	public List<T> obtenerItems() {
	    return new ArrayList<>(items); 
	}
	
	public void agregarItem(T item) {
		items.add(item);
	}
	
	public T obtenerItem(int index) {
		return items.get(index);
	}
	
	public void usarItem(int index, Personaje portador) {
		T item = items.get(index);
		item.usar(portador);
		items.remove(index);
	}
}
