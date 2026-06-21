package com.dsy1103.msusuarios.controller;


import com.dsy1103.msusuarios.assemblers.PerfilAssemblers;
import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/perfiles")
public class PerfilControllerV2 {

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PerfilDTO>> listarTodos() {
        List<EntityModel<PerfilDTO>> perfil = perfilService.listarTodo()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(perfil,
                linkTo(methodOn(PerfilControllerV2.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PerfilDTO> buscarPorId(@PathVariable long id) {
        PerfilDTO perfil = perfilService.buscarPorId(id);
        return assembler.toModel(perfil);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PerfilDTO>> crear(@RequestBody @Valid PerfilDTO perfil) {
        PerfilDTO perfilnuevo = perfilService.crear(perfil);
        return ResponseEntity
                .created(linkTo(methodOn(PerfilControllerV2.class).buscarPorId(perfilnuevo.getId())).toUri())
                .body(assembler.toModel(perfilnuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PerfilDTO>> actualizar(@PathVariable long id,@RequestBody PerfilDTO perfil){
        perfil.setId(id);
        perfilService.actualizar(perfil);
        PerfilDTO actualizar = perfilService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(actualizar));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        perfilService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
