# AWS: fundamentos, EC2, S3, VPC y RDS

## Objetivo

- Lanzar y asegurar una EC2, instalarle Java y correr ahí el jar de taskflow-api
- Usar S3: un bucket privado.
- Crear un RDS PostgreSQL que solo acepta conexiones desde la EC2 creada, y conectar la API cambiando solo variables de entorno.

## Contenido de `taskflow-api`
- Controladores REST
- Manejo del repositorio con JPA / Spring Data
- Seguridad con JWT

## Presupuesto
Se creo un budget con limite mensual de $5 dolares, envia una notificación por correo cuando el gasto se acerca o supera ese limite.

![budget](./docs/images/budget.png)

## Asegurar la cuenta
Se creo un usuario `IAM`. Se considera una buena práctica usar una cuenta IAM y no una `root` porque la ultima tiene el poder absoluto sobre la cuenta de AWS. Si la cuenta root fuera comprometida por alguna razón, perderíamos el acceso de forma permanente a la cuenta AWS, con un usuario `IAM` se puede revocar/eliminar sin afectar la cuenta raíz, además podemos ajustar permisos. El usuario `IAM` es el que se utiliza día a día.

![usuario IAM](./docs/images/IAM-user.png)

## Lanzar la EC2
Se crea una instancia EC2 siguiendo la ruta: Barra de búsqueda → `EC2` → en el menú izquierdo Instances  botón naranja Launch instances. Se llena la información del formulario con la configuración de la instancia:
- Name and tags: Name: `taskflow-ec2`. Es el nombre de la instancia.
- Application and OS Images (Amazon Machine Image): Amazon Linux 2023 AMI. Una AMI contiene el sistema operativo, servidor de aplicaciones y aplicaciones que usara la instancia.
- Instance type: `t3.micro`. Es la maquina donde correra la instancia, en este caso elegimos la más basica.
- Key pair (login): Se crea un archivo `.pem` que nos servira como llave privada SSH, permite que nos conectemos de forma segura a la instancia EC2 mediante SSH. Crea una llave pública que AWS guarda e instala automáticamente en la instancia EC2 y una llave privada (.pem) que se descarga en la computadora personal.

![Creacion de key pair (login)](./docs/images/key-pair.png)

- Network settings: Creamos un security group con una regla de tipo SSH con un source type de My Ip y otra de tipo Custom TCP. De esta forma solo mi ip podrá conectarse a la instancia EC2 mediante SSH y todo el mundo podrá acceder al puerto 8080. Si en source type hubiera elegido Anywhere (0.0.0.0/0) en lugar de My Ip, la puerta de mi instancia hubiera quedad abierta a cualquier IP de Internet y eso significa estar expuesto a ataques automatizados.

![Creacion de security group](./docs/images/security-group-rules.png)

- Configure storage: 8 GiB gp3. Es la capacidad y tipo de almacenamiento que tendrá la instancia.

Una vez creada la instancia, al darle click podremos ver la información que configuramos. La public IPv4 address nos servira al momento de conectarnos a la instancia mediante SSH, esta cambia cada vez que detenemos y volvemos a iniciar la instancia por lo que hay que estar al tanto de esto.

![EC2 Instance summary](./docs/images/instance-settings.png)

Si damos click en la pestaña Security de la instancia, se observa el security group que creamos (`taskflow-ec2-sg`) y sus respectivas reglas.

![EC2 Instance summary security groups](./docs/images/instance-settings-sg-rules.png)

## Conectar por SSH
Desde la carpeta donde guardamos el archivo .pem se ejecutan los siguientes comandos: 

```
chmod 400 taskflow-key.pem
```
Este comando cambia los permisos del archivo para que solo el dueño pueda leerlo (mediante el usuario del sistema operativo) y nadie pueda escribirlo o ejecutarlo.

```
ssh -i taskflow-key.pem ec2-user@<TU-IP>
```
Este comando es el que realiza la conexión a la instancia EC2, se tiene que remplazar "TU-IP" por la IPv4 address que aparece en los detalles de la instancia.

Una vez dentro de la instancia, podemos ejecutar los siguientes comandos para asegurarnos que todo funciona correctamente:

```
uname -a
```
Muestra información del kernel y sistema operativo.

```
curl ifconfig.me
```
Devuelve la IP pública.
```
free -m 
```
Muestra el uso de memoria RAM, en megabytes.

