# Proyecto Semana 1 - Arena de Combate

## Descripción
Proyecto en Java sobre un sistema de batallas por turnos entre diferentes tipos de personajes, diseñado con el objetivo de demostrar el aprendizaje de los temas vistos a lo largo de la primer semana en la academia de Xideral Monterrey.

## Temas de POO demostrados

### Polimorfismo
Los metodos atacar() y usarHabilidad() tienen diferentes comportamientos en cada subclase de Personaje (Guerrero, Mago, Arquero)

### Cast
En la clase Principal, se realizo un Downcasting de Personaje a Mago para poder acceder al metodo ganarJuego() de la clase Mago

### Singleton
La clase GestorArena es un singleton, controla el estado unico de la arena y permite ver el ranking de los jugadores dependiendo del criterio que se le provea.

### Strategy
La interfaz EstrategiaDeAtaque permite tener una logica de ataque intercambiable (AtaqueAgresivo/AtaqueDefensivo) sin tener que modificar Personaje

### HAS-A/IS-A
HAS-A: Personaje HAS-A Inventario y Personaje HAS-A EstadisticasBase
IS-A: Personaje IS-A Combatiente y IS-A Comparable. Guerrero, Mago, Arquero tienen una relacion IS-A con Personaje

### Comparable / Comparator
La clase abstracta Personaje implementa la interfaz Comparable para sobreescribir el metodo compareTo() y definir el orden natural por nivel, el metodo obtenerRanking() de la clase GestorArena recibe un argumento "criterio" de tipo Comparator para poder ordenar la lista de personajes de diferentes maneras

### Clases anonimas
En la clase Principal se da una clase anonima como parametro al metodo obtenerRanking() para ordenar a los jugadores por vida restante de forma descendente

### Inmutabilidad
La clase EstadisticasBase es inmutable, se coloca final al momento de definirla para que no pueda ser heredada, sus atributos son final y al ser solo valores primitivos es seguro que no podran ser modificados. La clase tampoco tiene setters, por lo que no hay forma de ser modificada despues de inicializada

### Static / Final
Diferentes constantes en varias clases (ej. PROBABILIDAD_CONTRAATAQUE, VIDA_CURADA)

### Generics
En la clase Inventario se define un upper bounded generic (Inventario<T extends Item>), por lo que T solo podra ser una clase que implemente Item

### Interfaces
Se definen las interfaces Item, Combatiente y EstrategiaDeAtaque para modelar diferentes contratos de comportamiento

### Abstract
Personaje se define como una clase abstracta ya que no existe un personaje generico, solo es una abstraccion de lo que deberia contener un personaje. Se definen metodos concretos: curar() y recibirDaño() porque todos los personajes realizan estas acciones de la misma forma, los metodos abstractos atacar() y usarHabilidad() se definen de esta forma para permitir que cada tipo de personaje tenga su propia forma de atacar y usar su habilidad

### Constructores
Las clases implementan constructores propios y utilizan super() para invocar el constructor de la clase padre

### Modificadores de acceso
Uso de public, private y protected segun que operaciones se busca exponer

### Lambdas
Se utilizan lambdas en el metodo añadirPersonajes() de la clase GestorArena para filtrar personajes que sean nulos y posteriormente añadirlos a la lista de personajes en la arena

### Encapsulación
Se definieron atributos privados con getters y setters para aplicar la encapsulacion, solo se definieron setters para atributos en los que no hay problema si son modificados despues de ser inicializados. En las clases Inventarios y GestorArena se retornan copias defensivas en los getters que buscan retornar colecciones mutables 
