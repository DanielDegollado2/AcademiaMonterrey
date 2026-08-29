# Unit Testing: JUnit y Mockito

Se necesita hacer Unit testing para asegurarnos que nuestro código se comporta como nosotros esperamos, en Java tenemos herramientas como JUnit que nos proporciona diferentes anotaciones y metodos para poder construir pruebas unitarias y Mockito que sirve para que podamos crear objetos mock en casos donde no queramos acceder a la base de datos y tener que esperar a que los datos sean retornados.

## JUnit

### Aserciones
En JUnit exiten las aserciones, que nos sirven para asegurarnos que un bloque de código esta retornando el valor que nosotros esperamos. Existe una gran variedad de aserciones:

- assertEquals: Checa la igualdad tomando como referencia el metodo equals() de los objetos que estan siendo comparados.

- assertTrue/assertFalse: Comprueba que se retorne true o false.

- assertSame/assertNotSame: Compara Identidad con ==, dos record con los mismos datos serían equals pero no same.

- assertArrayEquals: Compara arreglos, posición por posición. Esto sirve ya que un assertEquals sobre arreglos compara referencias y siempre fallaría.

- assertIterableEquals: Checa que una colección tenga los mismo elementos y el mismo orden.

- assertLinesMatch: Comprueba que el contenido de un texto sea el mismo línea a línea, admite expresiones regulares.

- assertThrows: Comprueba que algo lance una excepcion

- assertAll: Comprueba varias aserciones de golpe, esto sirve para poder ejecutar todas las aserciones y reporta juntas las que fallaron.

```java
public class Boleta {

    public static final int CALIFICACION_MINIMA = 0;
    public static final int CALIFICACION_MAXIMA = 100;

    /** Con 70 se aprueba. Con 69.9 no. Ese "o mas" es el corazon del proyecto. */
    public static final double MINIMA_APROBATORIA = 70.0;

    private final Alumno alumno;
    private final Map<String, Integer> calificaciones = new LinkedHashMap<>();

    public Boleta(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("Una boleta necesita un alumno");
        }
        this.alumno = alumno;
    }
 /**
     * Registra la calificacion de una materia. Si la materia ya estaba,
     * la sobrescribe.
     *
     * @throws IllegalArgumentException si la calificacion se sale de 0..100
     */
    public void registrar(String materia, int calificacion) {
        if (materia == null || materia.isBlank()) {
            throw new IllegalArgumentException("La materia no puede venir vacia");
        }
        if (calificacion < CALIFICACION_MINIMA || calificacion > CALIFICACION_MAXIMA) {
            throw new IllegalArgumentException(
                    "Calificacion fuera de rango: " + calificacion
                    + " (valido: " + CALIFICACION_MINIMA + " a " + CALIFICACION_MAXIMA + ")");
        }
        calificaciones.put(materia, calificacion);
    }

    /** Media de las materias registradas. Una boleta sin materias promedia 0. */
    public double promedio() {
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        double suma = 0;
        for (int c : calificaciones.values()) {
            suma += c;
        }
        return suma / calificaciones.size();
    }

    /**
     * La regla del limite.
     *
     * Borrar el '=' de ese >= es UN SOLO caracter, y es el que separa aprobar
     * de reprobar a quien saco exactamente 70. Hazlo y casi todos los tests siguen en
     * verde: solo cae el que prueba el limite. Eso es la seccion 05 de la guia.
     */
    public boolean aprobado() {
        return promedio() >= MINIMA_APROBATORIA;
    }

    public int totalMaterias() {
        return calificaciones.size();
    }

    public Alumno alumno() {
        return alumno;
    }

    public Map<String, Integer> calificaciones() {
        return Collections.unmodifiableMap(calificaciones);
    }
```

```java
@Test
    @DisplayName("Igualdad, negacion y nulos")
    void lasBasicas() {
        assertEquals(80.0, boleta.promedio());       // (90 + 70) / 2
        assertEquals(2, boleta.totalMaterias());

        assertTrue(boleta.aprobado());
        assertFalse(boleta.calificaciones().isEmpty());

        assertNotNull(boleta.alumno());
        assertNull(boleta.calificaciones().get("Kotlin"),
                "Kotlin no se registro: el mapa devuelve null, no lanza excepcion");
    }
}
```
Esta prueba, testea diferentes metodos de la clase Boleta: promedio(), totalMaterias(), aprobado(), calificaciones() y alumno().
La primera asercion verifica que el promedio que retorne el metodo promedio() se exactamente igual a 80.0.
La segunda asercion verifica que el total de materias sea igual a 2.
La tercera asercion verifica que el promedio sea mayor a la calificacion minima aprobatoria definida, el caso pasa si se retorna un true.
La cuarta asercion verifica que el Map calificaciones no este vacio, el caso pasa si se retorna un false.
La quinta asercion verifica que la boleta tenga un alumno, el caso pasa si hay un objeto presente y no se retorna null.
La sexta asercion verifica que una materia "Kotlin" no este presente en el Map calificaciones, el caso pasa si se retorna un null. Además muestra un mensaje explicando el resultado de ese test.

