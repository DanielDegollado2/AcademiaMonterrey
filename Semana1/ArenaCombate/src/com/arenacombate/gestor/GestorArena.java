package com.arenacombate.gestor;

import java.util.ArrayList;
import java.util.List;

import com.arenacombate.model.Personaje;

public class GestorArena {
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
    
    public List<Personaje> ObtenerPersonajes() {
    	return new ArrayList<>(personajes);
    }
    
    public void AñadirPersonaje(Personaje personaje) {
    	if(personaje != null) {
    		personajes.add(personaje);
    	}
    }
    
    public void AñadirPersonajes(List<Personaje> personajes) {
    	personajes.stream()
        .filter(e -> e != null)
        .forEach(this.personajes::add);
    }
    
    public void EliminarPersonajes() {
    	this.personajes.clear();
    }
    
    public void EliminarPersonaje(int index) {
    	this.personajes.remove(index);
    }
}
