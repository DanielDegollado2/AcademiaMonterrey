# Spring Security: Basic, JWT y OAuth2
Spring Security sirve para proteger nuestra API, nos permite añadir una capa de seguridad que a su vez esta compuesta por las capas de autenticación y de autorización, de esta forma podemos definir que endpoints queremos que solo puedan ser usados por usuarios autenticados y, si nos ponemos mas estrictos, solo por usuarios que tengan un rol especifico.

Hablare sobre tres tipos de implementar Spring Security: Basic, JWT y OAuth2.

## Basic
Esta forma de seguridad es mas simple, cada vez que el usuario quiera llamar a algun endpoint tendra que proporcionar su usuario y contraseña. Esto significa que sus credenciales viajaran en cada llamada que realice a la API. Primero BasicAuthenticationFilter verifica que las credenciales pertenezcan a un usuario existente en la base de datos que se especifique y despues, AuthorizationFilter que ese usuario tenga el rol necesario para poder llamar al endpoint que esta solicitando. Como las credenciales se envian en cada llamada a la API, es necesario que las peticiones vayan con HTTPS; con TLS cualquiera que trate de interceptarlas solo verían ruído.

### Configuracion

Para poder añadir seguridad a nuestra API, no es necesario modificar los archivos relacionados con el funcionamiento de esta, podemos agregar un paquete extra donde se realice toda la configuración correspondiente a esta nueva capa de seguridad.

Dentro de la clase SecurityConfig se incorporan dos metodos:

```java
@Bean
    public UserDetailsService userDetailsService(DataSource theDataSource) {

        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(theDataSource);

        // como buscar un usuario: debe devolver username, password y si esta activo
        theUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?");

        // como buscar sus roles: debe devolver username y rol
        theUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return theUserDetailsManager;
    }
```

En el metodo userDetailsService es donde le decimos a Spring que los usuarios viven en la base de datos, asi como el nombre de las tablas donde se ubican los usuarios y sus roles, ya que Spring utiliza las tablas "users" y "authorities" por defecto. El unico parametro que reciben las dos consultas es el username.

```java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                // leer: cualquier empleado
                .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                // crear y modificar: solo managers
                .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PATCH, "/api/employees/**").hasRole("MANAGER")
                // borrar: solo admins
                .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
                // cualquier otra cosa: al menos hay que estar autenticado
                .anyRequest().authenticated());

        // usar autenticacion HTTP Basic
        http.httpBasic(Customizer.withDefaults());

        // Desactivamos CSRF porque esta es una API REST sin sesiones ni cookies.
        // CSRF protege contra que OTRA pagina use la cookie de sesion del navegador;
        // si no hay cookie de sesion, no hay nada que robar. En una app web con
        // formularios y login por sesion, CSRF se deja ENCENDIDO.
        http.csrf(csrf -> csrf.disable());

        // Sin sesion en el servidor: cada request llega con sus credenciales
        // y se autentica desde cero. Eso es ser "stateless".
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
```
En el metodo filterChain es donde se configura la cadena de filtros para definir que rol tiene acceso a que metodo HTTP y a que endpoint. En esta configuracion se puede observar como los usuarios con el rol "EMPLOYEE" solo pueden ejecutar endpoints con el metodo HTTP GET, para poder hacer modificaciones a la base de datos con los metodos HTTP POST, PUT y PATCH es necesario tener el rol "MANAGER" y para poder borrar elementos de la base de datos con el metodo HTTP DELETE se necesita tener el rol "ADMIN" ya que es una peticion que no cualquiera deberia poder hacer.
Tambien se realizan otras configuraciones como definir la autenticacion HTTP Basic, desactivar CSRF ya que esta API no utiliza sesiones ni cookies, y se define que no hay sesion en el servidor; esto quiere decir que cada request tiene que mandar las credenciales del usuario y autenticarse desde cero porque no tiene un estado previo al ser "stateless".

## JWT
Este mecanismo de seguridad no necesita que las credenciales del usuario se manden en cada peticion a la API. La forma en la que funciona es la siguiente: el usuario llama a un endpoint de autenticacion, en esta peticion se envian las credenciales del usuario, si las credenciales son validadas correctamente, la API regresara un token firmado con un payload y una duracion. De esta forma, cualquier otra peticion a la API ya no necesitara las credenciales, sino un token vigente recibido anteriormente al autenticarse. El token incluira informacion llamada payload, donde se puede observar informacion como el rol del usuario, quien lo emitio, cuando se emitio, fecha de expiracion, etc.

### Configuracion
Dentro de la clase SecurityConfig se encuentran lo siguiente:

```java
  @Value("${rsa.public-key}")
    private RSAPublicKey publicKey;

    @Value("${rsa.private-key}")
    private RSAPrivateKey privateKey;
```

Los archivos .pem que contienen las llaves para firmar y validar tokens se convierten en objetos de llave RSA

```java
  @Bean
    public UserDetailsService userDetailsService(DataSource theDataSource) {

        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(theDataSource);

        theUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?");

        theUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?");

        return theUserDetailsManager;
    }
```

