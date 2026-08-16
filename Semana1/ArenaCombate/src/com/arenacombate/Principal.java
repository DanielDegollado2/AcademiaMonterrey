package com.arenacombate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.arenacombate.combate.*;
import com.arenacombate.model.*;
import com.arenacombate.gestor.*;

public class Principal {

	public static void main(String[] args) {
		// --- Creación de personajes ---

		EstrategiaDeAtaque ataqueAgresivo = new AtaqueAgresivo();
		EstrategiaDeAtaque ataqueDefensivo = new AtaqueDefensivo();
		
		EstadisticasBase estadisticasJohn = new EstadisticasBase(25, 20);
		EstadisticasBase estadisticasMarie = new EstadisticasBase(15, 25);
		EstadisticasBase estadisticasMichael = new EstadisticasBase(10, 35);
		
		Personaje guerrero = new Guerrero(100, 5, "John", ataqueAgresivo, estadisticasJohn);
		Personaje arquero = new Arquero(100, 7, "Marie", ataqueDefensivo, estadisticasMarie);
		Personaje mago = new Mago(100, 3, "Michael", ataqueDefensivo, estadisticasMichael);
		
		// --- Añadir personajes a arena ---
		
		GestorArena arena = GestorArena.getInstance();
		List<Personaje> personajes = new ArrayList<>();
		personajes.add(guerrero);
		personajes.add(arquero);
		personajes.add(mago);
		
		arena.añadirPersonajes(personajes);
		
		// --- Atacar a otro personaje ---
		System.out.println("Vida de arquero antes de ser atacado: " + arquero.getVida());
		System.out.println("Vida de guerrero antes de atacar (probabilidad de recibir un contra-ataque): " + guerrero.getVida());
		guerrero.atacar(arquero);
		System.out.println("Vida de arquero despues de ser atacado: " + arquero.getVida());
		System.out.println("Vida de guerrero despues de atacar (probabilidad de recibir un contra-ataque): " + guerrero.getVida());
		
		// --- Habilidades especiales ---
		// Guerrero - Golpe Critico
		System.out.println("Vida de mago antes de ser atacado con golpe critico: " + mago.getVida());
		guerrero.usarHabilidad(mago);
		System.out.println("Vida de mago despues de ser atacado con golpe critico: " + mago.getVida());
		
		// Mago - curar a otro personaje
		System.out.println("Vida de arquero antes de ser curado por mago: " + arquero.getVida());
		mago.usarHabilidad(arquero);
		mago.usarHabilidad(arquero);
		System.out.println("Vida de arquero despues de ser curado por mago: " + arquero.getVida());
		
		// Arquero - Disparo Certero
		System.out.println("Vida de guerrero antes de ser atacado con disparo certero: " + guerrero.getVida());
		arquero.usarHabilidad(guerrero);
		System.out.println("Vida de guerrero despues de ser atacado con disparo certero: " + guerrero.getVida());
		
		// --- Añadir item a inventario ---
		Inventario<Item> inventarioMago = mago.getInventario();
		inventarioMago.agregarItem(new PocionVida());
		inventarioMago.agregarItem(new PocionVida());
		inventarioMago.agregarItem(new PocionVida());
		
		System.out.println("Inventario de mago:\n" + inventarioMago);
		
		// --- Usar item de inventario
		System.out.println("Vida de mago antes de usar pocion de vida: " + mago.getVida());
		inventarioMago.usarItem(0, mago);
		inventarioMago.usarItem(1, mago);
		System.out.println("Vida de mago despues de usar pocion de vida: " + mago.getVida());
		System.out.println("Inventario de mago:\n" + inventarioMago);
		
		// --- Ranking de personajes ---
		// Por nivel
		List<Personaje> rankingPorNivel = arena.obtenerRanking((p1,p2) -> Double.compare(p2.getNivel(), p1.getNivel())); 
		System.out.println("Ranking por nivel descendente: ");
		rankingPorNivel.forEach(p -> System.out.println(p.getNombre() + ", Nivel: " + p.getNivel()));
		// Por vida
		List<Personaje> rankingPorVida = arena.obtenerRanking(new Comparator<Personaje>() {
		    @Override
		    public int compare(Personaje p1, Personaje p2) {
		        return (int) (p2.getVida() - p1.getVida()); 
		    }
		}); 
		
		System.out.println("Ranking por vida descendente: ");
		rankingPorVida.forEach(p -> System.out.println(p.getNombre() + ", Vida: " + p.getVida()));
		
		// --- Ranking por orden natural usando interfaz Comparable ---
		List<Personaje> ordenNatural = new ArrayList<>(personajes);
		Collections.sort(ordenNatural); 
		System.out.println("Orden natural (Comparable, por nivel descendente):");
		ordenNatural.forEach(p -> System.out.println(p.getNombre()));
		
		// --- Cast de Personaje a Mago ---
		for (Personaje p : personajes) {
		    if (p instanceof Mago) {
		        Mago m = (Mago) p;
		        System.out.println(m.getNombre() + " (Mago) intenta activar Ganar Juego...");
		        m.ganarJuego();
		    }
		}
	}

}
