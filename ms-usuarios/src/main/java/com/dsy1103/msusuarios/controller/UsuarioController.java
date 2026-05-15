package com.dsy1103.msusuarios.controller;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listar:http://localhost:8081/api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // Buscar por ID: GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // Crear: POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioDTO dto) {
        log.info("Recibida solicitud para crear un nuevo Usuario: {}", dto.getPrimerNombre());
        return new ResponseEntity<>(usuarioService.crear(dto), HttpStatus.CREATED);
    }

    // Actualizar: PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO dto) {
        log.info("Recibida solicitud para actualizar Usuario ID: {}", id);
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    // 5. ELIMINAR DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Usuario ID: {}", id);
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
