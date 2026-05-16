package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.RegionDTO;
import com.dsy1103.mssucursales.service.RegionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@Slf4j
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todas las REGIONES");
        return ResponseEntity.ok(regionService.listarRegiones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> obtenerRegionPorId(@PathVariable Long id) {
        log.info("REST: Buscando REGION por ID: {}", id);
        return ResponseEntity.ok(regionService.obtenerRegionPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegionDTO> guardarRegion(@Valid @RequestBody RegionDTO rDTO) {
        log.info("REST: Creando nueva REGION: {}", rDTO.toString());
        RegionDTO creada = regionService.guardarProveedor(rDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping
    public ResponseEntity<?> actualizarRegion(@Valid @RequestBody RegionDTO rDTO) {
        log.info("REST: Actualizando REGION: {}", rDTO.toString());
        regionService.actualizarRegion(rDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRegion(@PathVariable Long id) {
        log.warn("REST: Eliminando REGION ID: {}", id);
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}
