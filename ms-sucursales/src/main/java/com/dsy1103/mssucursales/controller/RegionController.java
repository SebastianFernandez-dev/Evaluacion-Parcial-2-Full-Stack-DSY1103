package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import com.dsy1103.mssucursales.service.RegionService;
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
@RequestMapping("/api/v1/region")
@Slf4j
@Tag(name = "Regiones", description = "Endpoints para gestionar las regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Operation(
        summary = "Listar todas las regiones",
        description = "Obtiene una lista con todas las regiones disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de regiones obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<RegionResponseDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todas las REGIONES");
        return ResponseEntity.ok(regionService.listarRegiones());
    }

    @Operation(
        summary = "Obtener region por ID",
        description = "Obtiene los detalles de una region especifica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Region obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Region no encontrada",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> obtenerRegionPorId(@PathVariable Long id) {
        log.info("REST: Buscando REGION por ID: {}", id);
        return ResponseEntity.ok(regionService.obtenerRegionPorId(id));
    }

    @Operation(
        summary = "Crear nueva region",
        description = "Crea una nueva region con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Region creada exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<RegionResponseDTO> guardarRegion(@Valid @RequestBody RegionRequestDTO dto) {
        log.info("REST: Creando nueva REGION: {}", dto.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.guardarRegion(dto));
    }

    @Operation(
        summary = "Actualizar region existente",
        description = "Actualiza los datos de una region existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Region actualizada exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = RegionResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Region no encontrada",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RegionResponseDTO> actualizarRegion(@PathVariable Long id, @Valid @RequestBody RegionRequestDTO dto) {
        log.info("REST: Actualizando REGION ID: {}", id);
        return ResponseEntity.ok(regionService.actualizarRegion(id, dto));
    }

    @Operation(
        summary = "Eliminar region",
        description = "Elimina una region existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Region eliminada exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Region no encontrada",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Long id) {
        log.warn("REST: Eliminando REGION ID: {}", id);
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}
