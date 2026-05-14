package com.dsy1103.msusuarios.controller;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar: GET http://localhost:8081/api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // Buscar por ID: GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // Crear: POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioDTO> guardar(@Valid @RequestBody UsuarioDTO dto) {
        // Retornamos 201 Created como pide la pauta
        return new ResponseEntity<>(usuarioService.crear(dto), HttpStatus.CREATED);
    }

    // Actualizar: PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }
}
