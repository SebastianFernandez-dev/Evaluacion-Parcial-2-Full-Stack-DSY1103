package com.dsy1103.msinventario.controller;

import com.dsy1103.msinventario.dto.MovimientoStockDTO;
import com.dsy1103.msinventario.service.MovimientoStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/movimientostock")
@Slf4j
@Tag(name="Movimiento de Stock", description="Operaciones para el registro y consulta del historial de movimientos de inventario (Entradas/Salidas)")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService movimientoStockService;

    @Operation(
            summary="Listar todos los movimientos de stock",
            description="Retorna el historial completo de todos los movimientos de stock registrados en el sistema.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Historial de movimientos obtenido con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation = MovimientoStockDTO.class)))})
    @GetMapping
    public ResponseEntity<List<MovimientoStockDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los MOVIMIENTOS");
        return ResponseEntity.ok(movimientoStockService.listarMovimientos());
    }

    @Operation(
            summary = "Obtener un movimiento de stock por su ID",
            description = "Busca un registro específico de movimiento de stock mediante su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode="200", description="Movimiento de stock encontrado con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation = MovimientoStockDTO.class))),
            @ApiResponse(responseCode="404", description="No existe ningún movimiento con el ID proporcionado",
                    content= @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockDTO> obtenerMovimientoPorId(
            @Parameter(description="ID numérico único del movimiento de stock", example="101", required=true)
            @PathVariable Long id) {
        log.info("REST: Buscando MOVIMIENTO por ID: {}", id);
        return ResponseEntity.ok(movimientoStockService.obtenerMovimientoPorId(id));
    }

    @Operation(
            summary="Listar movimientos asociados a un inventario específico",
            description="Retorna una lista de todos los movimientos (kardex) que ha tenido un artículo de inventario en particular.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Lista de movimientos del inventario obtenida con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation= MovimientoStockDTO.class))),
            @ApiResponse(responseCode ="404", description="El ID de inventario proporcionado no existe", content= @Content)})
    @GetMapping("/porinventario/{id}")
    public ResponseEntity<List<MovimientoStockDTO>> listarPorInventario(
            @Parameter(description="ID del registro de inventario para filtrar sus movimientos", example="1", required=true)
            @PathVariable Long id) {
        log.info("REST: Solicitud para listar MOVIMIENTO por INVENTARIO ID: {}", id);
        return ResponseEntity.ok(movimientoStockService.listarMovimientosPorInventario(id));
    }

    @Operation(
            summary="Registrar un nuevo movimiento de stock",
            description="Crea un nuevo registro de movimiento (como una entrada por compra o salida por venta) y actualiza el flujo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode="201", description="Movimiento de stock registrado exitosamente",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation = MovimientoStockDTO.class))),
            @ApiResponse(responseCode="400", description="Datos de entrada inválidos, estructura JSON malformada o inconsistencia en el stock", content= @Content)})
    @PostMapping
    public ResponseEntity<MovimientoStockDTO> guardarMovimiento(@Valid @RequestBody MovimientoStockDTO mDTO) {
        log.info("REST: Creando nuevo MOVIMIENTO: {}", mDTO.toString());
        MovimientoStockDTO creado = movimientoStockService.guardarMovimiento(mDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
            summary="Actualizar un movimiento de stock existente",
            description="Modifica las propiedades de un registro de movimiento. El registro afectado se localiza mediante el ID provisto en el JSON.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="204", description="Movimiento actualizado correctamente (Sin contenido en el cuerpo)", content= @Content),
            @ApiResponse(responseCode="400", description="Datos del cuerpo erróneos o ID inválido", content= @Content),
            @ApiResponse(responseCode="404", description="El registro de movimiento que se intenta modificar no existe", content= @Content)})
    @PutMapping
    public ResponseEntity<?> actualizarMovimiento(@Valid @RequestBody MovimientoStockDTO mDTO) {
        log.info("REST: Actualizando Movimiento: {}", mDTO.toString());
        movimientoStockService.actualizarMovimiento(mDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Eliminar un registro de movimiento de stock",
            description="Remueve de forma física o lógica un registro de movimiento del historial utilizando su ID.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="204", description="Movimiento eliminado con éxito", content= @Content),
            @ApiResponse(responseCode="404", description="El registro de movimiento a eliminar no fue encontrado", content= @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMovimiento(
            @Parameter(description="ID del movimiento de stock que se desea eliminar", example="50", required=true)
            @PathVariable Long id) {
        log.warn("REST: Eliminando MOVIMIENTO ID: {}", id);
        movimientoStockService.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
