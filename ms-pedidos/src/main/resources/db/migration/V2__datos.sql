INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (1, 'PED-00000001', 1, NOW(), 25000, true, 'Av. Apoquindo 1234, Las Condes', 'PAGADO');

INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (2, 'PED-00000002', 2, NOW(), 12500, false, 'Alameda 456, Santiago Centro', 'PENDIENTE');

INSERT INTO PEDIDO (id, codigo_pedido, usuario_id, fecha_pedido, total_pedido, pagado_pedido, direccion_entrega, estado_pedido)
VALUES (3, 'PED-00000003', 1, NOW(), 45000, true, 'Providencia 789, Providencia', 'PROCESANDO');

INSERT INTO DETALLEPEDIDO (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (1, 1, 2, 12500.0, 25000.0, 'Despachar con cuidado, frágil', NOW(), true);

INSERT INTO DETALLEPEDIDO (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (2, 2, 1, 12500.0, 12500.0, 'Entregar en conserjería', NOW(), true);

INSERT INTO DETALLEPEDIDO (pedido_id, producto_id, cantidad_pedido, precio_unitario, subtotal, observacion, fecha_registro, estado_detalle)
VALUES (3, 3, 3, 15000.0, 45000.0, 'Envío prioritario express', NOW(), true);