El metodo userDetailsService se mantiene para que Spring sepa de que tablas salen los usuarios y roles, al momento de hacer login es necesario que se sigan comprobando el usuario y contraseña contra la base de datos. Esto ya no se ejecuta en cada peticion como cuando se utiliza Basic, solo una vez.

```java
  @Bean
    @Order(1)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/api/auth/**");
        http.authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
```

Cuando se hace una llamada al endpoint con la ruta "/api/auth/**" y al tener @Order(1) esta es la cadena que Spring revisa primero. Aqui es donde se utiliza httpBasic para decodificar las credenciales y mandar a llamar a userDetailsService para verificar que sean credenciales validas y cargar los roles. Si la autenticacion se completa correctamente, se llama al metodo login de AuthController.

```java
 @PostMapping("/login")
    public TokenResponse login(Authentication authentication) {

        Instant ahora = Instant.now();

        // Los roles que salieron de la tabla "roles": ROLE_EMPLOYEE, ROLE_MANAGER...
        //
        // El filtro startsWith("ROLE_") NO es decorativo. Spring Security 7 agrega por su
        // cuenta autoridades que describen COMO te autenticaste (FACTOR_PASSWORD, y otras
        // FACTOR_* si usas multifactor). Son utiles dentro del servidor, pero no tienen
        // nada que hacer dentro de un token que viaja al cliente. Sin este filtro, el
        // token saldria con "roles":["ROLE_EMPLOYEE","FACTOR_PASSWORD"].
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE_"))
                .collect(Collectors.toList());

        // El PAYLOAD del token. Todo esto viaja en claro dentro del token:
        // va FIRMADO, no cifrado. Nunca pongas aqui datos secretos.
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("security-jwt")                        // quien lo emitio
                .issuedAt(ahora)                               // cuando  (claim "iat")
                .expiresAt(ahora.plusSeconds(ttlSeconds))      // hasta cuando (claim "exp")
                .subject(authentication.getName())             // de quien es (claim "sub")
                .claim("roles", roles)                         // que puede hacer
                .build();

        // firmar con RS256 = RSA + SHA-256, usando la llave privada
        JwsHeader header = JwsHeader.with(org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256).build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();

        return new TokenResponse(token, "Bearer", ttlSeconds, authentication.getName());
    }
```
Este metodo no recibe el usuario y contraseña como un RequestBody, porque la cadena loginFilterChain fue la que se encargo de validar las credenciales e inyectar el resultado en el parametro Authentication. Se necesitan credenciales validas para poder llegar a este metodo. El metodo login tiene varias funciones, primero filtra las autoridades para que solo queden las que empiecen con "ROLE_" ya que Spring Security 7 agrega autoridades por su cuenta que realmente no son necesarias en el token que se esta por firmar. Despues del filtrado de autoridades, se define el Payload del token, esta es la informacion que viaja dentro del token firmado, no se recomienda que se ponga datos que deben ser secretos ya que no estaran cifrados. Finalmente, se firma el token con el algoritmo RS256, usando la llave privada y se regresa para que pueda ser utilizado al momento de hacer peticiones a la API.

Cuando se hace una peticion a la API, se tiene que proporcionar un token valido. El metodo apiFilterChain es el encargado de recibir el token y validarlo.

```java
 @Bean
    @Order(2)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET,    "/api/clientes").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET,    "/api/clientes/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST,   "/api/clientes").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PUT,    "/api/clientes").hasRole("MANAGER")
                .requestMatchers(HttpMethod.PATCH,  "/api/clientes/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/clientes/**").hasRole("ADMIN")
                .anyRequest().authenticated());

        // Aqui esta el cambio de fondo: ya no se acepta HTTP Basic.
        // Esta cadena solo entiende "Authorization: Bearer <token>".
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
```

Al recibir una peticion con el token, este metodo ejecuta oauth2ResourceServer().jwt(...), basicamente lo que esto hace es leer el header Authorization: Bearer <token>, extrae el token sin el prefijo "bearer" y llama al metodo jwtDecoder()

```java
@Bean
public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
}
```

Aqui es donde el decoder utiliza la llave publica para verificar la firma del token, si el token se firmo con la llave privada correspondiente, la verificacion pasa sin problemas. Si alguien intento modificar el token o lo firmo con otra llave, la verificacion fallara y arrojara un httpStatus de 401. Ademas valida que el token no haya expirado y que el formato sea valido.
Si la firma es valida, se ejecuta jwtAuthenticationConverter()

```java
private JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }
```

Este metodo traduce los claim incluidos en el token a roles de Spring para que despues se pueda verificar en el metodo apiFilterChain si el usuario tiene autorizacion de realizar la peticion que esta haciendole a la API

### Lo que sucede cuando no se proporciona un token o se proporciona uno vencido
$ curl -i http://localhost:8070/api/clientes
HTTP/1.1 401
WWW-Authenticate: Bearer resource_metadata="http://localhost:8070/.well-known/oauth-protected-resource"
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Fri, 28 Aug 2026 21:47:10 GMT

Se puede observar un HTTP status code 401, ya que en la peticion no se envio un token firmado. Esto nos confirma que la API esta asegurada y siempre se necesitara proporcionar un token.

