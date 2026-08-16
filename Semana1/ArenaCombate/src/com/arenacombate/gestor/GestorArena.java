package com.arenacombate.gestor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.arenacombate.model.Personaje;

public final class GestorArena {
	private static GestorArena instancia;
    private List<Personaje> personajes;

    private GestorArena() {
        this.personajes = new ArrayList<>();
    }

    public static GestorArena getInstance() {
        if (instancia == null) {
        	instancia = new GestorArena();
        }
        return instancia;
    }
    
    public List<Personaje> obtenerRanking(Comparator<Personaje> criterio) {
        List<Personaje> ranking = obtenerPersonajes();
        ranking.sort(criterio);
        return ranking;
    }
    
    public List<Personaje> obtenerPersonajes() {
    	return new ArrayList<>(personajes);
    }
    
    public void añadirPersonaje(Personaje personaje) {
    	if(personaje != null) {
    		personajes.add(personaje);
    	}
    }
    
    public void añadirPersonajes(List<Personaje> nuevosPersonajes) {
    	nuevosPersonajes.stream()
        .filter(e -> e != null)
        .forEach(this.personajes::add);
    }
    
    public void eliminarPersonajes() {
    	this.personajes.clear();
    }
    
    public void eliminarPersonaje(int index) {
    	this.personajes.remove(index);
    }
}
