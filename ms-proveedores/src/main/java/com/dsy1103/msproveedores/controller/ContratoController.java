package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ContratoDTO;
import com.dsy1103.msproveedores.service.ContratoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contrato")
@Slf4j
public class ContratoController {
    
    @Autowired
    private ContratoService contratoService;
    
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los CONTRATOS");
        return ResponseEntity.ok(contratoService.listarContratos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> obtenerContratoPorId(@PathVariable Long id) {
        log.info("REST: Buscando CONTRATO por ID: {}", id);
        return ResponseEntity.ok(contratoService.obtenerContratoPorId(id));
    }

    @GetMapping("/porproveedor/{id}")
    public ResponseEntity<List<ContratoDTO>> listarPorProveedor(@PathVariable Long id) {
        log.info("REST: Solicitud para listar CONTRATOS por PROVEEDOR ID: {}", id);
        return ResponseEntity.ok(contratoService.listarContratosPorProveedor(id));
    }

    @PostMapping
    public ResponseEntity<ContratoDTO> guardarContrato(@Valid @RequestBody ContratoDTO cDTO) {
        log.info("REST: Creando nuevo CONTRATO: {}", cDTO.toString());
        ContratoDTO creado = contratoService.guardarContrato(cDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<?> actualizarContrato(@Valid @RequestBody ContratoDTO cDTO) {
        log.info("REST: Actualizando CONTRATO: {}", cDTO.toString());
        contratoService.actualizarContrato(cDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarContrato(@PathVariable Long id) {
        log.warn("REST: Eliminando CONTRATO ID: {}", id);
        contratoService.eliminarContrato(id);
        return ResponseEntity.noContent().build();
    }
}
