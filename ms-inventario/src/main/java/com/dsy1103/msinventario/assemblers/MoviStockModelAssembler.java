package com.dsy1103.msinventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.msinventario.controller.MoviStockControllerV2;
import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MoviStockModelAssembler implements RepresentationModelAssembler<MovimientoStockDTO, EntityModel<MovimientoStockDTO>> {

    @Override
    public EntityModel<MovimientoStockDTO> toModel(MovimientoStockDTO movimientoStock) {
        return EntityModel.of(movimientoStock,
                linkTo(methodOn(MoviStockControllerV2.class).getMovimientoById(movimientoStock.getId())).withSelfRel(),
                linkTo(methodOn(MoviStockControllerV2.class).getAllMovimientos()).withRel("movimientos"));
    }
}
