package com.dsy1103.msinventario.controller;

import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.dto.InventarioProductoDTO;
import com.dsy1103.msinventario.service.InventarioService;
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
@RequestMapping("/api/v1/inventario")
@Slf4j
@Tag(name="Inventario", description="Operaciones de lectura, creación, actualización y eliminación de inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(
            summary="Listar todos los inventarios",
            description="Retorna una lista completa de todos los registros de inventario disponibles en el sistema.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Lista de inventarios obtenida con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation= InventarioDTO.class)))})
    @GetMapping
    public ResponseEntity<List<InventarioDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los INVENTARIOS");
        return ResponseEntity.ok(inventarioService.listarInventarios());
    }

    @Operation(
            summary="Obtener inventario detallado por ID",
            description="Busca un registro de inventario específico por su ID e incluye la información del producto asociado.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Inventario encontrado con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation= InventarioProductoDTO.class))),
            @ApiResponse(responseCode="404", description="No se encontró ningún inventario con el ID proporcionado",
                    content= @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<InventarioProductoDTO> obtenerInventarioPorId(
            @Parameter(description="ID numérico único del registro de inventario", example="1", required=true)
            @PathVariable Long id) {
        log.info("REST: Buscando INVENTARIO por ID: {}", id);
        return ResponseEntity.ok(inventarioService.obtenerInventarioPorId(id));
    }

    @Operation(
            summary="Filtrar inventarios activos por cantidad mínima",
            description="Retorna una lista de inventarios que se encuentren activos y cuyo stock sea estrictamente mayor al parámetro ingresado.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="200", description="Lista filtrada obtenida con éxito",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation = InventarioDTO.class)))})
    @GetMapping("/mayorque/{cantidad}")
    public ResponseEntity<List<InventarioDTO>> listarCantidadMayor(
            @Parameter(description="Cantidad mínima de stock (filtro exclusivo)", example="10", required=true)
            @PathVariable Integer cantidad) {
        log.info("REST: Solicitud para listar INVENTARIOS con cantidad mayor que {} y activos", cantidad);
        return ResponseEntity.ok(inventarioService.listarInventariosConCantidadMayorActivos(cantidad));
    }

    @Operation(
            summary = "Registrar un nuevo inventario",
            description = "Crea un nuevo registro físico de inventario en el sistema a partir del cuerpo de la solicitud.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="201", description="Inventario creado exitosamente",
                    content= @Content(mediaType="application/json", schema= @Schema(implementation = InventarioDTO.class))),
            @ApiResponse(responseCode="400", description="Estructura del JSON inválida o datos requeridos faltantes",
                    content= @Content)})
    @PostMapping
    public ResponseEntity<InventarioDTO> guardarInventario(@Valid @RequestBody InventarioDTO iDTO) {
        log.info("REST: Creando nuevo INVENTARIO: {}", iDTO.toString());
        InventarioDTO creado = inventarioService.guardarInventario(iDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
            summary="Actualizar un inventario existente",
            description="Modifica las propiedades de un registro de inventario existente. Busca el registro basándose en el ID enviado dentro del JSON.")
    @ApiResponses(value= {
            @ApiResponse(responseCode="204", description="Inventario actualizado correctamente (Sin contenido de respuesta)",
                    content= @Content),
            @ApiResponse(responseCode="400", description="Datos de entrada erróneos o ID inválido",
                    content= @Content),
            @ApiResponse(responseCode="404", description="El inventario que se intenta actualizar no existe",
                    content= @Content)})
    @PutMapping
    public ResponseEntity<?> actualizarIventario(@Valid @RequestBody InventarioDTO iDTO) {
        log.info("REST: Actualizando IVENTARIO: {}", iDTO.toString());
        inventarioService.actualizarInventario(iDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Eliminar un registro de inventario",
            description="Realiza la baja o eliminación lógica/física de un registro de inventario del sistema por medio de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode="204", description="Inventario eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode="404", description="El inventario a eliminar no fue encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInventario(
            @Parameter(description="ID del inventario que se desea remover", example="5", required=true)
            @PathVariable Long id) {
        log.warn("REST: Eliminando IVENTARIO ID: {}", id);
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
