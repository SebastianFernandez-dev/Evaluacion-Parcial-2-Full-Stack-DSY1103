package com.dsy1103.mssucursales.assemblers;

import com.dsy1103.mssucursales.controller.SucursalControllerV2;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.stereotype.Component;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<SucursalResponseDTO, EntityModel<SucursalResponseDTO>> {

    @Override
    public EntityModel<SucursalResponseDTO> toModel(SucursalResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(SucursalControllerV2.class).getSucursalById(dto.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).getAllSucursales()).withRel("sucursales"));
    }
}
