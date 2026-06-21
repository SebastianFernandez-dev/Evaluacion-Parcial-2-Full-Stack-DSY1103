package com.dsy1103.mspagos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.mspagos.controller.PagoControllerV2;
import com.dsy1103.mspagos.dto.PagoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PagoModelAssembler
    implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pagoDTO) {
        return EntityModel.of(pagoDTO,
                linkTo(methodOn(PagoControllerV2.class)
                    .getPagoById(pagoDTO.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class)
                    .getAllPagos()).withRel("pagos"));
    }
}
