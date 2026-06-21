package com.dsy1103.mspedidos.controller;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import com.dsy1103.mspedidos.service.PedidoService;
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
@RequestMapping("/api/v1/pedidos")
@Tag(name="Pedidos", description="Endpoints para gestionar pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(
            summary="Listar todos los pedidos",
            description="Obtiene una lista con todos los pedidos registrados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PedidoDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        log.info("Solicitando listado global de pedidos");
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(
            summary="Obtener pedido por ID",
            description="Obtiene los detalles de un pedido específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PedidoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando pedido con ID: {}", id);
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @Operation(
            summary="Obtener pedidos por ID de usuario",
            description="Obtiene una lista de pedidos asociados a un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos del usuario obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PedidoDTO.class)
                    )
            )
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        log.info("Solicitando pedidos del usuario ID: {}", usuarioId);
        return ResponseEntity.ok(pedidoService.buscarPorUsuarioId(usuarioId));
    }

    @Operation(
            summary="Crear nuevo pedido",
            description="Crea un nuevo pedido con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PedidoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<PedidoDTO> crear(@Valid @RequestBody PedidoDTO dto) {
        log.info("Recibida nueva solicitud de compra para el usuario ID: {}", dto.getUsuarioId());
        PedidoDTO creado = pedidoService.crear(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @Operation(
            summary="Actualizar pedido existente",
            description="Actualiza los datos de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido actualizado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content
            )
    })
    @PutMapping
    public ResponseEntity<?> actualizar(@Valid @RequestBody PedidoDTO pDTO) {
        log.info("REST: Actualizando PEDIDO: {}", pDTO.toString());
        pedidoService.actualizar(pDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Eliminar pedido",
            description="Elimina un pedido existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido no encontrado",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Solicitud para eliminar pedido ID: {}", id);
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Listar pedidos pagados",
            description="Obtiene una lista de pedidos pagados ordenados por fecha")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos pagados obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PedidoModelo.class)
                    )
            )
    })
    @GetMapping("/pagados")
    public ResponseEntity<List<PedidoModelo>> listarPedidosPagados() {
        List<PedidoModelo> lista = pedidoService.obtenerPedidosPagadosYOrdenados();

        return ResponseEntity.ok(lista);
    }

}
