package com.dsy1103.mspedidos.assemblers;

import com.dsy1103.mspedidos.controller.DetallePedidoControllerV2;
import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DetallePedidoAssemblers implements RepresentationModelAssembler<DetallePedidoDTO, EntityModel<DetallePedidoDTO>> {

    @Override
    public EntityModel<DetallePedidoDTO> toModel(DetallePedidoDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(DetallePedidoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(DetallePedidoControllerV2.class).listarTodos()).withRel("DetallePedidos"));
    }

}
