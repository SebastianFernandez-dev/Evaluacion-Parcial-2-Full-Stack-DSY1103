package com.dsy1103.msempleados.controller;

import com.dsy1103.msempleados.assemblers.EmpleadoModelAssembler;
import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.service.EmpleadoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v2/empleados")
@Slf4j
public class EmpleadoControllerV2 {

    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private EmpleadoModelAssembler empleadoModelAssembler;

    @GetMapping(produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los empleados (HATEOAS)", description = "Retorna una lista de todos los empleados en formato HAL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente")
    })
    public CollectionModel<EntityModel<EmpleadoResponseDTO>> getAllEmpleados() {
        List<EntityModel<EmpleadoResponseDTO>> empleados = empleadoService.listarEmpleados().stream()
                .map(empleadoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(empleados, linkTo(methodOn(EmpleadoControllerV2.class).getAllEmpleados()).withSelfRel());
    }

    @GetMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener empleado por ID (HATEOAS)", description = "Retorna un empleado según su ID en formato HAL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public EntityModel<EmpleadoResponseDTO> getEmpleadoById(@PathVariable Long id) {
        EmpleadoResponseDTO empleadoDTO = empleadoService.obtenerEmpleadoPorID(id);
        return empleadoModelAssembler.toModel(empleadoDTO);
    }

    @PostMapping(produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo empleado (HATEOAS)", description = "Registra un nuevo empleado en formato HAL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public EntityModel<EmpleadoResponseDTO> createEmpleado(@Valid @RequestBody EmpleadoRequestDTO dto) {
        EmpleadoResponseDTO nuevoEmpleado = empleadoService.guardarEmpleado(dto);
        return empleadoModelAssembler.toModel(nuevoEmpleado);
    }

    @PutMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un empleado (HATEOAS)", description = "Actualiza los datos de un empleado existente en formato HAL")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EntityModel<EmpleadoResponseDTO>> updateEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoRequestDTO dto) {
        EmpleadoResponseDTO actualizado = empleadoService.actualizarEmpleado(id, dto);
        return ResponseEntity.ok(empleadoModelAssembler.toModel(actualizado));
    }

    @DeleteMapping(value="/{id}", produces=MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un empleado (HATEOAS)", description = "Elimina un empleado del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
