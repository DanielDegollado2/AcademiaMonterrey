# Core Avanzado: Threading, Manejo de Archivos y Serialización

## Threading
Los threads o hilos nos permiten ejecutar múltiples tareas de forma concurrente dentro de un mismo programa. Esto quiere decir que si nuestra máquina tiene varios núcleos, los threads permiten que distintas partes del programa se ejecuten en paralelo, en vez de una tarea a la vez. Cuando una tarea espera algo, ese tiempo de espera es tiempo muerto para el CPU, si se utilizan las threads mientras uno espera otro puede seguir trabajando.

### Crear threads con Thread / Runnable
Hay dos formas básicas de crear threads:
```java
class Threading extends Thread {
    public void run() {
        System.out.println("Corriendo en: " + Thread.currentThread().getName());
    }
}

Threading t = new Threading();
t.start();
```
Crear una clase que extienda Thread y llamar al metodo start() para crear un hilo nuevo del sistema operativo, y despues ahí corre run(). 
```java
Runnable runnable = () -> System.out.println("Corriendo en: " + Thread.currentThread().getName());
Thread t = new Thread(runnable);
t.start();
```
Implementar runnable y llamar al metodo start(). Es imporante no confundirse y llamar run() ya que no crearía nada nuevo, simplemente se ejecuta en el hilo actual y causaría que el código sea secuencial.

### Crear threads con un ExecutorService
Crear threads con ExecutorService es mejor que crearlos de forma manual con Thread / Runnable. Esto porque crear threads manualmente tiene varios problemas: es costoso porque cada thread real consume memoria y recursos del sistema operativo, si no se tiene un control puede llegar a tumbar la aplicación; cada thread que se crea se destruye al terminar, no existe un pool de donde tomar los threads que no se esten utilizando; los threads son difíciles de gestionar
ExecutorService utiliza un pool de threads reutilizables que nos ahorra todos esos problemas:
```java
ExecutorService executor = Executors.newFixedThreadPool(4);

for (int i = 0; i < 10; i++) {
    int id = i;
    executor.submit(() -> {
        System.out.println("Tarea " + id + " en " + Thread.currentThread().getName());
    });
}

executor.shutdown();
```
Se definen 4 threads reutilizables, el ciclo for representa 10 tareas que tienen que ser ejecutadas. El pool de ExecutorService va a reutilizar esos 4 threads para procesar las 10 tareas.

### El problema de la concurrencia
Implementar threads trae sus problemas, uno de los mas comunes es cuando múltiples threads leen y escriben la misma variable al mismo tiempo.
Un ejemplo de este problema se puede demostrar facilmente al utilizar un contador:
```java
class Contador {
    private int valor = 0;
    
    public void incrementar() {
        valor++; 
    }
    
    public int getValor() {
        return valor;
    }
}
```
En el metodo incrementar se tienen que ejecutar 3 pasos: leer el valor actual, sumarle 1 y guardar el nuevo valor. Entonces nosotros tenemos que pensar en que pasaria si dos threads hicieran esos tres pasos al mismo tiempo. El hilo A leería el valor que sería 0, el hilo B tambien leería ese valor 0, ambos hilos le sumarian 1 a ese 0 y guardarían el valor. Pensaríamos que al aplicar paralelismo y utilizar threads el valor sería 2, pero realmente sería 1.

```java
Contador contador = new Contador();
ExecutorService executor = Executors.newFixedThreadPool(10);

for (int i = 0; i < 1000; i++) {
    executor.submit(contador::incrementar);
}
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);

System.out.println(contador.getValor());
```
Se utiliza ExecutorService para definir un pool de 10 threads reutilizables, con el ciclo for definimos 1000 peticiones por ejecutar. Esos 10 threads se encargarán de ejecutar esas 1000 peticiones que consisten en mandar llamar el metodo incrementar de la clase Contador. A simple vista se esperaría que el valor del contador fuera 1000, pero realmente sería un numero inconsistente como 987, 993, etc.

### Soluciones al problema de la concurrencia
Existen tres formas de solucionar el problema:

Utilizando synchronized
```java
class Contador {
    private int valor = 0;
    
    public synchronized void incrementar() {
        valor++;
    }
    
    public synchronized int getValor() {
        return valor;
    }
}
```
Esta solución es bastante simple de implementar, basta con agregar la keyword synchronized a los metodos. Esto hace que solo un hilo pueda entrar a esos metodos. Hay que tomar en cuenta que esto significa que los demás threads tienen que esperar su turno, o sea que se serializa esa parte del código.

