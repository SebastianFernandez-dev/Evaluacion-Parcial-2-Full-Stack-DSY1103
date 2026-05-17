package com.dsy1103.msproductos.controller;

import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PRODUCTOS");

        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando PRODUCTO por ID: {}", id);
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @GetMapping("/buscar-avanzado")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(
            @RequestParam String nombre,
            @RequestParam Double precio) {
        log.info("REST: Buscando PRODUCTOS por nombre '{}' y precio < {}", nombre, precio);
        return ResponseEntity.ok(productoService.buscarPorNombreYPrecio(nombre, precio));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoDTO dto) {
        log.info("REST: Creando nuevo PRODUCTO: {}", dto.getNombreProducto());

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@Valid @RequestBody ProductoDTO dto) {
        log.info("REST: Actualizando PRODUCTO ID: {}", dto.getId());

        productoService.actualizarProducto(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando PRODUCTO ID: {}", id);

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
