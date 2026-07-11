package com.dsy1103.msreportes.controller;

import com.dsy1103.msreportes.dto.ReporteRequestDTO;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import com.dsy1103.msreportes.dto.ReporteUsuarioDTO;
import com.dsy1103.msreportes.service.ReporteService;
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
@RequestMapping("/api/v1/reporte")
@Slf4j
@Tag(name = "Reportes", description = "Endpoints para gestionar reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(
        summary = "Listar todos los reportes",
        description = "Obtiene una lista con todos los reportes disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ReporteResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ReporteResponseDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los REPORTES");
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    @Operation(
        summary = "Obtener reporte por ID con datos de usuario",
        description = "Obtiene los detalles de un reporte especifico incluyendo datos del usuario via Feign")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte obtenido exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ReporteUsuarioDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReporteUsuarioDTO> obtenerReportePorId(@PathVariable Long id) {
        log.info("REST: Buscando REPORTE por ID: {}", id);
        return ResponseEntity.ok(reporteService.obtenerReportePorId(id));
    }

    @Operation(
        summary = "Listar reportes por usuario",
        description = "Obtiene todos los reportes asociados a un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de reportes obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ReporteResponseDTO.class)))
    })
    @GetMapping("/porusuario/{id}")
    public ResponseEntity<List<ReporteResponseDTO>> listarPorUsuario(@PathVariable Long id) {
        log.info("REST: Solicitud para listar REPORTES por USUARIO ID {}", id);
        return ResponseEntity.ok(reporteService.listarReportePorUsuario(id));
    }

    @Operation(
        summary = "Crear nuevo reporte",
        description = "Crea un nuevo reporte con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Reporte creado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ReporteResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReporteResponseDTO> guardarReporte(@Valid @RequestBody ReporteRequestDTO dto) {
        log.info("REST: Creando nuevo REPORTE: {}", dto.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.guardarReporte(dto));
    }

    @Operation(
        summary = "Actualizar reporte existente",
        description = "Actualiza los datos de un reporte existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte actualizado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = ReporteResponseDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponseDTO> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReporteRequestDTO dto) {
        log.info("REST: Actualizando REPORTE ID: {}", id);
        return ResponseEntity.ok(reporteService.actualizarReporte(id, dto));
    }

    @Operation(
        summary = "Eliminar reporte",
        description = "Elimina un reporte existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Reporte eliminado exitosamente",
            content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        log.warn("REST: Eliminando REPORTE ID: {}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
