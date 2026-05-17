package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ProveedorDTO;
import com.dsy1103.msproveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedor")
@Slf4j
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PROVEEDORES");
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> obtenerProveedorPorId(@PathVariable Long id) {
        log.info("REST: Buscando PROVEEDOR por ID: {}", id);
        return ResponseEntity.ok(proveedorService.obtenerProveedorPorId(id));
    }

    @GetMapping("/activo")
    public ResponseEntity<List<ProveedorDTO>> listarActivos() {
        log.info("REST: Solicitud para listar PROVEEDORES ACTIVOS");
        return ResponseEntity.ok(proveedorService.listarProveedoresActivos());
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> guardarProveedor(@Valid @RequestBody ProveedorDTO pDTO) {
        log.info("REST: Creando nuevo PROVEEDOR: {}", pDTO.toString());
        ProveedorDTO creado = proveedorService.guardarProveedor(pDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<?> actualizarProveedor(@Valid @RequestBody ProveedorDTO pDTO) {
        log.info("REST: Actualizando PROVEEDOR: {}", pDTO.toString());
        proveedorService.actualizarProveedor(pDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        log.warn("REST: Eliminando PROVEEDOR ID: {}", id);
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
