ALTER TABLE inventario
    ADD COLUMN sucursal_id BIGINT NOT NULL AFTER producto_id,
    ADD COLUMN proveedor_id BIGINT NOT NULL AFTER sucursal_id;