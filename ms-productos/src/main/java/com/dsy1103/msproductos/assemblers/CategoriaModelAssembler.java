package com.dsy1103.msproductos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.msproductos.controller.CategoriaControllerV2;
import com.dsy1103.msproductos.dto.CategoriaDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>> {
    
    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO categoriaDTO) {
        return EntityModel.of(categoriaDTO,
                linkTo(methodOn(CategoriaControllerV2.class).getCategoriaById(categoriaDTO.getId())).withSelfRel(),
                linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withRel("categorias"));
    }
}
