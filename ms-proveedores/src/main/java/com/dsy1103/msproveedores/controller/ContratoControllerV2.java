package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.assemblers.ContratoModelAssembler;
import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import com.dsy1103.msproveedores.service.ContratoService;
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
@RequestMapping("/api/v2/contratos")
@Slf4j
public class ContratoControllerV2 {

    @Autowired
    private ContratoService contratoService;
    @Autowired
    private ContratoModelAssembler contratoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ContratoResponseDTO>> getAllContratos() {
        List<EntityModel<ContratoResponseDTO>> contratos = contratoService.listarContratos().stream()
                .map(contratoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(contratos,
                linkTo(methodOn(ContratoControllerV2.class).getAllContratos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ContratoResponseDTO> getContratoById(@PathVariable Long id) {
        ContratoResponseDTO contratoDTO = contratoService.obtenerContratoPorId(id);
        return contratoModelAssembler.toModel(contratoDTO);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ContratoResponseDTO> createContrato(@Valid @RequestBody ContratoRequestDTO dto) {
        ContratoResponseDTO nuevoContrato = contratoService.guardarContrato(dto);
        return contratoModelAssembler.toModel(nuevoContrato);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ContratoResponseDTO>> updateContrato(@PathVariable Long id, @Valid @RequestBody ContratoRequestDTO dto) {
        ContratoResponseDTO actualizado = contratoService.actualizarContrato(id, dto);
        return ResponseEntity.ok(contratoModelAssembler.toModel(actualizado));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        contratoService.eliminarContrato(id);
        return ResponseEntity.noContent().build();
    }
}
