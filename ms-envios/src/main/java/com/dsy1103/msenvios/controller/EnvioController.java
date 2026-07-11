package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.modelo.EnvioModelo;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/envios")
@Tag(name="Envios", description="Endpoints para gestionar los envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Operation(
        summary="Listar todos los envios",
        description="Obtiene una lista con todos los envios disponibles")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de envios obtenida exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<EnvioResponseDTO>> listarTodos() {
        log.info("Recibida solicitud para listar todos los envios");
        return ResponseEntity.ok(envioService.listarTodos());
    }

    @Operation(
        summary="Obtener envio por ID",
        description="Obtiene los detalles de un envio especifico utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Envio obtenido exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Envio no encontrado",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("Recibida solicitud para buscar envio por ID: {}", id);
        return ResponseEntity.ok(envioService.buscarPorId(id));
    }

    @Operation(
        summary="Crear nuevo envio",
        description="Crea un nuevo envio con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Envio creado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = EnvioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos",
            content = @Content
        )
    })
    @PostMapping
    public ResponseEntity<EnvioResponseDTO> crear(@Valid @RequestBody EnvioRequestDTO dto) {
        log.info("Recibida solicitud para crear un nuevo Envio");
        return new ResponseEntity<>(envioService.crear(dto), HttpStatus.CREATED);
    }

    @Operation(
        summary="Actualizar envio existente",
        description="Actualiza los datos de un envio existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Envio actualizado exitosamente",
            content = @Content(
                mediaType = "application/json", schema = @Schema(implementation = EnvioResponseDTO.class)
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
    public ResponseEntity<EnvioResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EnvioRequestDTO dto) {
        log.info("REST: Actualizando ENVIO ID: {}", id);
        return ResponseEntity.ok(envioService.actualizar(id, dto));
    }

    @Operation(
        summary="Eliminar envio",
        description="Elimina un envio existente utilizando su ID")
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
        log.info("Recibida solicitud para eliminar Envio con ID: {}", id);
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/no-entregados")
    public ResponseEntity<List<EnvioModelo>> listarNoEntregados(
            @RequestParam("inicio") String inicioStr,
            @RequestParam("fin") String finStr) {

        LocalDateTime inicio = LocalDateTime.parse(inicioStr);
        LocalDateTime fin = LocalDateTime.parse(finStr);

        List<EnvioModelo> lista = envioService.obtenerEnviosEnRangoNoEntregados(inicio, fin);
        return ResponseEntity.ok(lista);
    }
}
