package com.dsy1103.msinventario.controller;

import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.service.MovimientoStockService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientostock")
@Slf4j
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService movimientoStockService;

    @GetMapping
    public ResponseEntity<List<MovimientoStockDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los MOVIMIENTOS");
        return ResponseEntity.ok(movimientoStockService.listarMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockDTO> obtenerMovimientoPorId(@PathVariable Long id) {
        log.info("REST: Buscando MOVIMIENTO por ID: {}", id);
        return ResponseEntity.ok(movimientoStockService.obtenerMovimientoPorId(id));
    }

    @GetMapping("/porinventario/{id}")
    public ResponseEntity<List<MovimientoStockDTO>> listarPorInventario(@PathVariable Long id) {
        log.info("REST: Solicitud para listar MOVIMIENTO por INVENTARIO ID: {}", id);
        return ResponseEntity.ok(movimientoStockService.listarMovimientosPorInventario(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoStockDTO> guardarMovimiento(@Valid @RequestBody MovimientoStockDTO mDTO) {
        log.info("REST: Creando nuevo CONTRATO: {}", mDTO.toString());
        MovimientoStockDTO creado = movimientoStockService.guardarMovimiento(mDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<?> actualizarMovimiento(@Valid @RequestBody MovimientoStockDTO mDTO) {
        log.info("REST: Actualizando Movimiento: {}", mDTO.toString());
        movimientoStockService.actualizarMovimiento(mDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMovimiento(@PathVariable Long id) {
        log.warn("REST: Eliminando MOVIMIENTO ID: {}", id);
        movimientoStockService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
