-- Datos de ejemplo para poblar la base

-- Usuarios
INSERT INTO Usuarios(nombre, email, contraseña, ubicacion) VALUES
('Juan Pérez', 'juan@example.com', 'hashed_password_juan', 'Córdoba'),
('Ana Gómez', 'ana@example.com', 'hashed_password_ana', 'Buenos Aires');

-- Productos
INSERT INTO Productos(nombre, codigo, precio, favorito, imagen_url) VALUES
('Arroz 1Kg', '7791234567890', 120.50, FALSE, 'https://example.com/arroz.jpg'),
('Leche 1L', '7790987654321', 200.00, FALSE, 'https://example.com/leche.jpg');

-- Negocios Promocionados
INSERT INTO NegociosPromocionados(nombre, descuento, presupuesto_base, direccion, activo) VALUES
('Super Promo', 10.00, 1000.00, 'Av. Siempreviva 742', TRUE);

-- Listas (la columna total se calculará luego vía aplicación)
-- Suponemos que el usuario con id 1 crea la lista
INSERT INTO Listas(nombre, limite_presupuesto, id_usuario, es_promocion, negocio_id) VALUES
('Compra Mensual', 5000.00, 1, FALSE, NULL);

-- DetallesLista
-- Vinculamos los productos a la lista creada (id_lista = 1)
INSERT INTO DetallesLista(id_lista, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 2, 120.50), -- 2 Kg de Arroz
(1, 2, 3, 200.00); -- 3 L de Leche

-- ListasPromocionadas
-- (Sin datos iniciales, ya que la lista no es promocionada en el ejemplo)
