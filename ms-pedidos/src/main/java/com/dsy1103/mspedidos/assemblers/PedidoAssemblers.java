package com.dsy1103.mspedidos.assemblers;


import com.dsy1103.mspedidos.controller.PedidoControllerV2;
import com.dsy1103.mspedidos.dto.PedidoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssemblers implements RepresentationModelAssembler<PedidoDTO, EntityModel<PedidoDTO>> {

    @Override
    public EntityModel<PedidoDTO> toModel(PedidoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PedidoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).listarTodos()).withRel("pedidos"));
    }
}