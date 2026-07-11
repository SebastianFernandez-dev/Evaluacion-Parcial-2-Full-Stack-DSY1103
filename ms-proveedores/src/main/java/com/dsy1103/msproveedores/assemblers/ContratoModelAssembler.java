package com.dsy1103.msproveedores.assemblers;

import com.dsy1103.msproveedores.controller.ContratoControllerV2;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ContratoModelAssembler implements RepresentationModelAssembler<ContratoResponseDTO, EntityModel<ContratoResponseDTO>> {

    @Override
    public EntityModel<ContratoResponseDTO> toModel(ContratoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ContratoControllerV2.class).getContratoById(dto.getId())).withSelfRel(),
                linkTo(methodOn(ContratoControllerV2.class).getAllContratos()).withRel("contratos"));
    }
}