```java
public void registrar(String materia, int calificacion) {
        if (materia == null || materia.isBlank()) {
            throw new IllegalArgumentException("La materia no puede venir vacia");
        }
        if (calificacion < CALIFICACION_MINIMA || calificacion > CALIFICACION_MAXIMA) {
            throw new IllegalArgumentException(
                    "Calificacion fuera de rango: " + calificacion
                    + " (valido: " + CALIFICACION_MINIMA + " a " + CALIFICACION_MAXIMA + ")");
        }
        calificaciones.put(materia, calificacion);
    }
```
En el metodo registrar() de la clase Boleta se puede observar que se hace una verificación para que la calificación que recibe como parametro no salga del rango 0-100, si se llega a salir de ese rango arrojara una IllegalArgumentException. Las pruebas no solo se deben realizar sobre el caso feliz, tambien se tienen que tomar en cuenta los posibles caminos de error.

```java
@Test
    @DisplayName("Fuera de rango, 101 y -1, lanza IllegalArgumentException")
    void fueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> boleta.registrar("Java", 101));
        assertThrows(IllegalArgumentException.class, () -> boleta.registrar("Java", -1));
    }
```
Para verificar que el metodo retorne esa excepcion, se puede crear un caso de prueba con la asercion assertThrows(), a la cual se le tiene que pasar como parametros el tipo de excepción que se debe esperar del metodo y una lambda llamando al metodo. Este caso pasa si se arroja una IllegalArgumentException.

### Ciclo de vida
JUnit nos proporciona diferentes anotaciones que controlan que corre antes y después de cada test.

- @BeforeAll: Se ejecuta una vez, antes del primer test, tiene que ser static. Sirve si es necesario abrir una conexión o levantar un contenedor.

- @BeforeEach: Se ejecuta antes de cada test. Es util si siempre es necesario tener un escenario limpio, como un objeto recien creado que va a ser manipulado de diferentes formas por las pruebas.

- @AfterEach: Se ejecuta despues de cada test, aunque falle. Sirve para limpiar cualquier cosa que haya manipulado el test.

- @AfterAll: Se ejecuta una vez al final, tambien tiene que ser static. Usualmente cierra lo que abrio @BeforeAll.

```java
    private Boleta boleta;

    @BeforeAll
    static void abrirElSemestre() {
        System.out.println("== @BeforeAll  -- una sola vez, antes de todo");
    }

    @BeforeEach
    void matricularAlumno() {
        System.out.println("   -> @BeforeEach -- boleta nueva y limpia");
        boleta = new Boleta(new Alumno("A01", "Ana Torres"));
    }

    @AfterEach
    void limpiar() {
        testsEjecutados++;
        System.out.println("   <- @AfterEach  -- llevamos " + testsEjecutados + " test(s)");
    }

    @AfterAll
    static void cerrarElSemestre() {
        System.out.println("== @AfterAll   -- una sola vez, al final. Corrieron "
                + testsEjecutados + " tests");
    }
```
En esta definición del ciclo de vida se puede ver como usamos estas aserciones para manipular lo que ocurrirá antes y despues de los tests.
@BeforeAll simplemente imprime un mensaje en la terminal que aparecera antes de que se ejecute el primer test.
@BeforeEach genera un nuevo objeto Boleta antes de cada test para tener un escenario limpio donde no importa que modificaciones hayan hecho otros tests.
@AfterEach le suma 1 a un contador testEjecutados para llevar un registro de la cantidad de tests que llegaron a ejecutarse.
@AfterAll retorna el valor final que alcanzo el contador testEjecutados.

