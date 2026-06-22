package com.dsy1103.msinventario.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.msinventario.controller.InventarioControllerV2;
import com.dsy1103.msinventario.dto.InventarioDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<InventarioDTO, EntityModel<InventarioDTO>> {

    @Override
    public EntityModel<InventarioDTO> toModel(InventarioDTO inventario) {
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioControllerV2.class).getInventarioById(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventarioControllerV2.class).getAllInventarios()).withRel("inventarios"));
    }
}
