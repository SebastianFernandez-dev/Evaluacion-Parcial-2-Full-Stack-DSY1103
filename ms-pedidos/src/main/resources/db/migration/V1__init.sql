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

CREATE TABLE DETALLEPEDIDO (
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
