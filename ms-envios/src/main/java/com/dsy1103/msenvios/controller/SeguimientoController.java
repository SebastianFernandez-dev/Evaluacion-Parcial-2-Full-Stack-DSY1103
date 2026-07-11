package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.service.SeguimientoService;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/seguimientos")
@Tag(name="Seguimientos", description="Endpoints para gestionar los seguimientos de envios")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @Operation(
        summary="Listar todos los seguimientos",
        description="Obtiene una lista con todos los seguimientos disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de seguimientos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<SeguimientoResponseDTO>> listarTodos() {
        log.info("Recibida solicitud para listar todos los Seguimientos");
        return ResponseEntity.ok(seguimientoService.listarTodos());
    }

    @Operation(
        summary="Obtener seguimiento por ID",
        description="Obtiene los detalles de un seguimiento especifico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seguimiento obtenido exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("Recibida solicitud para buscar seguimiento por ID: {}", id);
        return ResponseEntity.ok(seguimientoService.buscarPorId(id));
    }

    @Operation(
        summary="Crear nuevo seguimiento",
        description="Crea un nuevo seguimiento con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Seguimiento creado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<SeguimientoResponseDTO> crear(@Valid @RequestBody SeguimientoRequestDTO dto) {
        log.info("Recibida solicitud para crear un nuevo Seguimiento");
        return new ResponseEntity<>(seguimientoService.crear(dto), HttpStatus.CREATED);
    }

    @Operation(
        summary="Actualizar seguimiento existente",
        description="Actualiza los datos de un seguimiento existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seguimiento actualizado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody SeguimientoRequestDTO dto) {
        log.info("REST: Actualizando SEGUIMIENTO ID: {}", id);
        return ResponseEntity.ok(seguimientoService.actualizar(id, dto));
    }

    @Operation(
        summary="Eliminar seguimiento",
        description="Elimina un seguimiento existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Seguimiento eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Seguimiento con ID: {}", id);
        seguimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
