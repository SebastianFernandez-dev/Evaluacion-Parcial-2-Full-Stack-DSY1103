package com.dsy1103.msenvios.controllerV2;

import com.dsy1103.msenvios.controller.SeguimientoController;
import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.service.SeguimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v2/seguimientos")
@Tag(name = "Seguimientos V2", description = "Endpoints V2 para gestionar seguimientos (con soporte HATEOAS)")
public class SeguimientoControllerV2 {

    @Autowired
    private SeguimientoService seguimientoService;

    @Operation(
        summary = "Listar todos los seguimientos (V2)",
        description = "Obtiene una lista de todos los seguimientos con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de seguimientos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<SeguimientoResponseDTO>>> listarTodos() {
        log.info("V2: Listando todos los seguimientos con HATEOAS");
        List<EntityModel<SeguimientoResponseDTO>> seguimientos = seguimientoService.listarTodos().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<SeguimientoResponseDTO>> model = CollectionModel.of(seguimientos);
        model.add(linkTo(methodOn(SeguimientoControllerV2.class).listarTodos()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @Operation(
        summary = "Obtener seguimiento por ID (V2)",
        description = "Obtiene un seguimiento por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seguimiento obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<SeguimientoResponseDTO>> buscarPorId(@PathVariable Long id) {
        log.info("V2: Buscando seguimiento ID: {} con HATEOAS", id);
        SeguimientoResponseDTO seguimiento = seguimientoService.buscarPorId(id);
        return ResponseEntity.ok(toEntityModel(seguimiento));
    }

    @Operation(
        summary = "Crear nuevo seguimiento (V2)",
        description = "Crea un nuevo seguimiento con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Seguimiento creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<EntityModel<SeguimientoResponseDTO>> crear(@Valid @RequestBody SeguimientoRequestDTO dto) {
        log.info("V2: Creando nuevo seguimiento con HATEOAS");
        SeguimientoResponseDTO creado = seguimientoService.crear(dto);
        EntityModel<SeguimientoResponseDTO> model = toEntityModel(creado);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @Operation(
        summary = "Actualizar seguimiento (V2)",
        description = "Actualiza un seguimiento existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Seguimiento actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SeguimientoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<SeguimientoResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody SeguimientoRequestDTO dto) {
        log.info("V2: Actualizando seguimiento ID: {}", id);
        SeguimientoResponseDTO actualizado = seguimientoService.actualizar(id, dto);
        return ResponseEntity.ok(toEntityModel(actualizado));
    }

    @Operation(
        summary = "Eliminar seguimiento (V2)",
        description = "Elimina un seguimiento existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Seguimiento eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Seguimiento no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("V2: Eliminando seguimiento ID: {}", id);
        seguimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<SeguimientoResponseDTO> toEntityModel(SeguimientoResponseDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(SeguimientoControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(SeguimientoControllerV2.class).listarTodos()).withRel("seguimientos"),
            linkTo(methodOn(SeguimientoController.class).listarTodos()).withRel("seguimientos-v1"));
    }
}
