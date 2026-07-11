package com.dsy1103.msreportes.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import com.dsy1103.msreportes.assemblers.ReporteModelAssembler;
import com.dsy1103.msreportes.dto.ReporteRequestDTO;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import com.dsy1103.msreportes.service.ReporteService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v2/reportes")
@Slf4j
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ReporteModelAssembler reporteModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ReporteResponseDTO>> getAllReportes() {
        List<EntityModel<ReporteResponseDTO>> reportes = reporteService.listarReportes().stream()
                .map(reporteModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reportes, linkTo(methodOn(ReporteControllerV2.class).getAllReportes()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ReporteResponseDTO> getReporteById(@PathVariable Long id) {
        ReporteResponseDTO reporteDTO = reporteService.listarReportes().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Reporte no encontrado"));
        return reporteModelAssembler.toModel(reporteDTO);
    }

    @GetMapping(value = "/porusuario/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ReporteResponseDTO>> getReportesByUsuario(@PathVariable Long id) {
        List<EntityModel<ReporteResponseDTO>> reportes = reporteService.listarReportePorUsuario(id).stream()
                .map(reporteModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(reportes, linkTo(methodOn(ReporteControllerV2.class).getAllReportes()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ReporteResponseDTO> createReporte(@Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO nuevoReporte = reporteService.guardarReporte(dto);
        return reporteModelAssembler.toModel(nuevoReporte);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ReporteResponseDTO>> updateReporte(@PathVariable Long id, @Valid @RequestBody ReporteRequestDTO dto) {
        ReporteResponseDTO actualizado = reporteService.actualizarReporte(id, dto);
        return ResponseEntity.ok(reporteModelAssembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