```java

@Test
    @DisplayName("Test 1: registra Java y comprueba el promedio")
    void primerTest() {
        boleta.registrar("Java", 80);
        assertEquals(80.0, boleta.promedio());
    }

    @Test
    @DisplayName("Test 2: la boleta llego VACIA, no trae lo del test 1")
    void segundoTest() {
        // Si @BeforeEach no existiera, o si JUnit reutilizara la instancia,
        // aqui seguiria la materia "Java" del test anterior y esto seria 1.
        assertEquals(0, boleta.totalMaterias(),
                "Cada test arranca con una boleta nueva: los tests NO se heredan estado");

        boleta.registrar("SQL", 60);
        assertEquals(60.0, boleta.promedio());
    }

    /**
     * La demostracion. Este metodo incrementa un campo de instancia y luego
     * comprueba que vale 1 -- no 2, no 3, por muchas veces que corra la clase.
     *
     * Es la prueba de que JUnit te dio una instancia nueva. Y de ahi sale la
     * regla practica mas importante de este proyecto:
     *
     *   NO uses campos de instancia para pasarte datos entre tests.
     *   No funciona, y el dia que parezca funcionar es porque el orden
     *   de ejecucion te dio la razon por accidente.
     */
    @Test
    @DisplayName("Cada test corre sobre una INSTANCIA NUEVA de la clase")
    void cadaTestEsUnaInstanciaNueva() {
        contadorDeInstancia++;
        assertEquals(1, contadorDeInstancia,
                "Si esto fuera 2, JUnit habria reutilizado la instancia entre tests");
    }
```
El primer test registra Java en el Map de calificaciones con un 80 de calificación y utiiza un assertEquals para asegurarse que el promedio sea 80.0 al ser la unica materia que existe.
El segundo verifica que el Map de calificaciones contenga un total de 0, ya que no se ha realizado el registro de ninguna calificación. Despues registra SQL con una calificación de 60 y verifica que el promedio de calificaciones en el Map sea de 60.0. Si no se hubiera definido el @BeforeEach, la primer aserción no pasaría porque la calificación de Java estaria dentro del Map calificaciones al no crearse un nuevo objeto Boleta.
El tercer test sirve para demostrar como JUnit genera una instancia nueva cada de la clase cada vez que se ejecuta un test. Se definió un contadorDeInstancia no static, lo que significa que esta atado a una instancia de la clase. Con el assertEquals se verifica que ese contadorDeInstancia siempre retorne 1, si retornará un valor mayor, significa que JUnit reutilizó la instancia entre tests.

## Mockito
Mockito nos permite crear un "doble" de los colaboladores necesarios para poder ejecutar un test. Necesitamos que exista un doble porque existen colaboladores que harían que la ejecución de la prueba fuera más lenta. El ejemplo más claro de estos colaboladores son los repositorios, tener que hacer una llamada a estos cada vez que queramos hacer una prueba es algo muy costoso. Digamos que queremos probar que un curso sea marcado como lleno cuando haya 30 alumnos inscritos, habría que literalmente inscribir a los 30 alumnos y nos tomaría mucho tiempo. 
Con Mockito no nos tenemos que preocupar por nada de esto, simplemente definimos un Mock. Un mock es un objeto que Mockito fabrica en tiempo de ejecución, con todos los métodos vacíos y apunta cada llamada que recibe.

### Definir un Mock con when / verify
```java
   @Mock
    private RepositorioAlumnos repo;

    @Test
    @DisplayName("thenReturn: la respuesta fija")
    void respuestaFija() {
        when(repo.buscar("A01")).thenReturn(Optional.of(ANA));

        assertEquals(Optional.of(ANA), repo.buscar("A01"));
        assertEquals("Ana Torres", repo.buscar("A01").orElseThrow().nombre());
    }
```
Para crear un mock, debemos usar la anotación @Mock y definir el colaborador que queremos mockear. En este caso, estamos creando un mock de RepositorioAlumnos, de esta forma no es necesario hacer la llamada real para obtener datos de este.
No basta con definir el Mock, tenemos que programar la respuesta y a que llamada corresponde. En la linea when(repo.buscar("A01")).thenReturn(Optional.of(ANA)); estamos diciendo que cuando se haga una llamada al metodo buscar() del repositorio con un argumento de "A01", Mockito la tiene que interceptar y usar como patron para retornar un Optional. Mockito realmente nunca ejecuta el metodo buscar(). Hay que tener en cuenta que el mock solo responde al argumento exacto que definimos, si el código preguntara por "A99" en lugar de "A01", el mock devolverá Optional.empty().
Tambien podemos utilizar verify(), la diferencia con when() es que verify() sirve cuando nos importa que se le llamó al colaborador, no lo que este devuelve. Esto sirve especialmente con los métodos void, ya que estos no retornan ningun valor.

