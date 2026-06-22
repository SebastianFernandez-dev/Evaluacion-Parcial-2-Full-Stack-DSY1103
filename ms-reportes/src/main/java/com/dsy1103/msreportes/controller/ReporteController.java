package com.dsy1103.msreportes.controller;

import com.dsy1103.msreportes.dto.ReporteDTO;
import com.dsy1103.msreportes.dto.ReporteUsuarioDTO;
import com.dsy1103.msreportes.service.ReporteService;
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
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los REPORTES");
        return ResponseEntity.ok(reporteService.listarReportes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteUsuarioDTO> obtenerReportePorId(@PathVariable Long id) {
        log.info("REST: Buscando REPORTE por ID: {}", id);
        return ResponseEntity.ok(reporteService.obtenerReportePorId(id));
    }

    @GetMapping("/porusuario/{id}")
    public ResponseEntity<List<ReporteDTO>> listarPorUsuario(@PathVariable Long id) {
        log.info("REST: Solicitud para listar REPORTES por USUARIO ID {}", id);
        return ResponseEntity.ok(reporteService.listarReportePorUsuario(id));
    }

    @PostMapping
    public ResponseEntity<ReporteDTO> guardarReporte(@Valid @RequestBody ReporteDTO rDTO) {
        log.info("REST: Creando nuevo REPORTE: {}", rDTO.toString());
        ReporteDTO creado = reporteService.guardarReporte(rDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<?> actualizarReporte(@Valid @RequestBody ReporteDTO rDTO) {
        log.info("REST: Actualizando REPORTE: {}", rDTO.toString());
        reporteService.actualizarReporte(rDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReporte(@PathVariable Long id) {
        log.warn("REST: Eliminando REPORTE ID: {}", id);
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
