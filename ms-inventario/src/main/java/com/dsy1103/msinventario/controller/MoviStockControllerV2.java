package com.dsy1103.msinventario.controller;

import com.dsy1103.msinventario.assemblers.MoviStockModelAssembler;
import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.service.MovimientoStockService;
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
@RequestMapping("/api/v2/movimientostock")
public class MoviStockControllerV2 {

    @Autowired
    private MovimientoStockService movimientoStockService;
    @Autowired
    MoviStockModelAssembler moviStockModelAssembler;

    @GetMapping(produces= MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<MovimientoStockDTO>> getAllMovimientos() {
        List<EntityModel<MovimientoStockDTO>> listaMovimientos = movimientoStockService.listarMovimientos()
                .stream()
                .map(moviStockModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(listaMovimientos,
                linkTo(methodOn(MoviStockControllerV2.class).getAllMovimientos()).withSelfRel());
    }


    @GetMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<MovimientoStockDTO> getMovimientoById(@PathVariable Long id) {
        MovimientoStockDTO dto = movimientoStockService.obtenerMovimientoPorId(id);
        return moviStockModelAssembler.toModel(dto);
    }

    @PostMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MovimientoStockDTO>> createMovimiento(@RequestBody MovimientoStockDTO movimientoStock) {
        MovimientoStockDTO newMovimiento = movimientoStockService.guardarMovimiento(movimientoStock);
        return ResponseEntity
                .created(linkTo(methodOn(MoviStockControllerV2.class).getMovimientoById(newMovimiento.getId())).toUri())
                .body(moviStockModelAssembler.toModel(newMovimiento));
    }

    @PutMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<MovimientoStockDTO>> updateMovimiento(@RequestBody MovimientoStockDTO movimientoStock) {
        movimientoStockService.actualizarMovimiento(movimientoStock);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteInventario(@PathVariable Long id) {
        movimientoStockService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
