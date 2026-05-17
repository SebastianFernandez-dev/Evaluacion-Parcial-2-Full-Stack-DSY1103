-- 1. Crear la tabla de Pedidos
CREATE TABLE PEDIDO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_pedido VARCHAR(12) NOT NULL UNIQUE,
    usuario_id BIGINT NOT NULL,
    fecha_pedido DATETIME NOT NULL,
    total_pedido INT NOT NULL,
    pagado_pedido BOOLEAN NOT NULL,
    direccion_entrega VARCHAR(200) NOT NULL,
    estado_pedido VARCHAR(30)
);

-- 2. Crear la tabla de Detalle Pedido con el nombre exacto que busca Hibernate
CREATE TABLE detallepedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad_pedido INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    observacion VARCHAR(150) NOT NULL,
    fecha_registro DATETIME NOT NULL,
    estado_detalle BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_pedido_detalle FOREIGN KEY (pedido_id) REFERENCES PEDIDO(id) ON DELETE CASCADE
);

-- 3. Insertar los 3 registros de prueba obligatorios en PEDIDO
INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (1, 'PED-00000001', 1, NOW(), 25000, true, 'Av. Apoquindo 1234, Las Condes', 'PAGADO');

INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (2, 'PED-00000002', 2, NOW(), 12500, false, 'Alameda 456, Santiago Centro', 'PENDIENTE');

INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (3, 'PED-00000003', 1, NOW(), 45000, true, 'Providencia 789, Providencia', 'PROCESANDO');

-- 4. Insertar detalles de prueba en la tabla corregida
INSERT INTO detallepedido (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (1, 101, 2, 12500.0, 25000.0, 'Despachar con cuidado, frágil', NOW(), true);

INSERT INTO detallepedido (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (2, 102, 1, 12500.0, 12500.0, 'Entregar en conserjería', NOW(), true);

INSERT INTO detallepedido (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (3, 103, 3, 15000.0, 45000.0, 'Envío prioritario express', NOW(), true);