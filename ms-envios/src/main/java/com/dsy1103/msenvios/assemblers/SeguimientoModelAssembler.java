package com.dsy1103.msenvios.assemblers;

import com.dsy1103.msenvios.controllerV2.SeguimientoControllerV2;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SeguimientoModelAssembler implements RepresentationModelAssembler<SeguimientoResponseDTO, EntityModel<SeguimientoResponseDTO>> {

    @Override
    public EntityModel<SeguimientoResponseDTO> toModel(SeguimientoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(SeguimientoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(SeguimientoControllerV2.class).listarTodos()).withRel("seguimientos"));
    }
}
