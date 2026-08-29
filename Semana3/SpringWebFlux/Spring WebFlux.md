# Spring WebFlux
Este tema esta relacionado con el paradigma de la programación reactiva, parte del concepto de no tener que estar pidiendo las cosas y esperar a que lleguen sin hacer nada más, podemos simplemente "suscribirnos" a algun publisher y que las cosas que hayamos pedido nos lleguen mientras nosotros hacemos otra cosa. De esta forma nos volvemos mucho mas eficientes. A las cosas o datos que vayamos recibiendo podemos llamarlos un "flujo", este flujo es una secuencia de datos que se van procesando de forma asincrona a medida que van estando disponibles, voy a hablar sobre dos tipos de flujos, que nos sirven en diferentes escenarios; ya sea que necesitemos solo 0 o 1 datos (Mono) o 0 a N datos (Flux).

## Mono
Mono es un tipo de flujo el cual su principal característica es que solamente retorna uno o ningun valor. 

### Endpoint reactivo Mono
```java
 @GetMapping("/employees/{id}")
    public Mono<Employee> findById(@PathVariable int id) {
        log.info("-> pediste el empleado {} (hilo: {})", id, Thread.currentThread().getName());

        return repo.findById(id)
                   .doOnNext(e -> log.info("<- llego {} (hilo: {})",
                           e.firstName(), Thread.currentThread().getName()))
                   .switchIfEmpty(Mono.error(new ResponseStatusException(
                           HttpStatus.NOT_FOUND, "No existe el empleado " + id)));
    }
```
Que un metodo retorne un Mono significa que retorna un "contenedor" que representa un valor que llegara en el futuro (ya sea 0 o 1 elemento), no retorna el valor en si, es la "promesa" de que el valor va a estar ahí. Una vez que la base de datos responde, el framework "activa" el Mono con el dato. Además, esto abarca el tema de hilos, el hilo que atendió esa petición queda libre inmediatamente, no tiene que quedarse esperando a que el repositorio regrese el valor. Esto significa que esos hilos que fueron liberados podrán ser utilizados para otras peticiones. 

En el siguiente endpoint bloqueante se puede ver la diferencia
```java
@GetMapping("/employees/{id}")
    public Employee findByIdBloqueante(@PathVariable int id) {
        return repo.findByIdBloqueante(id);   // Thread.sleep() sobre el event loop
    }
```
El tipo de retorno de este metodo no tiene Mono<>, tiene un tipo que necesitara que la base de datos le regrese. El hilo a cargo de esa petición se quedara esperando hasta que el valor sea retornado, sin importar que tantas otra peticiones pendientes haya.

### Estados posibles de un Mono
- Un valor: retorna exactamente un valor, no más. Arroja un 200 OK
- Vacio: Para Mono, el estar vacío no representa un error, arrojara un 200 OK que podría confundir a varias personas. Si se quiere que Mono arroje un error si esta vacío, se puede utilizar el método:
```java
switchIfEmpty(Mono.error(new ResponseStatusException(
                           HttpStatus.NOT_FOUND, "No existe el empleado " + id)));
```
- Error: El código de arriba hace que Mono retorne una excepción en lugar de un valor. Las excepciones de la programación reactiva se pueden manejar sin necesidad de un try-catch, por ejemplo:
```java
 @GetMapping("/employees/{id}/boom")
    public Mono<Employee> boom(@PathVariable int id) {
        return repo.findById(id)
                   .flatMap(e -> Mono.<Employee>error(new IllegalStateException("truena a proposito")))
                   .onErrorResume(ex -> {
                       log.warn("me lo comi: {}", ex.getMessage());
                       return Mono.just(new Employee(-1, "Plan", "B", "fallback@academymty.mx"));
                   });
    }
```
Este es un endpoint que siempre va a tener un error, se recupera de este gracias al metodo onErrorResume(). En el cuerpo dentro de ese metodo se loggea el mensaje de la excepcion y finalmente se retorna un nuevo Mono, que emite un objeto Employee.

### Comparación endpoint reactivo y bloqueante
```bash
$ ./comparar.sh 100

  Tu maquina tiene 16 nucleos, asi que el event loop de Netty tiene
  ~16 hilos. Lanzamos 100 peticiones CONCURRENTES a cada ruta.

  Prediccion antes de correrlo:
    reactivo    -> ~5.000s   (ningun hilo espera: las 100 se solapan)
    bloqueante  -> ~31.25s   (100 peticiones / 16 hilos = 6.2 tandas de 5.000s)

  reactivo     100 peticiones en   6.30 s
  bloqueante   100 peticiones en  35.27 s
```
En esta comparación se puede observar claramente la diferencia. Mi maquina tiene 16 hilos disponibles, los cuales son utilizados de una forma mas eficiente por el endpoint reactivo, los hilos no esperan a que la base de datos de una respuesta, el endpoint reactivo los suelta y pueden ejecutar otra petición hasta terminar con las 100. El endpoint bloqueante "duerme" a los hilos una vez que empiezan la petición, esto quiere decir que mi maquina puede ejecutar 16 peticiones al mismo tiempo, pero los hilos deben esperar hasta que cada petición termine para poder iniciar otra.