```java
public interface Notificador {

    void enviarConfirmacion(Alumno alumno, Curso curso);

    void enviarRechazo(Alumno alumno, Curso curso, String motivo);
}

    @Mock
    private Notificador notificador;

    private Curso java101;

    @BeforeEach
    void curso() {
        java101 = new Curso("JAVA-101", 30);
    }

    @Test
    @DisplayName("verify: se llamo, exactamente una vez")
    void seLlamo() {
        notificador.enviarConfirmacion(ANA, java101);

        verify(notificador).enviarConfirmacion(ANA, java101);
        // 'verify(x)' sin mas equivale a 'verify(x, times(1))'
    }

    @Test
    @DisplayName("times(n): cuantas veces exactamente")
    void cuantasVeces() {
        notificador.enviarConfirmacion(ANA, java101);
        notificador.enviarConfirmacion(ANA, java101);

        verify(notificador, times(2)).enviarConfirmacion(ANA, java101);
        verify(notificador, atLeast(1)).enviarConfirmacion(ANA, java101);
        verify(notificador, atMost(5)).enviarConfirmacion(ANA, java101);
    }
```
Se puede observar como la interface Notificador tiene dos metodos void, por lo que no retornan nada y no hay una forma de verificar que es lo que retornan. Este caso es perfecto para utilizar verify, ya que solo estariamos verificando que los métodos realmente se llamaron.

En el test seLlamo(), se utiliza verify() para verificar que enviarConfirmacion() se llamo exactamente una vez.
En el test cuantasVeces(), ademas de enviar el Mock como argumento a verify(), tambien podemos proporcionar diferentes metodos que verifican la cantidad de veces que se llamo al colaborador. En el primer verify, verificamos que se llamo enviarConfimracion() exactamente 2 veces, en el segundo verify verificamos que enviarConfirmacion() se llamo por lo menos 1 vez y en el tercer verify verificamos que enviarConfirmacion() no se llamo mas de 5 veces.

```java
@Test
    @DisplayName("never(): la confirmacion NO se manda")
    void loQueNoDebePasar() {
        notificador.enviarRechazo(ANA, java101, "cupo lleno");

        verify(notificador).enviarRechazo(ANA, java101, "cupo lleno");
        verify(notificador, never()).enviarConfirmacion(any(), any());
    }
```
Tambien se puede verificar que un metodo no se haya llamado nunca. En este test, al rechazarse una inscripción se quiere verificar que no se mande la confirmación. Solo tenemos que proporcionar never() como argumento y lograremos verificarlo.

### Como saber que colaboladores no mockear
Existe una regla que nos ayuda a saber esto: se tiene que mockear lo que duele, lo que sea lento, externo, no repetible, no determinista. Por eso en los tests anteriores esta bien mockear los repositorios, son colaboradores que no contienen datos de forma local y ralentizarian la ejecución de los tests. No se debe mockear lo que decide, lo que tiene adentro la regla que estamos probando. Por ejemplo, no hay que mockear las clases que son completamente locales y no tocan nada de fuera. Si queremos el objeto de una clase de este tipo, basta con construirlo con new. Los records, objetos de valor o entidades nunca se mockean.

## Experimento script ver-fallar.sh
```bash
$ ./ver-fallar.sh

== 1. La suite tal y como esta en el repositorio ==
   BUILD SUCCESS -- los 22 tests en verde.

== 2. Metemos el bug: 'promedio() >= 70' pasa a 'promedio() > 70' ==
   74:        return promedio() > MINIMA_APROBATORIA;   // BUG INYECTADO
   Un caracter. El alumno que saca exactamente 70 ahora reprueba.

== 3. La suite con el bug dentro ==
   [ERROR]   BoletaTest.elLimiteAlcanzadoPorPromedio:109 El promedio es 70 clavado: aprueba ==> expected: <true> but was: <false>
   [ERROR]   BoletaTest.elLimiteExacto:83 70 es aprobatorio: el reglamento dice '70 o mas' ==> expected: <true> but was: <false>

   [ERROR] Tests run: 22, Failures: 2, Errors: 0, Skipped: 0
```

Este script ejecuta 22 tests, se puede ver que todos se ejecutan sin ningun problema la primera vez, despues se introduce un bug al cambiar una condición: 'promedio() >= 70' pasa a 'promedio() > 70'. Esto quiere decir que los alumnos que saquen 70 van a reprobar cuando se supone que deben aprobar. Los demas tests que probaban con elementos mayores o menores a 70 nunca se dan cuenta de este bug. En el coverage la linea que contiene la condición 'promedio() > 70' estara en verde porque tecnicamente si se testeo esa parte del código, por eso no hay que dejarnos llevar por un coverage alto. Siempre hay que tomar en cuenta cinco casos por regla: el límite, el límite menos uno, el límite más uno, el vacío y el nulo.



