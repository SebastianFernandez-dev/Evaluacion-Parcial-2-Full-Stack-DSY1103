package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.service.SucursalService;
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
@RequestMapping("/api/v1/sucursal")
@Slf4j
@Tag(name = "Sucursales", description = "Endpoints para gestionar las sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Operation(
        summary = "Listar todas las sucursales",
        description = "Obtiene una lista con todas las sucursales disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de sucursales obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<SucursalResponseDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todas las SUCURSALES");
        return ResponseEntity.ok(sucursalService.listarSucursales());
    }

    @Operation(
        summary = "Obtener sucursal por ID",
        description = "Obtiene los detalles de una sucursal especifica utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sucursal obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> obtenerSucursalPorId(@PathVariable Long id) {
        log.info("REST: Buscando SUCURSAL por ID: {}", id);
        return ResponseEntity.ok(sucursalService.obtenerSucursalPorId(id));
    }

    @Operation(
        summary = "Listar sucursales por nombre de region",
        description = "Obtiene todas las sucursales que pertenecen a una region por su nombre")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de sucursales obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class)))
    })
    @GetMapping("/pornombreregion/{nombre}")
    public ResponseEntity<List<SucursalResponseDTO>> listarPorNombreRegion(@PathVariable String nombre) {
        log.info("REST: Solicitud para listar SUCURSALES por REGION NOMBRE: {}", nombre);
        return ResponseEntity.ok(sucursalService.listarSucursalesPorRegion(nombre));
    }

    @Operation(
        summary = "Crear nueva sucursal",
        description = "Crea una nueva sucursal con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Sucursal creada exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Region no encontrada",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<SucursalResponseDTO> guardarSucursal(@Valid @RequestBody SucursalRequestDTO dto) {
        log.info("REST: Creando nueva SUCURSAL: {}", dto.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(sucursalService.guardarSucursal(dto));
    }

    @Operation(
        summary = "Actualizar sucursal existente",
        description = "Actualiza los datos de una sucursal existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sucursal actualizada exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = SucursalResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Sucursal o Region no encontrada",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponseDTO> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody SucursalRequestDTO dto) {
        log.info("REST: Actualizando SUCURSAL ID: {}", id);
        return ResponseEntity.ok(sucursalService.actualizarSucursal(id, dto));
    }

    @Operation(
        summary = "Eliminar sucursal",
        description = "Elimina una sucursal existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Sucursal eliminada exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Sucursal no encontrada",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        log.warn("REST: Eliminando SUCURSAL ID: {}", id);
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
