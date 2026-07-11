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
import com.dsy1103.mssucursales.assemblers.SucursalModelAssembler;
import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.service.SucursalService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v2/sucursales")
@Slf4j
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<SucursalResponseDTO>> getAllSucursales() {
        List<EntityModel<SucursalResponseDTO>> sucursales = sucursalService.listarSucursales().stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(sucursales, linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<SucursalResponseDTO> getSucursalById(@PathVariable Long id) {
        SucursalResponseDTO sucursalDTO = sucursalService.obtenerSucursalPorId(id);
        return sucursalModelAssembler.toModel(sucursalDTO);
    }

    @GetMapping(value = "/porregion/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<SucursalResponseDTO>> getSucursalesByRegion(@PathVariable String nombre) {
        List<EntityModel<SucursalResponseDTO>> sucursales = sucursalService.listarSucursalesPorRegion(nombre).stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(sucursales, linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<SucursalResponseDTO> createSucursal(@Valid @RequestBody SucursalRequestDTO dto) {
        SucursalResponseDTO nuevaSucursal = sucursalService.guardarSucursal(dto);
        return sucursalModelAssembler.toModel(nuevaSucursal);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<SucursalResponseDTO>> updateSucursal(@PathVariable Long id, @Valid @RequestBody SucursalRequestDTO dto) {
        SucursalResponseDTO actualizada = sucursalService.actualizarSucursal(id, dto);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(actualizada));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteSucursal(@PathVariable Long id) {
        sucursalService.eliminarSucursal(id);
        return ResponseEntity.noContent().build();
    }
}