## Instalar Java en la instancia EC2
Para instalar Java en nuestra instancia, usamos el comando:
```
sudo dnf install -y java-21-amazon-corretto-headless
```
Para verificar la correcta instalación de Java podemos usar el comando:
```
java -version
```
Si obtenemos un mensaje que contenga algo como: openjdk version "21.0.12.1", entonces se confirma que la instalación fue exitosa.

## Copiar el .jar de `taskflow-api` a la instancia de EC2
Primero tenemos que compilar el proyecto localmente con el comando: 
```
mvn -q -DskipTests package
```
Con -DskipTests nos saltamos la ejecución de los tests unitarios durante el build. Como resultado se genera un archivo .jar.

Despues, necesitamos copiar el archivo .jar hacia la instancia EC2, mediante SSH:
```
scp -i <ruta-a-tu>/taskflow-key.pem target/taskflow-api-*.jar ec2-user@<TU-IP>:~/taskflow-api.jar
```
En el comando se especifica la ruta donde guardamos el archivo .pem , los archivos .jar que queremos subir a EC2 y el usuario ec2-user con la IP pública de la instancia y la ruta `~/taskflow-api.jar`

Para asegurarnos que el mismo archivo que tenemos en la computadora es el que se subio a EC2, podemos ejecutar los comandos: 
```
shasum -a 256 target/taskflow-api-*.jar
```
Se ejecuta en la computadora.
```
sha256sum ~/taskflow-api.jar 
```
Se ejecuta en la instancia EC2.

Se producen dos hashes que deben ser idénticos. Asi es como sabemos que es el mismo archivo, no se recompiló nada.
![shasum](./docs/images/shasum.png)
![sha256sum](./docs/images/sha256sum.png)

## Primer arranque público
Para correr la aplicación, se usaron los siguientes comandos:
```
nohup java -jar taskflow-api.jar > app.log 2>&1 &
```
Ejecuta la aplicación Spring Boot que fue empaquetada como jar ejecutable y guarda los logs en un archivo llamado `app.log`, retorna el Process ID del proceso que queda corriendo en segundo plano.

```
tail -f app.log
```
Muestra en tiempo real el contenido del archivo `app.log` en tiempo real. Tenemos que esperar a que aparezca `Started TaskflowApiApplication`. Una vez que aparezca ya se puede abrir el swagger de la aplicación y ejecutar los endpoints definidos.
![swagger primer verson](./docs/images/swagger-manual.png)

## Crear base de datos RDS 
Hasta este momento estabamos utilizando una base de datos embebida que corría dentro de la misma JVM, ahora creamos una base de datos RDS para que la información de las tasks viva ahi.
Para crear una base de datos RDS se tiene que seguir el camino: Barra de búsqueda → RDS → menú izquierdo Databases → el botón naranja Create database y elegir Full configuration.

La configuración de la base de datos es la siguiente: 
- Engine options: PostgreSQL
- Choose a database creation method: Full configuration
- Templates: Free tier
- Availability and durability: Single-AZ DB instance deployment (1 instance). Para este proyecto no existira redundancia de la información, vivira en una sola instancia.
- Settings: Engine version PostgreSQL 18.3-R2
- Credentials Settings: Se definió el master username y master password para acceder a la base de datos.
- Instance configuration: Burstable classes (includes t classes)
- Storage: General Purpose SSD (gp3) con una capacidad minima de almacenamiento de 20GB, se desactiva Enable storage autoscaling para que la capacidad de almacenaiento no aumente incluso despues de que se excedio la cantidad especificada.
- Connectivity: Don't connect to an EC2 compute resource, se deniega el acceso publico y se crea un nuevo VPC security group con el nombre `taskflow-rds-sg`
- Additional credentials settings (dentro de Credentials Settings): Password authentication
Additional configuration: Initial database name: taskflow

Al crearse la base de datos, podemos entrar a ver sus detalles.
![Base de datos RDS](./docs/images/rds-settings.png)

Se puede observar que la base de datos RDS no tiene un IP pública, esto esta definido asi para que no pueda recibir tráfico desde internet, ya que eso haría que fuera un blanco de todo tipo de ataques. Nuestra instancia EC2 puede acceder a la base de datos gracias a que viven dentro de la misma VPC (Virtual Private Cloud), es básicamente una red privada dentro de AWS. Gracias a esto, la instancia EC2 y la base de datos RDS pueden comunicarse entre sí usando sus IPs privadas.

## S3 Bucket
Para crear un bucket, podemos seguir la ruta: Barra de búsqueda → S3 → Create bucket.

