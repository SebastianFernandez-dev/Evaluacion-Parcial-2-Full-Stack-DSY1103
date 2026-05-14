package com.dsy1103.msusuarios.controller;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    // 1. Listar todos: GET /api/perfiles
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listar() {
        return ResponseEntity.ok(perfilService.listarTodo());
    }

    // 2. Crear: POST /api/perfiles
    @PostMapping
    public ResponseEntity<PerfilDTO> guardar(@Valid @RequestBody PerfilDTO dto) {
        // Aquí es donde el Service validará que el usuarioId existe
        return new ResponseEntity<>(perfilService.crear(dto), HttpStatus.CREATED);
    }

    // 3. Actualizar: PUT /api/perfiles/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PerfilDTO dto) {
        return ResponseEntity.ok(perfilService.actualizar(id, dto));
    }



}
