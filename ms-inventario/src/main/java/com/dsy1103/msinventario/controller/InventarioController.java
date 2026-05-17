package com.dsy1103.msinventario.controller;

import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.dto.InventarioProductoDTO;
import com.dsy1103.msinventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@Slf4j
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los INVENTARIOS");
        return ResponseEntity.ok(inventarioService.listarInventarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioProductoDTO> obtenerInventarioPorId(@PathVariable Long id) {
        log.info("REST: Buscando INVENTARIO por ID: {}", id);
        return ResponseEntity.ok(inventarioService.obtenerInventarioPorId(id));
    }

    @GetMapping("/mayorque/{cantidad}")
    public ResponseEntity<List<InventarioDTO>> listarCantidadMayor(@PathVariable Integer cantidad) {
        log.info("REST: Solicitud para listar INVENTARIOS con cantidad mayor que {} y activos", cantidad);
        return ResponseEntity.ok(inventarioService.listarInventariosConCantidadMayorActivos(cantidad));
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> guardarInventario(@Valid @RequestBody InventarioDTO iDTO) {
        log.info("REST: Creando nuevo INVENTARIO: {}", iDTO.toString());
        InventarioDTO creado = inventarioService.guardarInventario(iDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<?> actualizarIventario(@Valid @RequestBody InventarioDTO iDTO) {
        log.info("REST: Actualizando IVENTARIO: {}", iDTO.toString());
        inventarioService.actualizarInventario(iDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInventario(@PathVariable Long id) {
        log.warn("REST: Eliminando IVENTARIO ID: {}", id);
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