Configuración del bucket:
- Bucket type: General purpose.
- Bucket namespace: Global namespace.
- Bucket name: taskflow-artefacto-danieldegollado2. 
- Block Public Access settings: Block all public access.

Una vez creado el bucket, subimos el archivo .jar que se genero al compilar el proyecto localmente.

![jar en bucker](./docs/images/s3-uploaded-jar.png)

Si tratamos de acceder a la Object URL del bucket (ubicado en la ruta: click al nombre del jar → pestaña Properties → bloque Object overview → Object URL.), nos regresara un XML con el mensaje `AccessDenied`, esto quiere decir que el bucket es privado.

![xml access denied](./docs/images/xml-access-denied.png)

Para tener acceso al .jar podemos crear un presigned URL, el cual es una url que tiene un tiempo de duración que nosotros definimos. Al pegar la url en una pestaña nueva, automáticamente se descarga el jar.

## Conectar la API al RDS
Primero tenemos que generar un secreto JWT, ya que el perfil docker al que vamos a cambiar lo solicita al leer `${JWT_SECRET}`. Podemos generar el secreto con el siguiente comando:
```
openssl rand -hex 32 
```
Se imprimen 64 caracteres hexadecimales, esto se tiene que copiar y guardar en algun otro lugar seguro.

Una vez tenemos el secreto JWT, podemos ejecutar el siguiente comando para hacer la conexión de la instancia EC2 con la base de datos RDS.
```
nohup java -jar taskflow-api.jar \
  --spring.profiles.active=docker \
  --DB_HOST=<endpoint-rds> --DB_PORT=5432 --DB_NAME=taskflow \
  --DB_USER=taskflow --DB_PASSWORD='<la tuya>' \
  --JWT_SECRET='<64 caracteres NUEVOS, no los de dev>' \
  > app.log 2>&1 &
```
Lo que este comando hace es, primero cambiar el spring profile activo a `docker` (el perfil se llama asi, no tiene nada que ver con docker) porque anteriormente estabamos usando el profile `h2` para usar H2 como base de datos en memoria. El perfil `docker` utiliza postgresql. Ademas puede configurar diferentes atributos, como el host, name, user, password y jwt secret (los 64 carácteres hexadecimales generados anteriormente).

Al ejecutar el comando me sale el siguiente error: 

![ec2 rds connection error](./docs/images/conexion-rds-ec2-error.png)

Es un error de red `timed out`: el paquete no recibe respuesta porque el Security Group de RDS no tiene una regla que permita el tráfico entrante desde la EC2, así que lo descarta silenciosamente. Se soluciona agregando una regla inbound en el Security Group del RDS: tipo PostgreSQL (puerto 5432), y como origen el Security Group de la EC2.

Hay que ir a la ruta: EC2 → menú izquierdo, bloque Network & Security → Security Groups → click a taskflow-rds-sg (RDS creo este security group) → pestaña Inbound rules → Edit inbound rules → Add rule:

![rds security añadir regla inbound](./docs/images/rds-inbound-rules.png)

Esta inbound rule le dice al Security Group de RDS que acepte tráfico entrante en el puerto
5432 (PostgreSQL) proveniente de cualquier instancia que tenga asignado el Security Group
de la EC2.

Ahora la aplicación funciona sin ningun problema. El .jar está desplegado y corriendo en la instancia EC2, la cual se comunica con la base de datos RDS por IP privada dentro de la misma VPC, gracias a la inbound rule del Security Group de RDS que permite tráfico en el puerto 5432 desde el Security Group de la EC2.

![app trabajando ec2 con rds](./docs/images/taskflow-rds-ec2-working.png)

## Integrador y limpieza

### Smoke test
Ya con la aplicación TaskFlow en AWS, haremos una prueba básica para verificar que uno de los endpoints funcione correctamente. En este caso probaremos el endpoint `tasks`.

Primero tenemos que hacer login, se utilizara el usuario de ana.

![ec2 con rds login](./docs/images/ec2-rds-login-test.png)

La API nos regresa un token, que usaremos para recibir autorización para llamar a los metodos `GET` y `POST` del endpoint `tasks`.

![ec2 con rds autorizacion](./docs/images/ec2-rds-authorized.png)

`POST` de una tarea.

![post de una tarea](./docs/images/POST-task.png)

`GET` de esa tarea.

![get de una tarea](./docs/images/GET-task.png)


