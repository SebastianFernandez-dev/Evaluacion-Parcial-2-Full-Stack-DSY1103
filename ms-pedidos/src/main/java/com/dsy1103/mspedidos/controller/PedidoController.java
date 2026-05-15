package com.dsy1103.mspedidos.controller;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // LISTAR TODOS LOS PEDIDOS
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        log.info("Solicitando listado global de pedidos");
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    // BUSCAR GET
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando pedido con ID: {}", id);
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    // CREAR POST
    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("Recibida nueva solicitud de compra para el usuario ID: {}", dto.getUsuarioId());
        PedidoDTO creado = pedidoService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // ACTUALIZAR PUT
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PedidoDTO dto) {
        log.info("Solicitud para actualizar pedido ID: {}", id);
        return ResponseEntity.ok(pedidoService.actualizar(id, dto));
    }

    // ELIMINAR DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar pedido ID: {}", id);
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
