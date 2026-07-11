package com.dsy1103.msproveedores.assemblers;

import com.dsy1103.msproveedores.controller.ProveedorControllerV2;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProveedorModelAssembler implements RepresentationModelAssembler<ProveedorResponseDTO, EntityModel<ProveedorResponseDTO>> {

    @Override
    public EntityModel<ProveedorResponseDTO> toModel(ProveedorResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ProveedorControllerV2.class).getProveedorById(dto.getId())).withSelfRel(),
                linkTo(methodOn(ProveedorControllerV2.class).getAllProveedores()).withRel("proveedores"));
    }
}
