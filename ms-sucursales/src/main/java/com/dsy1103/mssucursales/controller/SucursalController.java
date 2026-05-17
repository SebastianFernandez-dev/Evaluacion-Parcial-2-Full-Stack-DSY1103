package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.SucursalDTO;
import com.dsy1103.mssucursales.service.SucursalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursal")
@Slf4j
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todas las SUCURSALES");
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> obtenerSucursalPorId(@PathVariable Long id) {
        log.info("REST: Buscando SUCURSAL por ID: {}", id);
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }

    @GetMapping("/pornombreregion/{nombre}")
    public ResponseEntity<List<SucursalDTO>> listarPorNombreRegion(@PathVariable String nombre) {
        log.info("REST: Solicitud para listar SUCURSALES por REGION NOMBRE: {}", nombre);
        return ResponseEntity.ok(sucursalService.listarSucursalesPorRegion(nombre));
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> guardarSucursal(@Valid @RequestBody SucursalDTO sDTO) {
        log.info("REST: Creando nueva SUCURSAL: {}", sDTO.toString());
        SucursalDTO creada = sucursalService.guardarSucursal(sDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping
    public ResponseEntity<?> actualizarSucursal(@Valid @RequestBody SucursalDTO sDTO) {
        log.info("REST: Actualizando SUCURSAL: {}", sDTO.toString());
        sucursalService.actualizarSucursal(sDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSucursal(@PathVariable Long id) {
        log.warn("REST: Eliminando SUCURSAL ID: {}", id);
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
