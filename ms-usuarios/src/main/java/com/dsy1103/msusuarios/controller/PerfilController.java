package com.dsy1103.msusuarios.controller;

import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.service.PerfilService;
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
@RequestMapping("/api/v1/perfiles")
@Tag(name="Perfiles", description="Endpoints para gestionar perfiles de usuario")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @Operation(
            summary="Listar todos los perfiles",
            description="Obtiene una lista con todos los perfiles registrados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de perfiles obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PerfilDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<PerfilDTO>> listartodo() {
        log.info("Solicitando lista de todos los Perfiles");
        return ResponseEntity.ok(perfilService.listarTodo());
    }

    @Operation(
            summary="Obtener perfil por ID",
            description="Obtiene los detalles de un perfil específico utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil obtenido exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PerfilDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando Perfil con ID: {}", id);
        return ResponseEntity.ok(perfilService.buscarPorId(id));
    }

    @Operation(
            summary="Crear nuevo perfil",
            description="Crea un nuevo perfil con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Perfil creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",schema = @Schema(implementation = PerfilDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<PerfilDTO> crear(@Valid @RequestBody PerfilDTO dto) {
        log.info("Recibida solicitud para crear Perfil");
        return new ResponseEntity<>(perfilService.crear(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary="Actualizar perfil existente",
            description="Actualiza los datos de un perfil existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Perfil actualizado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PutMapping
    public ResponseEntity<?> actualizar(@Valid @RequestBody PerfilDTO pDTO) {
        log.info("Actualizando PERFIL: {}", pDTO.toString());
        perfilService.actualizar(pDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary="Eliminar perfil",
            description="Elimina un perfil existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Perfil eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Perfil ID: {}", id);
        perfilService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
