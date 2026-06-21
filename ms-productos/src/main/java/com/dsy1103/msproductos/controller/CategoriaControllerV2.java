package com.dsy1103.msproductos.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import com.dsy1103.msproductos.assemblers.CategoriaModelAssembler;
import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.service.CategoriaService;

@RestController
@RequestMapping("/api/v2/categorias")
public class CategoriaControllerV2 {
    
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler categoriaModelAssembler;

    @GetMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<CategoriaDTO>> getAllCategorias() {
        List<EntityModel<CategoriaDTO>> categorias = categoriaService.listarCategorias().stream()
                .map(categoriaModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(categorias, linkTo(methodOn(CategoriaControllerV2.class).getAllCategorias()).withSelfRel());
    }

    @GetMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = categoriaService.obtenerCategoriaPorId(id);
        return categoriaModelAssembler.toModel(categoriaDTO);
    }

    @PostMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.guardarCategoria(categoriaDTO);
        return categoriaModelAssembler.toModel(nuevaCategoria);
    }

    @PutMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> updateCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        categoriaService.actualizarCategoria(categoriaDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
