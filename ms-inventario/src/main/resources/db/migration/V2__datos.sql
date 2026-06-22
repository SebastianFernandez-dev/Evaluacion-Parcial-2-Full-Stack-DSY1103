INSERT INTO inventario (id, codigo, ubicacion, cantidad_disponible, stock_minimo, activo, fecha_realizacion, producto_id) VALUES
(1, 'INV-NOTE-001', 'Almacén Central - Pasillo A1', 45,
10, 1, '2026-01-15', 1),
(2, 'INV-MONI-002', 'Almacén Norte - Estante B3', 8,
15, 1, '2026-02-10', 2),
(3, 'INV-MOUS-003', 'Almacén Central - Pasillo A5', 120,
 20, 1, '2026-03-01', 3);

INSERT INTO movimiento_stock (id, tipo, cantidad, motivo, saldo_posterior, fecha, aprobado, fk_inventario_id) VALUES
(1, 'ENTRADA', 50, 'Carga inicial por recepción de proveedor',
 50, '2026-01-15', 1, 1),
(2, 'SALIDA', 5, 'Despacho a Sucursal Santiago Centro',
 45, '2026-02-20', 1, 1);

INSERT INTO movimiento_stock (id, tipo, cantidad, motivo, saldo_posterior, fecha, aprobado, fk_inventario_id) VALUES
(3, 'ENTRADA', 20, 'Carga inicial de stock importado',
 20, '2026-02-10', 1, 2),
(4, 'SALIDA', 12, 'Venta corporativa cliente interno',
 8, '2026-05-10', 1, 2);

INSERT INTO movimiento_stock (id, tipo, cantidad, motivo, saldo_posterior, fecha, aprobado, fk_inventario_id) VALUES
(5, 'ENTRADA', 100, 'Ingreso por compra nacional',
 100, '2026-03-01', 1, 3),
(6, 'ENTRADA', 20, 'Ajuste de inventario tras auditoría física',
 120, '2026-04-18', 1, 3);
