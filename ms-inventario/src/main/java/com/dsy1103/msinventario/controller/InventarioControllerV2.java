package com.dsy1103.msinventario.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.msinventario.assemblers.InvProdModelAssembler;
import com.dsy1103.msinventario.assemblers.InventarioModelAssembler;
import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.dto.InventarioProductoDTO;
import com.dsy1103.msinventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/inventario")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private InventarioModelAssembler inventarioModelAssembler;
    @Autowired
    private InvProdModelAssembler invProdModelAssembler;

    @GetMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<InventarioDTO>> getAllInventarios() {
        List<EntityModel<InventarioDTO>> listaIventarios = inventarioService.listarInventarios()
                .stream()
                .map(inventarioModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(listaIventarios,
                linkTo(methodOn(InventarioControllerV2.class).getAllInventarios()).withSelfRel());
    }

    @GetMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public EntityModel<InventarioProductoDTO> getInventarioById(@PathVariable Long id) {
        InventarioProductoDTO dto = inventarioService.obtenerInventarioPorId(id);
        return invProdModelAssembler.toModel(dto);
    }

    @PostMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioDTO>> createInventario(@RequestBody InventarioDTO inventario) {
        InventarioDTO newInventario = inventarioService.guardarInventario(inventario);
        return ResponseEntity
                .created(linkTo(methodOn(InventarioControllerV2.class).getInventarioById(newInventario.getId())).toUri())
                .body(inventarioModelAssembler.toModel(newInventario));
    }

    @PutMapping(produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<InventarioDTO>> updateInventario(@RequestBody InventarioDTO inventario) {
        inventarioService.actualizarInventario(inventario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
