package com.dsy1103.mspedidos.controller;

import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/detalles-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detalleService;

    // 1. LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> listarTodos() {
        log.info("Solicitando lista de todos los detalles");
        return ResponseEntity.ok(detalleService.listarTodos());
    }

    // BUSCAR GET
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando detalle con ID: {}", id);
        return ResponseEntity.ok(detalleService.buscarPorId(id));
    }

    // 3. CREAR POST
    @PostMapping
    public ResponseEntity<DetallePedidoDTO> crear(@Valid @RequestBody DetallePedidoDTO dto) {
        log.info("Recibida solicitud para crear detalle de producto ID: {}", dto.getProductoId());
        DetallePedidoDTO creado = detalleService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR PUT
    @PutMapping
    public ResponseEntity<?> actualizar(@Valid @RequestBody DetallePedidoDTO dDTO) {
        log.info("Actualizando DETALLE PEDIDO: {}", dDTO.toString());
        detalleService.actualizar(dDTO);
        return ResponseEntity.noContent().build();
    }

    // 5. ELIMINAR DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar detalle ID: {}", id);
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}