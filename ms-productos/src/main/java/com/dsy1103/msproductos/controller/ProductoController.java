package com.dsy1103.msproductos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
@Tag(name="Productos", description="Endpoints para gestionar los productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
        summary="Listar todos los productos",
        description="Obtiene una lista con todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProductoDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PRODUCTOS");

        return ResponseEntity.ok(productoService.listarProductos());
    }

    @Operation(
        summary="Obtener producto por ID",
        description="Obtiene los detalles de un producto específico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProductoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando PRODUCTO por ID: {}", id);
        return ResponseEntity.ok(productoService.obtenerProductoPorId(id));
    }

    @Operation(
        summary="Buscar productos por nombre y precio",
        description="Obtiene una lista de productos que coinciden con el nombre y tienen un precio menor al especificado")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProductoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parámetros de búsqueda inválidos",
            content = @Content
            )
    })
    @GetMapping("/buscar-avanzado")
    public ResponseEntity<List<ProductoDTO>> buscarPorNombreYPrecio(
            @RequestParam String nombre,
            @RequestParam Double precio) {
        log.info("REST: Buscando PRODUCTOS por nombre '{}' y precio < {}", nombre, precio);
        return ResponseEntity.ok(productoService.buscarPorNombreYPrecio(nombre, precio));
    }

    @Operation(
        summary="Crear nuevo producto",
        description="Crea un nuevo producto con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProductoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> guardar(@Valid @RequestBody ProductoDTO dto) {
        log.info("REST: Creando nuevo PRODUCTO: {}", dto.getNombreProducto());

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.guardarProducto(dto));
    }

    @Operation(
        summary="Actualizar producto existente",
        description="Actualiza los datos de un producto existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto actualizado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@Valid @RequestBody ProductoDTO dto) {
        log.info("REST: Actualizando PRODUCTO ID: {}", dto.getId());

        productoService.actualizarProducto(dto);
        return ResponseEntity.noContent().build();
    }


    @Operation(
        summary="Eliminar producto",
        description="Elimina un producto existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
        )    
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando PRODUCTO ID: {}", id);

        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