### Topologia
| Recurso | Identificador / Valor |
|---|---|
| Región | `us-east-1` |
| EC2 — Instance ID | `i-0d6bde941aea44ed8` |
| EC2 — Nombre | `taskflow-ec2` |
| EC2 — Security Group | `taskflow-ec2-sg` |
| RDS — Endpoint (ofuscado) | `database-1.XXXXXXXXXXXX.us-east-1.rds.amazonaws.com` |
| RDS — Puerto | `5432` |
| RDS — Security Group | `taskflow-rds-sg` |
| S3 — Bucket | `taskflow-XXXXXXXXXX` |

### Limpieza
 
Despues de terminar este proyecto de prueba, conviene borrar todos los recursos que ya no vayan a ser necesarios.

- Instancia EC2 borrada

![ec2 terminada](./docs/images/terminated-ec2.png)

- Base de datos RDS borrada

![rds borrada](./docs/images/terminated-rds.png)

- Vaciar bucket

![s3 bucket vacio](./docs/images/s3-empty.png)

- Borrar keypairs

![keypair borrado](./docs/images/deleted-keypairs.png)

- Borrar security groups de EC2 y RDS

![security groups de ec2 y rds borrados](./docs/images/deleted-sg.png)

# AWS: DynamoDB, CodePipeline y CodeDeploy

## Objetivo
En el proyecto pasado, se ejecutaron muchos comandos para poder poner la app de TaskFlow en AWS, en este proyecto se escriben solo una vez como scripts. Solo sera necesario hacer un `git push` y ver como la aplicación se despliega sola.

## Relanzar la EC2
La instancia EC2 que se destruyo en el proyecto pasado se volverá a crear en este con dos cosas nuevas: IAM instance profile y Tag Name = taskflow-ec2.

Creamos un rol para la EC2.

![ec2 rol](./docs/images/ec2-role.png)

El rol `taskflow-ec2-role` tiene un permission policy `AmazonS3ReadOnlyAccess`, con este permiso el agente de CodeDeploy que corre dentro de la instancia puede leer el artefacto del bucket.

Para crear la instancia EC2 se sigue la misma ruta: EC2 → Instances → Launch instances.

Configuración (La misma que el proyecto pasado, con algunas diferencias):
- Name and tags: Name: `taskflow-ec2`. Es el nombre de la instancia.
- Application and OS Images (Amazon Machine Image): Amazon Linux 2023 AMI. Una AMI contiene el sistema operativo, servidor de aplicaciones y aplicaciones que usara la instancia.
- Instance type: `t3.micro`. Es la maquina donde correra la instancia, en este caso elegimos la más basica.
- Key pair (login): Se crea un archivo `.pem` que nos servira como llave privada SSH, permite que nos conectemos de forma segura a la instancia EC2 mediante SSH. Crea una llave pública que AWS guarda e instala automáticamente en la instancia EC2 y una llave privada (.pem) que se descarga en la computadora personal.
- Security group: Ahora creamos un security group desde que creamos la instancia EC2. Es el security group `taskflow-ec2-sg`, el puerto 22 solo puede acceder `My Ip` para SSH y el puerto 8080 esta abierto `0.0.0.0/0`.
- IAM instance profile: Se elige el rol `taskflow-ec2-role` creado previamente.

La nueva instancia EC2 es creada correctamente.

![nueva ec2](./docs/images/new-ec2.png)

Como es una nueva instancia, la `Public IPv4 address` es diferente. Para entrar por SSH a la instancia volvemos a utilizar el comando:

```
ssh -i taskflow-key.pem ec2-user@100.61.151.79
```
Como esta instancia es nueva y esta vacia, tenemos que volver a instalar Java 21.
```
sudo dnf install -y java-21-amazon-corretto-headless
```
Esta vez no se subira el .jar con un comando, el responsable de subirlo a la instancia es el pipeline.

## DynamoDB
En el proyecto pasado se utilizaron las bases de datos H2 y PostgreSQL, esta vez se utilizara `DynamoDB`. Es una base de datos `NoSQL` que trabaja con un modelo de clave-valor gestionado.
Esta base de datos utiliza dos claves principales: Partition key que decide en que servidor físico vive el dato y Sort key que ordena lo que hay dentro de esa partición.

Utilizando `AWS CLI` configurada con el usuario IAM `taskflow-admin` creado anteriormente, se creara una nueva tabla, insertaremos y leeremos datos.

