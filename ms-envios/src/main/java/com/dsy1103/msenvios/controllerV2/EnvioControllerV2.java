package com.dsy1103.msenvios.controllerV2;

import com.dsy1103.msenvios.controller.EnvioController;
import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.service.EnvioService;
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
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v2/envios")
@Tag(name = "Envios V2", description = "Endpoints V2 para gestionar envios (con soporte HATEOAS)")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @Operation(
        summary = "Listar todos los envios (V2)",
        description = "Obtiene una lista de todos los envios con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de envios obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EnvioResponseDTO>>> listarTodos() {
        log.info("V2: Listando todos los envios con HATEOAS");
        List<EntityModel<EnvioResponseDTO>> envios = envioService.listarTodos().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<EnvioResponseDTO>> model = CollectionModel.of(envios);
        model.add(linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @Operation(
        summary = "Obtener envio por ID (V2)",
        description = "Obtiene un envio por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Envio obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Envio no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EnvioResponseDTO>> buscarPorId(@PathVariable Long id) {
        log.info("V2: Buscando envio ID: {} con HATEOAS", id);
        EnvioResponseDTO envio = envioService.buscarPorId(id);
        return ResponseEntity.ok(toEntityModel(envio));
    }

    @Operation(
        summary = "Crear nuevo envio (V2)",
        description = "Crea un nuevo envio con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Envio creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<EntityModel<EnvioResponseDTO>> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        log.info("V2: Creando nuevo envio con HATEOAS");
        EnvioResponseDTO creado = envioService.crear(dto);
        EntityModel<EnvioResponseDTO> model = toEntityModel(creado);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @Operation(
        summary = "Actualizar envio (V2)",
        description = "Actualiza un envio existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Envio actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Envio no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EnvioResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody EnvioRequestDTO dto) {
        log.info("V2: Actualizando envio ID: {}", id);
        EnvioResponseDTO actualizado = envioService.actualizar(id, dto);
        return ResponseEntity.ok(toEntityModel(actualizado));
    }

    @Operation(
        summary = "Eliminar envio (V2)",
        description = "Elimina un envio existente por su ID con enlaces HATEOAS")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Envio eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Envio no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("V2: Eliminando envio ID: {}", id);
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<EnvioResponseDTO> toEntityModel(EnvioResponseDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(EnvioControllerV2.class).buscarPorId(dto.getId())).withSelfRel(),
            linkTo(methodOn(EnvioControllerV2.class).listarTodos()).withRel("envios"),
            linkTo(methodOn(EnvioController.class).listarTodos()).withRel("envios-v1"));
    }
}