Utilizando AtomicInteger
```java
import java.util.concurrent.atomic.AtomicInteger;

class Contador {
    private AtomicInteger valor = new AtomicInteger(0);
    
    public void incrementar() {
        valor.incrementAndGet(); 
    }
    
    public int getValor() {
        return valor.get();
    }
}
```
Ahora valor es un AtomicInteger en lugar de un int primitivo. AtomicInteger hace que las operaciones que se ejecuten sobre el valor que envuelve sean atómicas, esto quiere decir que se ejecutan como una sola operación indivisible, ningun otro thread puede meterse mientras se ejecuta. Esta solución no es bloqueante como syncronized, ningun thread se queda dormido esperando su turno.

Utilizando Colecciones concurrentes (cuando el dato compartido es una colección)
```java
Map<String, Integer> mapa = new ConcurrentHashMap<>(); 
List<String> lista = new CopyOnWriteArrayList<>();   
```
Las versiones normales de las colecciones como ArrayList o HashMap no son seguras con múltiples threads, es por eso que es mejor usar las versiones concurrentes. ConcurrentHashMap reemplaza a HashMap y CopyOnWriteArrayList.

### Evolución del manejo de Threads a traves de las versiones de Java
- Java 1.0 - Thread y Runnable: la forma más basica, se tenía un manejo manual total
- Java 5 - ExecutorService, Callable, Future, clases atómicas como AtomicInteger, colecciones concurrentes, synchronized mejorado con Lock / ReentrantLock
- Java 7 - Framework Fork/Join, sirve para dividir tareas grandes en subtareas paralelas recursivamente
- Java 8 - CompletableFuture, se introdujo una programación asíncronica más declarativa, se puede encadenar con metodos como .thenApply(), thenCompose()
- Java 9 - Flow API, es la base de la programación reactiva en Java estándar, es la forma en la que se adoptaron los Reactive Streams que se vieron en los proyectos de WebFlux
- Java 19-21 - Virtual Threads, es el cambio mas grande, permite crear threads mucho mas ligeros que son gestionados por la JVM, haciendo que se permitan incluso millones de threads concurrentes sin tener que pagar el costo de memoria o recursos.

## Manejo de Archivos y Serialización
Para manejar archivos en Java, tenemos disponible la libreria java.nio.file.Files que es la forma mas moderna de hacerlo. Si queremos manipular archivos de la forma tradicional, podemos utilizar el paquete java.io que contiene los streams de I/O clásicos de Java como InputStream, OutputStream, Reader y Writer. La serialización, que es el proceso de convertir un objeto en memoria en un stream de bytes y la deserialización que es el proceso de convertir un stream de bytes en un objeto en memoria, necesitan manejar archivos. Por eso es importante conocer cuales son las alternativas que tenemos.

### Clase serializable
```java
class Gorilla implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private Boolean friendly;
	private transient String favoriteFood;
	private double weight = 10;

	public Gorilla(String name, int age, Boolean friendly, String favoriteFood) {
		this.name = name;
		this.age = age;
		this.friendly = friendly;
		this.favoriteFood = favoriteFood;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Boolean getFriendly() {
		return friendly;
	}

	public void setFriendly(Boolean friendly) {
		this.friendly = friendly;
	}

	public String getFavoriteFood() {
		return favoriteFood;
	}

	public void setFavoriteFood(String favoriteFood) {
		this.favoriteFood = favoriteFood;
	}

	@Override
	public String toString() {
		return "Gorilla [name=" + name + ", age=" + age + ", friendly=" + friendly + ", favoriteFood=" + favoriteFood
				+ ", weight=" + weight + "]";
	}
}
```
Para que una clase pueda ser serializable es necesario que implemente la interfaz Serializable. Si una clase hereda de una que implemente la interfaz Serializable, esta tambien lo sera. Tambien hay que verificar si la clase tiene una relación HAS-A con otra clase, ya que cualquier clase que sea atributo de una clase serializable tambien tiene que implementar la interfaz Serializable. 
Es buena práctica agregar un serialVersionUID, ya que cuando un objeto se serializa Java guarda un número que identifica la version de la clase; cuando se intenta deserializar ese archivo, Java compara el serialVersionUID guardado en el archivo con el de la clase actual en el código, si coinciden se deserializa, si no se lanza una InvalidClassException y va a fallar. Si no se define el serialVersionUID, entonces Java lo genera automáticamente, si despues de serializar una clase decides modificarla, entonces el serialVersionUID generado por Java y que esta presente en el archivo sera diferente al de la clase modificada y no sera posible deserializar ese archivo.
El atributo favoriteFood esta definido como transient, esto quiere decir que su valor no sera serializado y se visualizara como null. Esto sirve si una clase contiene datos que son sensibles y no queremos que sean tomados en cuenta cuando se serialize.

