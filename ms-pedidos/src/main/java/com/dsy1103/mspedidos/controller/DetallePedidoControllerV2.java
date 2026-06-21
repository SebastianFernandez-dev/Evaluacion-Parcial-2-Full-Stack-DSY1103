package com.dsy1103.mspedidos.controller;


import com.dsy1103.mspedidos.assemblers.DetallePedidoAssemblers;
import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.service.DetallePedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/DetallePedidos")
public class DetallePedidoControllerV2 {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private DetallePedidoAssemblers assemblers;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<DetallePedidoDTO>> listarTodos(){
        List<EntityModel<DetallePedidoDTO>> detallepedido = detallePedidoService.listarTodos()
                .stream()
                .map(assemblers::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(detallepedido,
                linkTo(methodOn(DetallePedidoControllerV2.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<DetallePedidoDTO> buscarPorId(@PathVariable long id) {
        DetallePedidoDTO detallepedido = detallePedidoService.buscarPorId(id);
        return assemblers.toModel(detallepedido);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetallePedidoDTO>> crear(@RequestBody @Valid DetallePedidoDTO detallepedido) {
        DetallePedidoDTO detallepedidoNuevo = detallePedidoService.crear(detallepedido);
        return ResponseEntity
                .created(linkTo(methodOn(DetallePedidoControllerV2.class).buscarPorId(detallepedidoNuevo.getId())).toUri())
                .body(assemblers.toModel(detallepedidoNuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetallePedidoDTO>> actualizar(@PathVariable long id,@RequestBody DetallePedidoDTO detallepedido) {
        detallepedido.setId(id);
        detallePedidoService.actualizar(detallepedido);
        DetallePedidoDTO actualizar = detallePedidoService.buscarPorId(id);
        return ResponseEntity.ok(assemblers.toModel(actualizar));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
