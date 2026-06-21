package com.dsy1103.msusuarios.controller;


import com.dsy1103.msusuarios.assemblers.UsuarioAssemblers;
import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<UsuarioDTO>> listarTodos() {
        List<EntityModel<UsuarioDTO>> usuario = usuarioService.listarTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UsuarioDTO> buscarPorId(@PathVariable long id) {
        UsuarioDTO pedido = usuarioService.buscarPorId(id);
        return assembler.toModel(pedido);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> crear(@RequestBody @Valid UsuarioDTO usuario) {
        UsuarioDTO usuarionuevo = usuarioService.crear(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(usuarionuevo.getId())).toUri())
                .body(assembler.toModel(usuarionuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizar(@PathVariable long id,@RequestBody UsuarioDTO usuario){
        usuario.setId(id);
        usuarioService.actualizar(usuario);
        UsuarioDTO actualizar = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(actualizar));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
