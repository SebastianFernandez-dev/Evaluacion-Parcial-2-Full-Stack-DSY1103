package com.dsy1103.msempleados.assemblers;

import com.dsy1103.msempleados.controller.EmpleadoControllerV2;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.stereotype.Component;

@Component
public class EmpleadoModelAssembler implements RepresentationModelAssembler<EmpleadoResponseDTO, EntityModel<EmpleadoResponseDTO>> {

    @Override
    public EntityModel<EmpleadoResponseDTO> toModel(EmpleadoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EmpleadoControllerV2.class).getEmpleadoById(dto.getId())).withSelfRel(),
                linkTo(methodOn(EmpleadoControllerV2.class).getAllEmpleados()).withRel("empleados"));
    }

}
