package com.dsy1103.mssucursales.controller;

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
import com.dsy1103.mssucursales.assemblers.RegionModelAssembler;
import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import com.dsy1103.mssucursales.service.RegionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v2/regiones")
@Slf4j
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionModelAssembler regionModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<RegionResponseDTO>> getAllRegiones() {
        List<EntityModel<RegionResponseDTO>> regiones = regionService.listarRegiones().stream()
                .map(regionModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(regiones, linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<RegionResponseDTO> getRegionById(@PathVariable Long id) {
        RegionResponseDTO regionDTO = regionService.obtenerRegionPorId(id);
        return regionModelAssembler.toModel(regionDTO);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<RegionResponseDTO> createRegion(@Valid @RequestBody RegionRequestDTO dto) {
        RegionResponseDTO nuevaRegion = regionService.guardarRegion(dto);
        return regionModelAssembler.toModel(nuevaRegion);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionResponseDTO>> updateRegion(@PathVariable Long id, @Valid @RequestBody RegionRequestDTO dto) {
        RegionResponseDTO actualizada = regionService.actualizarRegion(id, dto);
        return ResponseEntity.ok(regionModelAssembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}
