# Automatización de Pruebas – Santander México (Selenium + TestNG)

## Descripción del proyecto
Automatización con Selenium y TestNG sobre el sitio de Santander México, que valida el menú de la página principal (Personas, Empresas, PyMes, Banca Privada y Acerca del Banco).

## Stack tecnológico
- Java 21
- Maven
- Selenium
- TestNG

## Estructura del proyecto
```
selenium-intermedio-testng-santander-danieldegollado/
├── pom.xml
├── testng.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       ├── base/
    │       │   └── BasePage.java
    │       ├── pages/
    │       │   ├── DashboardPage.java
    │       │   ├── PersonasMenu.java
    │       │   ├── EmpresasMenu.java
    │       │   ├── PymesMenu.java
    │       │   ├── AcercaDelBancoMenu.java
    │       │   └── AutomationPracticePage.java
    │       └── utils/
    │           └── DriverFactory.java
    └── test/
        ├── java/
        │   ├── base/
        │   │   └── BaseTest.java
        │   ├── listeners/
        │   │   └── ScreenshotListener.java
        │   └── tests/
        │       ├── PersonasTest.java
        │       ├── EmpresasTest.java
        │       ├── PymesTest.java
        │       ├── AcercaDelBancoTest.java
        │       ├── BancaPrivadaTest.java
        │       └── AutomationPracticeTest.java
        └── resources/
            └── files/
                └── sample-upload.txt
```
## Descripción de las clases mas importantes

Proyecto basado en la estructura clara del framework POM de referencia:

- `base.BasePage`: operaciones reutilizables de todas las páginas (Operaciones basadas en locators `By` que ayudan a ejecutar operaciones sobre elementos web: Encontrarlo, darle click, mandarle un valor, obtener su texto, seleccionarlo por su texto, verificar si esta seleccionado, verificar si existe, verificar si redirige a la url correcta al darle click).
- `utils.DriverFactory`: creación y configuración de Chrome.
- `pages.DashboardPage`: localizadores y acciones de la página principal, al dar click sobre cada botón (Personas, Empresas, Pymes y Acerca del Banco) se retorna su respectivo menú. El botón Banca Privada retorna un booleano dependiendo de si se redirigió a la url definida al darle click.
- `pages.PersonasMenu` : localizadores y acciones del menú Personas, cada acción retorna un booleano dependiendo de si se redirigió a la url definida al dar click sobre un link.
- `pages.EmpresasMenu` : localizadores y acciones del menú Empresas, cada acción retorna un booleano dependiendo de si se redirigió a la url definida al dar click sobre un link.
- `pages.PymesMenu` : localizadores y acciones del menú Pymes, cada acción retorna un booleano dependiendo de si se redirigió a la url definida al dar click sobre un link.
- `pages.AcercaDelBancoMenu` : localizadores y acciones del menú Acerca del Banco, cada acción retorna un booleano dependiendo de si se redirigió a la url definida al dar click sobre un link.
- `pages.AutomationPracticePage`: localizadores y acciones de la página. Archivo de ejemplo fuera del contexto del proyecto de Santander, se ejecuta sobre un sitio distinto y permite realizar diferentes interacciones generales de Selenium: formularios y controles con subida de archivos, interacción con tabla de productos, manejo de alertas, y acciones avanzadas (menú hover, doble clic, drag and drop, apertura/cierre de pestañas nuevas). 
- `base.BaseTest`: apertura y cierre del navegador en TestNG.
- `tests.PersonasTest`: valida que la pagina sea redirigida a la url correspondiente al hacer click sobre cada link del menú Personas. Los tests están agrupados por sección del menú (Crédito y financiamiento, Canales digitales, Tipo de cuenta, Ahorro e inversión, Seguros, Información y ayuda, Beneficios).
- `tests.EmpresasTest`: valida que la pagina sea redirigida a la url correspondiente al hacer click sobre cada link del menú Empresas.
- `tests.PymesTest`: valida que la pagina sea redirigida a la url correspondiente al hacer click sobre cada link del menú Pymes.
- `tests.BancaPrivadaTest`: valida que la pagina sea redirigida a la url correspondiente al hacer click sobre el botón Banca Privada del menú principal.
- `tests.AcercaDelBancoTest`: valida que la pagina sea redirigida a la url correspondiente al hacer click sobre cada link del menú Acerca Del Banco.
- `tests.AutomationPracticeTest`: es un archivo de ejemplo, fuera del contexto del proyecto de Santander. Se ejecuta sobre un sitio distinto usado para testear interacciones generales de Selenium: formularios y controles con subida de archivos, interacción con tabla de productos, manejo de alertas, y acciones avanzadas (menú hover, doble clic, drag and drop, apertura/cierre de pestañas nuevas).

## Reportes
Después de correr los tests, se genera una carpeta llamada `test-output` donde se pueden encontrar diferentes archivos donde se observa cuales fueron los resultados de la ejecución. Los resultados se pueden observar en: 
- `test-output/index.html`: Muestra los tests que corrieron, en que orden lo hicieron, el tiempo que tardaron y si pasaron o no
- `test-output/emailable-report.html`: Reporte compacto, esta pensado para compartir por correo o adjuntar directamente. Resume resultados, duración y estado de cada test.
- `test-output/screenshots/`: Capturas de pantalla generadas automáticamente por `ScreenshotListener`, sirve para observar el estado visual de la página cuando algún test falla

## Ejecutar pruebas en Eclipse
1. Importar como `Existing Maven Project`.
2. Ejecutar `Maven > Update Project`.
3. Abrir `testng.xml`.
4. Ejecutar `Run As > TestNG Suite`.

## Ejecutar pruebas con Maven

```bash
mvn clean test
```

Ejecución sin mostrar Chrome:

```bash
mvn clean test -Dheadless=true
```

Generar una falla intencional y comprobar la captura:

```bash
mvn clean test -DforceFailure=true
```
