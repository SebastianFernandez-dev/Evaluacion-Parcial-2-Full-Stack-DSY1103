package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import com.dsy1103.msproveedores.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contratos")
@Slf4j
@Tag(name="Contratos", description="Endpoints para gestionar los contratos de proveedores")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Operation(
        summary="Listar todos los contratos",
        description="Obtiene una lista con todos los contratos disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de contratos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ContratoResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ContratoResponseDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los CONTRATOS");
        return ResponseEntity.ok(contratoService.listarContratos());
    }

    @Operation(
        summary="Obtener contrato por ID",
        description="Obtiene los detalles de un contrato específico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contrato obtenido exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ContratoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contrato no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponseDTO> obtenerContratoPorId(@PathVariable Long id) {
        log.info("REST: Buscando CONTRATO por ID: {}", id);
        return ResponseEntity.ok(contratoService.obtenerContratoPorId(id));
    }

    @Operation(
        summary="Listar contratos por proveedor",
        description="Obtiene una lista de contratos asociados a un proveedor específico")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de contratos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ContratoResponseDTO.class)
            )
        )
    })
    @GetMapping("/porproveedor/{id}")
    public ResponseEntity<List<ContratoResponseDTO>> listarPorProveedor(@PathVariable Long id) {
        log.info("REST: Solicitud para listar CONTRATOS por PROVEEDOR ID: {}", id);
        return ResponseEntity.ok(contratoService.listarContratosPorProveedor(id));
    }

    @Operation(
        summary="Crear nuevo contrato",
        description="Crea un nuevo contrato con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Contrato creado exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ContratoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<ContratoResponseDTO> guardarContrato(@Valid @RequestBody ContratoRequestDTO dto) {
        log.info("REST: Creando nuevo CONTRATO: {}", dto.getNumero());
        ContratoResponseDTO creado = contratoService.guardarContrato(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
        summary="Actualizar contrato existente",
        description="Actualiza los datos de un contrato existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contrato actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",schema = @Schema(implementation = ContratoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contrato no encontrado",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContratoResponseDTO> actualizarContrato(@PathVariable Long id, @Valid @RequestBody ContratoRequestDTO dto) {
        log.info("REST: Actualizando CONTRATO ID: {}", id);
        return ResponseEntity.ok(contratoService.actualizarContrato(id, dto));
    }

    @Operation(
        summary="Eliminar contrato",
        description="Elimina un contrato existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Contrato eliminado exitosamente",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Contrato no encontrado",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarContrato(@PathVariable Long id) {
        log.warn("REST: Eliminando CONTRATO ID: {}", id);
        contratoService.eliminarContrato(id);
        return ResponseEntity.noContent().build();
    }
}
