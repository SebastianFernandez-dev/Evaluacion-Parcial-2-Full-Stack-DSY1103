package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.EnvioDTO;
import com.dsy1103.msenvios.dto.SeguimientoDTO;
import com.dsy1103.msenvios.service.EnvioService;
import com.dsy1103.msenvios.service.SeguimientoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    // Listar
    @GetMapping
    public ResponseEntity<List<SeguimientoDTO>> listarTodos() {
        log.info("Recibida solicitud para listar todos los Seguimiento");
        return ResponseEntity.ok(seguimientoService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Recibida solicitud para crear un Seguimiento");
        return ResponseEntity.ok(seguimientoService.buscarPorId(id));
    }

    // Crear: POST
    @PostMapping
    public ResponseEntity<SeguimientoDTO> crear(@Valid @RequestBody SeguimientoDTO dto) {
        log.info("Recibida solicitud para crear un nuevo Seguimiento");
        return new ResponseEntity<>(seguimientoService.crear(dto), HttpStatus.CREATED);
    }

    // Actualizar: PUT
    @PutMapping
    public ResponseEntity<?> actualizarSeguimiento(@Valid @RequestBody SeguimientoDTO sDTO) {
        log.info("Actualizando SEGUIMIENTO: {}", sDTO.toString());
        seguimientoService.actualizarSeguimiento(sDTO);
        return ResponseEntity.noContent().build();
    }

    // ELIMINAR: DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Seguimiento con ID: {}", id);
        seguimientoService.eliminar(id);
        return ResponseEntity.ok("Seguimiento eliminado correctamente con el ID: " + id);
    }

}
