CREATE TABLE IF NOT EXISTS inventario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(12) NOT NULL UNIQUE,
    ubicacion VARCHAR(100) NOT NULL,
    cantidad_disponible INT NOT NULL,
    stock_minimo INT NOT NULL,
    activo TINYINT(1) NOT NULL,
    fecha_realizacion DATE NOT NULL,
    producto_id BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS movimiento_stock (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    motivo VARCHAR(150) NOT NULL,
    saldo_posterior INT NOT NULL,
    fecha DATE NOT NULL,
    aprobado TINYINT(1) NOT NULL,
    fk_inventario_id BIGINT,

    -- Relación establecida en tu @ManyToOne / @JoinColumn(name="fk_inventario_id")
    CONSTRAINT fk_movimiento_inventario
    FOREIGN KEY (fk_inventario_id)
    REFERENCES inventario(id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
