package com.dsy1103.msreportes.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.msreportes.controller.ReporteControllerV2;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<ReporteResponseDTO, EntityModel<ReporteResponseDTO>> {

    @Override
    public EntityModel<ReporteResponseDTO> toModel(ReporteResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ReporteControllerV2.class).getReporteById(dto.getId())).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).getAllReportes()).withRel("reportes"));
    }
}
