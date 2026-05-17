INSERT INTO pagos (codigo_transaccion, pedido_id, monto, metodo_pago, estado_pago, fecha_pago, activo_pago)
VALUES
('TXN-2026-001', 1, 15990.0, 'Tarjeta de Crédito', 'APROBADO',  '2026-01-10', true),
('TXN-2026-002', 2, 45000.0, 'Transferencia',      'APROBADO',  '2026-02-20', true),
('TXN-2026-003', 1,  8500.0, 'Débito',             'RECHAZADO', '2026-03-05', false),
('TXN-2026-004', 3, 23000.0, 'Tarjeta de Débito',  'APROBADO',  '2026-04-12', true),
('TXN-2026-005', 2, 61500.0, 'Transferencia',      'PENDIENTE', '2026-05-01', true);