## Flux
En el anterior tipo de flujo solo podíamos recibir 0 o 1 valor, con Flux podemos tener un flujo de una N cantidad de valores. 

### Endpoint reactivo Flux
```java

@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Lectura> comoJson() {
        log.info("[JSON]  el cliente esperara a que terminen las 5 lecturas");
        return sensor.lecturas().take(5);
    }

 @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Lectura> comoStream() {
        log.info("[STREAM] el cliente recibira una lectura por segundo");
        return sensor.lecturas().take(20);
    }
```

Técnicamente ambos endpoints son reactivos Flux, pero al ejecutar una petición en cada uno, se puede observar como el metodo comoJson() retorna los valores de golpe y comoStream() si va retornando los valores conforme van estando disponibles. Esto tiene que ver con el MediaType que producen, comoJson() produce un Json y este tiene que esperar a recolectar todos los valores para poder retornarlos, Stream no tiene esa necesidad y se ve claramente como cada valor va apareciendo cada segundo.

### Prueba endpoints reactivos Flux
```bash
== A. application/json  -- vas a esperar 5 segundos EN SILENCIO ==
   (mira el reloj: no aparece nada hasta el final)
[{"numero":0,"sensor":"sensor-A","celsius":26.0,"hora":"00:26:19"},{"numero":1,"sensor":"sensor-A","celsius":28.5,"hora":"00:26:20"},{"numero":2,"sensor":"sensor-A","celsius":30.7,"hora":"00:26:21"},{"numero":3,"sensor":"sensor-A","celsius":32.5,"hora":"00:26:22"},{"numero":4,"sensor":"sensor-A","celsius":33.6,"hora":"00:26:23"}]
real    0m5.119s
user    0m0.031s
sys     0m0.030s

== B. text/event-stream -- el MISMO Flux, una lectura por segundo ==
   (la -N de curl desactiva el buffer; sin ella verias lo mismo que en A)
data:{"numero":0,"sensor":"sensor-A","celsius":26.0,"hora":"00:26:24"}

data:{"numero":1,"sensor":"sensor-A","celsius":28.5,"hora":"00:26:25"}

data:{"numero":2,"sensor":"sensor-A","celsius":30.7,"hora":"00:26:26"}

data:{"numero":3,"sensor":"sensor-A","celsius":32.5,"hora":"00:26:27"}
```
En el resultado de la prueba A se observa como el tiempo real en el que tardaron en aparecer los datos es de 5 segundos aproximadamente, aunque en el atributo de hora de cada temperatura se ve el tiempo en el que los datos tardaron en estar disponibles. Basicamente cada dato tardo solo 1 segundo en ser retornado, pero tuvimos que esperar 5 segundos en verlos ya que el Json estaba recolectandolos.
En el resultado de la prueba B se observa como el atributo hora de cada temperatura va aumentando en 1 segundo, se comprueba que cada dato puede ser mostrado cada segundo si se muestran en un Stream y no en un Json.

## Flujos Lazy
Que un flujo sea Lazy significa que no se ejecuta al momento de ser definido, solo hasta que alguien se suscribe. Antes de eso, al definir el metodo reactivo solo estamos diciendo que va a retornar un Mono/Flux y que estos operadores van a actuar sobre el flujo. 

```java
public Flux<Lectura> lecturas() {
        return Flux.interval(CADENCIA)
                   .map(this::medir);
    }
```
En este código se define el flujo, cuando se mande a llamar a este método no se ejecuta nada todavía. Se devuelve un Flux y el método se termina de inmediato. Cuando el controlador regrese ese Flux es cuando Spring WebFlux se suscribe y empieza a ejecutarse toda la cadena de operadores definida previamente.

## Casos donde WebFlux no vale la pena
Hay algunos casos donde WebFlux no vale tanto la pena, por ejemplo:
- Cuando se tiene un proyecto CRUD basico que sigue utilizando JPA/Hibernate via JDBC. Ya que esta capa de repositorio sería bloqueante, se tendría que modificar a R2DBC y habría que valorar cuales son los impactos de ese cambio. Hay muchas empresas con proyectos que siguen utiizando esa arquitectura tradicional y tal vez para ellos WebFlux no represente tanta ventaja como para hacer esa modificación.

- Migrar una app Spring MVC ya funcional: Ademas de cambiar la capa del repositorio, habria que modificar bastantes servicios y controladores para que retornen Mono/Flux, asegurarse de que no haya puntos que puedan ser bloqueantes en toda la aplicación, porque si se llegase a escapar uno podría ser peor que tener una aplicación sin WebFlux. Hay aplicaciones que ya funcionan sin problemas y el hacer la migracion hacia WebFlux puede representarles mas inconvenientes que aspectos positivos.

- El equipo no esta familiarizado con la programación reactiva: La programación reactiva es un paradigma de programación completamente diferente, el cual tiene una curva de aprendizaje lenta. Si se le deja la responsabilidad de construir una aplicación con código reactivo a un equipo sin experiencia en esta area, podría tener como resultado un ciclo de desarrollo bastante lento y una aplicación propensa a tener errores, por lo que el costo de mantenimiento puede superar el beneficio de rendimiento.
