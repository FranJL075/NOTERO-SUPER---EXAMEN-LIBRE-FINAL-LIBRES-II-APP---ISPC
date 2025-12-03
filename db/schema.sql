-- Script de creación de la base de datos PresuMarket

CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    contrasena_hash VARCHAR(255) NOT NULL,
    ubicacion VARCHAR(150)
);

CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    codigo VARCHAR(64) UNIQUE NOT NULL,
    precio NUMERIC(10,2) NOT NULL CHECK (precio>=0),
    favorito BOOLEAN DEFAULT FALSE
);

CREATE TABLE listas (
    id_lista SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    limite_presupuesto NUMERIC(10,2),
    total NUMERIC(10,2) DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT NOW(),
    id_usuario INT NOT NULL REFERENCES usuarios(id_usuario)
);

CREATE TABLE detalles_lista (
    id_detalle SERIAL PRIMARY KEY,
    id_lista INT NOT NULL REFERENCES listas(id_lista) ON DELETE CASCADE,
    id_producto INT NOT NULL REFERENCES productos(id_producto),
    cantidad INT NOT NULL CHECK (cantidad>0),
    subtotal NUMERIC(10,2) NOT NULL,
    UNIQUE(id_lista, id_producto)
);

CREATE TABLE negocios_promocionados (
    id_negocio SERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    descuento NUMERIC(5,2) NOT NULL CHECK (descuento BETWEEN 0 AND 100),
    presupuesto_base NUMERIC(10,2),
    direccion VARCHAR(150)
);

CREATE TABLE listas_promocionadas (
    id_lista INT REFERENCES listas(id_lista) ON DELETE CASCADE,
    id_negocio INT REFERENCES negocios_promocionados(id_negocio),
    PRIMARY KEY (id_lista, id_negocio)
);
