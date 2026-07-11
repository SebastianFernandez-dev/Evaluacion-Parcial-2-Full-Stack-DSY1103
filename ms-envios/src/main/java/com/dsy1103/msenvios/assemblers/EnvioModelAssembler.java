package com.dsy1103.msenvios.assemblers;

import com.dsy1103.msenvios.controllerV2.EnvioControllerV2;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<EnvioResponseDTO, EntityModel<EnvioResponseDTO>> {

    @Override
    public EntityModel<EnvioResponseDTO> toModel(EnvioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EnvioControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withRel("envios"));
    }
}
