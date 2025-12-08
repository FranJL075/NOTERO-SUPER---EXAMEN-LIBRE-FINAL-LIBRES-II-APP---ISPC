-- Usuarios: almacena información de los usuarios de la aplicación
CREATE TABLE IF NOT EXISTS Usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contraseña TEXT NOT NULL,
    ubicacion VARCHAR(255)
);

-- Productos: catálogo de productos que pueden agregarse a una lista de compra
CREATE TABLE IF NOT EXISTS Productos (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo VARCHAR(128) UNIQUE NOT NULL,
    precio NUMERIC(10,2) NOT NULL,
    favorito BOOLEAN DEFAULT FALSE,
    imagen_url TEXT,
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- NegociosPromocionados: comercios que ofrecen listas promocionadas
CREATE TABLE IF NOT EXISTS NegociosPromocionados (
    id_negocio SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descuento NUMERIC(5,2) NOT NULL, -- porcentaje de descuento (0-100)
    presupuesto_base NUMERIC(10,2),
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
);

-- Listas: presupuestos de compra creados por los usuarios
CREATE TABLE IF NOT EXISTS Listas (
    id_lista SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    limite_presupuesto NUMERIC(10,2),
    total NUMERIC(10,2) DEFAULT 0,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_usuario INTEGER NOT NULL,
    es_promocion BOOLEAN DEFAULT FALSE,
    negocio_id INTEGER,
    CONSTRAINT fk_lista_usuario FOREIGN KEY(id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_lista_negocio FOREIGN KEY(negocio_id) REFERENCES NegociosPromocionados(id_negocio) ON DELETE SET NULL
);

-- DetallesLista: productos contenidos en cada lista
CREATE TABLE IF NOT EXISTS DetallesLista (
    id_detalle SERIAL PRIMARY KEY,
    id_lista INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) GENERATED ALWAYS AS (cantidad * precio_unitario) STORED,
    CONSTRAINT fk_detalle_lista FOREIGN KEY(id_lista) REFERENCES Listas(id_lista) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY(id_producto) REFERENCES Productos(id_producto) ON DELETE CASCADE
);

-- ListasPromocionadas: relación N-N entre listas y negocios promocionados
CREATE TABLE IF NOT EXISTS ListasPromocionadas (
    id_lista INTEGER NOT NULL,
    id_negocio INTEGER NOT NULL,
    PRIMARY KEY(id_lista, id_negocio),
    CONSTRAINT fk_lp_lista FOREIGN KEY(id_lista) REFERENCES Listas(id_lista) ON DELETE CASCADE,
    CONSTRAINT fk_lp_negocio FOREIGN KEY(id_negocio) REFERENCES NegociosPromocionados(id_negocio) ON DELETE CASCADE
);