Para crear una nueva tabla se ejecuta el comando:
```bash
aws dynamodb create-table \
  --table-name taskflow-eventos \
  --attribute-definitions \
      AttributeName=taskId,AttributeType=S \
      AttributeName=fechaHora,AttributeType=S \
  --key-schema \
      AttributeName=taskId,KeyType=HASH \
      AttributeName=fechaHora,KeyType=RANGE \
  --billing-mode PAY_PER_REQUEST
```
Se definen los atributos taskId y fechaHora. El atributo taskId tiene un KeyType `HASH`, esto significa que será la Partition Key que decide en que servidor vive el dato, el atributo fechaHora tiene un KeyType `RANGE` porque es la Sort Key que ordena lo que hay dentro de esa partición.

![eventos table](./docs/images/table.png)

Para crear un evento se ejecuta el comando:

```bash
aws dynamodb put-item --table-name taskflow-eventos --item '{
  "taskId":    {"S": "T-001"},
  "fechaHora": {"S": "2026-09-08T09:15:00Z"},
  "tipo":      {"S": "CREADA"},
  "autor":     {"S": "ana"}
}'
```
Cada valor lleva su tipo `"S"` (equivalente a), este es básicamente el equivalente a String.

Se agregan mas items. 

```bash
aws dynamodb put-item --table-name taskflow-eventos --item '{"taskId":{"S":"T-001"},"fechaHora":{"S":"2026-09-08T10:02:00Z"},"tipo":{"S":"ASIGNADA"},"autor":{"S":"admin"},"detalle":{"S":"asignada a luis"}}'
aws dynamodb put-item --table-name taskflow-eventos --item '{"taskId":{"S":"T-001"},"fechaHora":{"S":"2026-09-08T11:40:00Z"},"tipo":{"S":"EN_PROGRESO"},"autor":{"S":"luis"}}'
aws dynamodb put-item --table-name taskflow-eventos --item '{"taskId":{"S":"T-002"},"fechaHora":{"S":"2026-09-08T09:30:00Z"},"tipo":{"S":"CREADA"},"autor":{"S":"luis"},"detalle":{"S":"Revisar el contrato de la API"}}'
aws dynamodb put-item --table-name taskflow-eventos --item '{"taskId":{"S":"T-002"},"fechaHora":{"S":"2026-09-08T16:05:00Z"},"tipo":{"S":"COMPLETADA"},"autor":{"S":"luis"}}'
```

Asi se ven todos los items creados desde la consola.

![eventos items](./docs/images/items.png)

### `Query` contra `Scan`
Si ejecutamos un `query` por `taskId` con `--return-consumed-capacity TOTAL`.

```bash
aws dynamodb query --table-name taskflow-eventos \
  --key-condition-expression "taskId = :t" \
  --expression-attribute-values '{":t":{"S":"T-001"}}' \
  --return-consumed-capacity TOTAL
```

Devuelve como resultado: 

```bash
"Count": 3,
    "ScannedCount": 3,
    "ConsumedCapacity": {
        "TableName": "taskflow-eventos",
        "CapacityUnits": 0.5
    }
```
Devolvió y escaneo 3 eventos de los 5 totales, la razón de esto es que solo lee la partición `T-001` que especificamos en el `query` y regresa 3 eventos ordenados por la `Sort Key` `fechaHora`.

Ahora, para realizar un `scan` se ejecuta el comando:

```bash
aws dynamodb scan --table-name taskflow-eventos \
  --filter-expression "tipo = :x" \
  --expression-attribute-values '{":x":{"S":"COMPLETADA"}}' \
  --return-consumed-capacity TOTAL
```

En este comando se busca que nos devuelva los eventos marcados como `COMPLETADA`, el resultado es el siguiente:

```bash
 "Count": 1,
    "ScannedCount": 5,
    "ConsumedCapacity": {
        "TableName": "taskflow-eventos",
        "CapacityUnits": 2.0
    }
```

Solo devolvió 1 evento completado pero escaneo 5 en total. 

En esta comparación queda claro cual es el funcionamiento y en que se diferencian `query` y `scan`. Un `query` solo lee lo correspondiente a una partición, mientras que `scan` lee la tabla entera. En cuanto a la forma de filtrar la información, `query` lo hace al leer, por clave y `scan` filtra despues de haber leído toda la tabla.
Tambien hay una gran diferencia en el costo que tiene cada uno. Mientras mas elementos haya en una tabla, el costo de `scan` aumenta, el costo de `query` depende de los elementos de una partición (que deberían ser menos que los elementos totales en la tabla).

Si se quiere hacer una busqueda, es mas recomendable utilizar `query`. En DynamoDB `scan` es una herramienta de
mantenimiento y de exportación, no de consulta.