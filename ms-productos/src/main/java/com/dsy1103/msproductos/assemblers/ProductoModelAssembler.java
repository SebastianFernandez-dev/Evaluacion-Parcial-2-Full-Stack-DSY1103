package com.dsy1103.msproductos.assemblers;

import com.dsy1103.msproductos.controller.ProductoControllerV2;
import com.dsy1103.msproductos.dto.ProductoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<ProductoDTO, EntityModel<ProductoDTO>> {
    
    @Override
    public EntityModel<ProductoDTO> toModel(ProductoDTO productoDTO) {
        return EntityModel.of(productoDTO,
                linkTo(methodOn(ProductoControllerV2.class).getProductoById(productoDTO.getId())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withRel("productos"));
    }
    
}
