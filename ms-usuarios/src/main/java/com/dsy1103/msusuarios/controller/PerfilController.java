package com.dsy1103.msusuarios.controller;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.service.PerfilService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // 1. Listar todos: GET /api/perfiles
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listartodo() {
        log.info("Solicitando lista de todos los Perfiles");
        return ResponseEntity.ok(perfilService.listarTodo());
    }

    // 2. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando Perfil con ID: {}", id);
        return ResponseEntity.ok(perfilService.buscarPorId(id));
    }

    // 2. Crear: POST /api/perfiles
    @PostMapping
    public ResponseEntity<PerfilDTO> crear(@Valid @RequestBody PerfilDTO dto) {
        log.info("Recibida solicitud para crear Perfil");
        return new ResponseEntity<>(perfilService.crear(dto), HttpStatus.CREATED);
    }

    // 3. Actualizar: PUT /api/perfiles/{id}
    @PutMapping
    public ResponseEntity<?> actualizar(@Valid @RequestBody PerfilDTO pDTO) {
        log.info("Actualizando PERFIL: {}", pDTO.toString());
        perfilService.actualizar(pDTO);
        return ResponseEntity.noContent().build();
    }

    // 5. ELIMINAR DETALLE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Perfil ID: {}", id);
        perfilService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
