package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.assemblers.ProveedorModelAssembler;
import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/proveedores")
@Slf4j
public class ProveedorControllerV2 {

    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ProveedorModelAssembler proveedorModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ProveedorResponseDTO>> getAllProveedores() {
        List<EntityModel<ProveedorResponseDTO>> proveedores = proveedorService.listarProveedores().stream()
                .map(proveedorModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(proveedores,
                linkTo(methodOn(ProveedorControllerV2.class).getAllProveedores()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProveedorResponseDTO> getProveedorById(@PathVariable Long id) {
        ProveedorResponseDTO proveedorDTO = proveedorService.obtenerProveedorPorId(id);
        return proveedorModelAssembler.toModel(proveedorDTO);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProveedorResponseDTO> createProveedor(@Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO nuevoProveedor = proveedorService.guardarProveedor(dto);
        return proveedorModelAssembler.toModel(nuevoProveedor);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProveedorResponseDTO>> updateProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO actualizado = proveedorService.actualizarProveedor(id, dto);
        return ResponseEntity.ok(proveedorModelAssembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
