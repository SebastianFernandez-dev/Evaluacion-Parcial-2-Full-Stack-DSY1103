package com.dsy1103.msusuarios.assemblers;


import com.dsy1103.msusuarios.controller.UsuarioControllerV2;
import com.dsy1103.msusuarios.dto.UsuarioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UsuarioAssemblers implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {

    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UsuarioControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarTodos()).withRel("Usuarios"));
    }

}
