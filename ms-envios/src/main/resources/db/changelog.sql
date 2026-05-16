--liquibase formatted sql

--changeset milton:1
CREATE TABLE envio_modelo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_envio VARCHAR(40) NOT NULL,
    pedido_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    direccion_destino VARCHAR(200) NOT NULL,
    estado_envio VARCHAR(30) NOT NULL,
    fecha_salida DATETIME NOT NULL,
    fecha_entrega_estimada DATE NOT NULL,
    fecha_entrega DATE NOT NULL,
    activo BOOLEAN NOT NULL
);

--changeset milton:2
CREATE TABLE seguimiento_modelo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(120) NOT NULL,
    ubicacion_actual VARCHAR(255) NOT NULL,
    observacion VARCHAR(200) NOT NULL,
    fecha_seguimiento DATETIME NOT NULL,
    visible BOOLEAN NOT NULL,
    envio_id BIGINT NOT NULL,
    CONSTRAINT fk_seguimiento_envio FOREIGN KEY (envio_id) REFERENCES envio_modelo(id)
);