package com.dsy1103.msproductos.controller;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name="Categorias", description="Endpoints para gestionar las categorias de productos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
        summary="Listar todas las categorias",
        description="Obtiene una lista con todas las categorias disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de categorias obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = CategoriaDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        log.info("REST: Solicitud para listar todas las CATEGORIAS");

        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @Operation(
        summary="Obtener categoria por ID",
        description="Obtiene los detalles de una categoria específica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = CategoriaDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria no encontrada",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando CATEGORIA por ID: {}", id);

        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @Operation(
        summary="Crear nueva categoria",
        description="Crea una nueva categoria con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoria creada exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = CategoriaDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaDTO dto) {
        log.info("REST: Creando nueva CATEGORIA: {}", dto.getNombreCategoria());

        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardarCategoria(dto));
    }

    @Operation(
        summary="Actualizar categoria existente",
        description="Actualiza los datos de una categoria existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Categoria actualizada exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria no encontrada",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@Valid @RequestBody CategoriaDTO dto) {
        log.info("REST: Actualizando CATEGORIA ID: {}", dto.getId());

        categoriaService.actualizarCategoria(dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary="Eliminar categoria",
        description="Elimina una categoria existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Categoria eliminada exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria no encontrada",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando CATEGORIA ID: {}", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
