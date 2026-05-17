CREATE TABLE IF NOT EXISTS pagos (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_transaccion  VARCHAR(40)  NOT NULL,
    pedido_id           BIGINT       NOT NULL,
    monto               DOUBLE       NOT NULL,
    metodo_pago         VARCHAR(30)  NOT NULL,
    estado_pago         VARCHAR(20)  NOT NULL,
    fecha_pago          DATE         NOT NULL,
    activo_pago         BOOLEAN      NOT NULL
);