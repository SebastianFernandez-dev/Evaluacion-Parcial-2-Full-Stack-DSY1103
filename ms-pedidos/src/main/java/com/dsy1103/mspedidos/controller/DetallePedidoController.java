package com.dsy1103.mspedidos.controller;

import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.service.DetallePedidoService;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/detalles-pedido")
@Tag(name="Detalles de Pedido", description="Endpoints para gestionar los detalles de un pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detalleService;

    @Operation(
            summary="Listar todos los detalles de pedido",
            description="Obtiene una lista con todos los detalles de pedido registrados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de detalles obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = DetallePedidoDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> listarTodos() {
        log.info("Solicitando lista de todos los detalles");
        return ResponseEntity.ok(detalleService.listarTodos());
    }

    @Operation(
            summary="Obtener detalle de pedido por ID",
            description="Obtiene los detalles de un detalle de pedido específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = DetallePedidoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Detalle no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando detalle con ID: {}", id);
        return ResponseEntity.ok(detalleService.buscarPorId(id));
    }

    @Operation(
            summary="Crear nuevo detalle de pedido",
            description="Crea un nuevo detalle de pedido con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Detalle creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = DetallePedidoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<DetallePedidoDTO> crear(@Valid @RequestBody DetallePedidoDTO dto) {
        log.info("Recibida solicitud para crear detalle de producto ID: {}", dto.getProductoId());
        DetallePedidoDTO creado = detalleService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(
            summary="Actualizar detalle de pedido existente",
            description="Actualiza los datos de un detalle de pedido existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Detalle actualizado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Detalle no encontrado",
                    content = @Content
            )
    })
    @PutMapping
    public ResponseEntity<?> actualizar(@Valid @RequestBody DetallePedidoDTO dDTO) {
        log.info("Actualizando DETALLE PEDIDO: {}", dDTO.toString());
        detalleService.actualizar(dDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Eliminar detalle de pedido",
            description="Elimina un detalle de pedido existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Detalle eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Detalle no encontrado",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar detalle ID: {}", id);
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}