-- Crear base de datos
CREATE DATABASE ecommerce_db;

-- Crear tabla Cliente
CREATE TABLE cliente (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    telefono BIGINT,
    fecha_registro DATE NOT NULL,
    direccion_envio VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_cliente_correo (correo)
);

-- Crear tabla Pedido
CREATE TABLE pedido (
    id INT NOT NULL AUTO_INCREMENT,
    fecha_creacion DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    total DOUBLE,
    direccion_envio VARCHAR(255),
    metodo_pago VARCHAR(20),
    id_cliente INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

-- Crear tabla Factura
CREATE TABLE factura (
    id INT NOT NULL AUTO_INCREMENT,
    numero_factura BIGINT NOT NULL,
    subtotal DOUBLE NOT NULL,
    impuestos DOUBLE NOT NULL,
    total DOUBLE NOT NULL,
    id_pedido INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_factura_id_pedido (id_pedido),
    UNIQUE KEY uk_factura_numero (numero_factura),
    CONSTRAINT fk_factura_pedido
        FOREIGN KEY (id_pedido) REFERENCES pedido(id)
        ON DELETE CASCADE
);
