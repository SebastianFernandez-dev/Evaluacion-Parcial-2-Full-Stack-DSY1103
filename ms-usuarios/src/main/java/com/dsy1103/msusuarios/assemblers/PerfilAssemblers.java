package com.dsy1103.msusuarios.assemblers;

import com.dsy1103.msusuarios.controller.PerfilControllerV2;
import com.dsy1103.msusuarios.controller.UsuarioControllerV2;
import com.dsy1103.msusuarios.dto.PerfilDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PerfilAssemblers implements RepresentationModelAssembler<PerfilDTO, EntityModel<PerfilDTO>> {

    @Override
    public EntityModel<PerfilDTO> toModel(PerfilDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PerfilControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(PerfilControllerV2.class).listarTodos()).withRel("Perfil"));
    }

}
