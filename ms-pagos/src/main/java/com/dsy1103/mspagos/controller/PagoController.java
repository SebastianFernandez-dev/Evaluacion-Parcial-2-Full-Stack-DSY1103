package com.dsy1103.mspagos.controller;

import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@Slf4j
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PAGOS");
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando PAGO por ID: {}", id);
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(id));
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<PagoDTO>> filtrarPorMontoYEstado(
            @RequestParam Double monto,
            @RequestParam String estadoPago) {
        log.info("REST: Filtrando PAGOS con monto > {} y estado '{}'", monto, estadoPago);
        return ResponseEntity.ok(pagoService.buscarPorMontoYEstado(monto, estadoPago));
    }

    @PostMapping
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoDTO dto) {
        log.info("REST: Creando nuevo PAGO: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        log.info("REST: Actualizando PAGO ID: {}", id);
        dto.setId(id);
        pagoService.actualizarPago(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando PAGO ID: {}", id);
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