### Serialización con streams
```java
public class PrincipalObjectOutput {

	public static void main(String[] args) throws IOException {
		
		String currentDir = System.getProperty("user.dir");
        File file = new File(currentDir + "/data/gorillas.data");

		// Crear una lista de 10 gorilas
        List<Gorilla> gorillas = new ArrayList<>();
        
        gorillas.add(new Gorilla("Koko", 12, true, "Bananas"));
        gorillas.add(new Gorilla("Kong", 25, false, "Frutas tropicales"));
        gorillas.add(new Gorilla("Bubbles", 8, true, "Manzanas"));
        gorillas.add(new Gorilla("Magilla", 15, true, "Nueces"));
        gorillas.add(new Gorilla("Harambe", 17, true, "Hojas verdes"));
        gorillas.add(new Gorilla("Enzo", 20, false, "Caña de azúcar"));
        gorillas.add(new Gorilla("Lucy", 10, true, "Bayas"));
        gorillas.add(new Gorilla("Coco", 5, true, "Mangos"));
        gorillas.add(new Gorilla("Brutus", 22, false, "Melones"));
        gorillas.add(new Gorilla("Nala", 7, true, "Plátanos"));
        gorillas.add(new Gorilla("KingKong", 20, true, "Plátanos"));
        
        saveToFile(gorillas,file);
        
        System.out.println("Listo!!!");
	}

	static void saveToFile(List<Gorilla> gorillas, File dataFile) throws IOException {
		try (var out = new ObjectOutputStream(
					   new BufferedOutputStream(
					   new FileOutputStream(dataFile)))) {

			for (Gorilla gorilla : gorillas)

				out.writeObject(gorilla);
		}
	}

}
```
La clase PrincipalObjectOutput serializa una lista de objetos Gorilla. Definimos un nuevo objeto File y especificamos la ruta de este, posteriormente pasamos la lista de gorillas y el File al metodo saveToFile()como argumentos. En ese metodo se puede observar como utilizamos los streams, hay 3 streams envueltos uno dentro de otro, cada uno agregando una capacidad. FileOutputStream escribe bytes directamente al archivo físico, BufferedOutputStrem envuelve al anterior y agrega un buffer en memoria para acumular datos y escribirlos en bloques mas grandes, el stream que agrega la capacidad de serializar objetos Java completos y envuelve a los dos anteriores es ObjectOutputStream, lo hace mediante el metodo writeObject. Todos esos streams estan dentro de un try-with-resources, para que al terminar el bloque de código o si ocurre una excepción, los tres streams se cierren automáticamente.
El bucle For es donde ocurre la serialización, cada elemento Gorilla de la lista gorillas se pasa como argumento al metodo writeObject() y escribe el dato en el archivo "gorillas.data".

### Deserialización con java.nio.file.Files
```java
public class PrincipalObjectInput {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String currentDir = System.getProperty("user.dir");
		Path dataFile = Path.of(currentDir, "data", "gorillas.data");

		List<Gorilla> gorillas = readFromFile(dataFile);

		gorillas.forEach(System.out::println);

		System.out.println("Listo!!!");
	}

	static List<Gorilla> readFromFile(Path dataFile) throws IOException, ClassNotFoundException {

		var gorillas = new ArrayList<Gorilla>();

		try (var in = new ObjectInputStream(
				      new BufferedInputStream(
				      Files.newInputStream(dataFile)))) {
			while (true) {
				var object = in.readObject();
				if (object instanceof Gorilla g)
					gorillas.add(g);
			}
		} catch (EOFException e) {
			return gorillas;
		}

	}

}
```
PrincipalObjectInput se encarga de deserializar un archivo y convertirlo a una lista de objetos Gorilla. En esta clase se genera un Path con Path.of(), este metodo toma múltiples argumentos y arma la ruta uniendo los segmentos automaticamente, ya no se necesita escribir la ruta con /.
Se define una lista de objetos Gorilla que recibira los datos obtenidos del metodo readFromFile. Dentro del metodo readFromFile se observan 3 streams envueltos uno del otro, Files.newInputStream es el equivalente moderno de FileInputStream y regresa un InputStream normal, por lo que puede seguir siendo envuelto por BufferedInputStream y ObjectInputStream. newInputStream es el que abre el archivo y da accesso a los bytes crudos, BufferedInputStream agrega un buffer para poder leer un bloque grande en lugar de byte por byte, finalmente ObjectInputStream nos da la capacidad de transformar los bytes en objetos de Java. Los tres streams estan dentro de un try-with-resource para que se cierren automáticamente cuando termine el bloque de código o ocurra una excepción.
Dentro del bucle while es donde ocurre la deserialización. Es un bucle infinito por lo que continuara hasta que ya no haya mas datos que leer del archivo y lance una EOFException, el bloque de código dentro del bucle primero lee el objeto con el método readObject(), verifica que el objeto sea un Gorilla y lo agrega a un ArrayList que se retorna en cuanto se lance la excepción.

