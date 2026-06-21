package com.dsy1103.mspagos.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.dsy1103.mspagos.assemblers.PagoModelAssembler;
import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.service.PagoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler pagoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PagoDTO>> getAllPagos() {
        List<EntityModel<PagoDTO>> pagos = pagoService.listarPagos().stream()
                .map(pagoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<PagoDTO> getPagoById(@PathVariable Long id) {
        return pagoModelAssembler.toModel(pagoService.obtenerPagoPorId(id));
    }

    @GetMapping(value = "/filtrar", produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<PagoDTO>> filtrarPorMontoYEstado(
            @RequestParam Double monto,
            @RequestParam String estadoPago) {
        List<EntityModel<PagoDTO>> pagos = pagoService.buscarPorMontoYEstado(monto, estadoPago).stream()
                .map(pagoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoControllerV2.class).getAllPagos()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> createPago(@Valid @RequestBody PagoDTO dto) {
        PagoDTO nuevo = pagoService.guardarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoModelAssembler.toModel(nuevo));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> updatePago(@PathVariable Long id,
            @Valid @RequestBody PagoDTO dto) {
        dto.setId(id);
        pagoService.actualizarPago(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
