package com.dsy1103.mspedidos.controller;

import com.dsy1103.mspedidos.assemblers.PedidoAssemblers;
import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.MediaTypes;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoAssemblers assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PedidoDTO>> listarTodos() {
        List<EntityModel<PedidoDTO>> pedidos = pedidoService.listarTodos()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PedidoDTO> buscarPorId(@PathVariable long id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        return assembler.toModel(pedido);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidoDTO>> crear(@RequestBody @Valid PedidoDTO pedido) {
        PedidoDTO pedidoNuevo = pedidoService.crear(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).buscarPorId(pedidoNuevo.getId())).toUri())
                .body(assembler.toModel(pedidoNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidoDTO>> actualizar(@PathVariable long id,@RequestBody PedidoDTO pedido){
        pedido.setId(id);
        pedidoService.actualizar(pedido);
        PedidoDTO actualizar = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(actualizar));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
