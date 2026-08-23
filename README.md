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

# Semana 2 - Proyecto Spring Boot con Jpa y Mongo Repository, Demostracion Inyeccion de Dependencias

## Descripción
Desarrollo de dos proyectos en Spring Boot con arquitectura en capas equivalente (presentación y servicio), variando únicamente la capa de persistencia entre JPA/MySQL y Spring Data MongoDB, para comparar ambos enfoques de acceso a datos.
Demostración en un proyecto Java del concepto Inyección de Dependencias

### Entidades
Se utilizaron tres entidades: Cliente, Pedido, Factura

Atributos Cliente:
id, nombre, apellido, correo, contrasena, telefono, fechaRegistro, direccionEnvio, activo, pedidos

Atributos Pedido:
id, fechaCreacion, estado, total, direccionEnvio, metodoPago, cliente

Atributos Factura:
id, numeroFactura, subtotal, impuestos, total, pedido

### Relaciones
Existe una relacion One to Many entre Cliente y Pedido, ya que un cliente puede realizar varios pedidos y tenerlos a su nombre

Existe una relacione One to One entre Pedido y Factura, ya que un pedido solo puede tener una factura y una factura solo puede pertenecer a un pedido, la factura se genera al momento de marcar un pedido como "PAGADO"

### Como levantar los proyectos
Proyecto Jpa: Crear base de datos ecommerce_db y tablas cliente, pedido y factura utilizando el script sql, el proyecto corre en el puerto 8070
Proyecto Mongo: El proyecto corre en el puerto 8082

### Endpoints
Los proyectos comparten los endpoints siguientes:

ClienteRestController:
GET    /api/clientes
GET    /api/clientes/{clienteId}
GET    /api/clientes/correo/{correo}
GET    /api/clientes/{clienteId}/pedidos
POST   /api/clientes
PUT    /api/clientes
PATCH  /api/clientes/{clienteId}
PATCH  /api/clientes/{id}/desactivar
DELETE /api/clientes/{clienteId}

PedidoRestController:
GET    /api/pedidos
GET    /api/pedidos/{pedidoId}
GET    /api/pedidos/cliente/{clienteId}
POST   /api/pedidos
PATCH  /api/pedidos/{id}/estado
DELETE /api/pedidos/{pedidoId}

FacturaRestController
GET    /api/facturas
GET    /api/facturas/{facturaId}
GET    /api/facturas/pedido/{pedidoId}
DELETE /api/facturas/{facturaId}

### Proyecto Inyección de Dependencias
Para demostrar el concepto de inyección de dependencias decidí crear la clase Auto, porque un auto no crea su propio motor, necesita que alguien mas se lo instale. Cree una interfaz Motor que es implementada por las clases MotorElectrico y MotorGasolina, la clase Auto solo sabe que debe tener un motor, pero no especifica de que tipo. Auto se enfoca en ejecutar su método arrancarAuto(), que a su vez llama al método arrancarMotor() de la dependencia motor. La clase Inyector es la que se encarga de proporcionarle el motor al auto mediante su constructor, de esta forma se reduce el acoplamiento porque no se necesita modificar nada dentro de la clase Auto para que esta tenga un motor, esa tarea se le delega al Inyector.
Al momento de testar esto es útil porque digamos que solo queremos testear la funcionalidad del auto, si tuviéramos una clase Auto que ejecuta un new Motor dentro de su clase y crea un motor internamente, entonces ese test también dependería de que el motor tenga un correcto funcionamiento y si hay algún fallo no sabremos si fue culpa del auto o del motor. Gracias al desacoplamiento que nos facilita la inyección de dependencias, simplemente podemos proporcionarle un objeto "mock" o "falso" para poder enfocarnos en la funcionalidad del auto.
