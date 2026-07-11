package com.dsy1103.mssucursales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.mssucursales.controller.RegionControllerV2;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RegionModelAssembler implements RepresentationModelAssembler<RegionResponseDTO, EntityModel<RegionResponseDTO>> {

    @Override
    public EntityModel<RegionResponseDTO> toModel(RegionResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(RegionControllerV2.class).getRegionById(dto.getId())).withSelfRel(),
                linkTo(methodOn(RegionControllerV2.class).getAllRegiones()).withRel("regiones"));
    }
}
