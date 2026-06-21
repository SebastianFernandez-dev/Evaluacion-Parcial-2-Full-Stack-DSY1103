package com.dsy1103.msproductos.controller;

import com.dsy1103.msproductos.assemblers.ProductoModelAssembler;
import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.service.ProductoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoModelAssembler;

    @GetMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<ProductoDTO>> getAllProductos() {
        List<EntityModel<ProductoDTO>> productos = productoService.listarProductos().stream()
                .map(productoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(productos, linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());

    }

    @GetMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.obtenerProductoPorId(id);
        return productoModelAssembler.toModel(productoDTO);
    }

    @PostMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO nuevoProducto = productoService.guardarProducto(productoDTO);
        return productoModelAssembler.toModel(nuevoProducto);
    }

    @PutMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProductoDTO>> updateProducto(@RequestBody ProductoDTO productoDTO) {
        productoService.actualizarProducto(productoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
