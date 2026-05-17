package com.dsy1103.msproductos.controller;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        log.info("REST: Solicitud para listar todas las CATEGORIAS");

        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando CATEGORIA por ID: {}", id);

        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaDTO dto) {
        log.info("REST: Creando nueva CATEGORIA: {}", dto.getNombreCategoria());

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardarCategoria(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@Valid @RequestBody CategoriaDTO dto) {
        log.info("REST: Actualizando CATEGORIA ID: {}", dto.getId());

        categoriaService.actualizarCategoria(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando CATEGORIA ID: {}", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

}
