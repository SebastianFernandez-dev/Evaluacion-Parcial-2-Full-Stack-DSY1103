package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
@Slf4j
@Tag(name="Proveedores", description="Endpoints para gestionar los proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Operation(
        summary="Listar todos los proveedores",
        description="Obtiene una lista con todos los proveedores disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de proveedores obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PROVEEDORES");
        return ResponseEntity.ok(proveedorService.listarProveedores());
    }

    @Operation(
        summary="Obtener proveedor por ID",
        description="Obtiene los detalles de un proveedor específico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerProveedorPorId(@PathVariable Long id) {
        log.info("REST: Buscando PROVEEDOR por ID: {}", id);
        return ResponseEntity.ok(proveedorService.obtenerProveedorPorId(id));
    }

    @Operation(
        summary="Listar proveedores activos",
        description="Obtiene una lista de los proveedores que se encuentran activos")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de proveedores activos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        )
    })
    @GetMapping("/activo")
    public ResponseEntity<List<ProveedorResponseDTO>> listarActivos() {
        log.info("REST: Solicitud para listar PROVEEDORES ACTIVOS");
        return ResponseEntity.ok(proveedorService.listarProveedoresActivos());
    }

    @Operation(
        summary="Crear nuevo proveedor",
        description="Crea un nuevo proveedor con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Proveedor creado exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> guardarProveedor(@Valid @RequestBody ProveedorRequestDTO dto) {
        log.info("REST: Creando nuevo PROVEEDOR: {}", dto.getNombre());
        ProveedorResponseDTO creado = proveedorService.guardarProveedor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
        summary="Actualizar proveedor existente",
        description="Actualiza los datos de un proveedor existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDTO dto) {
        log.info("REST: Actualizando PROVEEDOR ID: {}", id);
        return ResponseEntity.ok(proveedorService.actualizarProveedor(id, dto));
    }

    @Operation(
        summary="Eliminar proveedor",
        description="Elimina un proveedor existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Proveedor eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        log.warn("REST: Eliminando PROVEEDOR ID: {}", id);
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
