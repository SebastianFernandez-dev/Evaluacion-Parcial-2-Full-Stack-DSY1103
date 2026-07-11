package com.dsy1103.msempleados.controller;


import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.service.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@Slf4j
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    @Operation(summary = "Listar todos los empleados", description = "Retorna una lista de todos los empleados registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empleados obtenida exitosamente")
    })
    public ResponseEntity<List<EmpleadoResponseDTO>> listarTodos() {
        log.info("REST: solicitud para listar todos los EMPLEADOS");
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado por ID", description = "Retorna un empleado según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> obtenerEmpleadoPorId(@PathVariable Long id) {
        log.info("REST: Buscando EMPLEADO por ID: {}", id);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorID(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar empleados por sucursal y año", description = "Retorna empleados filtrados por sucursal y año de ingreso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    })
    public ResponseEntity<List<EmpleadoResponseDTO>> listarPorSucursalYAnio(
            @RequestParam Long sucursalId,
            @RequestParam int anio) {
        log.info("REST: Listando EMPLEADOS por sucursalId {} y año {}", sucursalId, anio);
        return ResponseEntity.ok(empleadoService.listarPorSucursalYAnio(sucursalId, anio));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo empleado", description = "Registra un nuevo empleado en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<EmpleadoResponseDTO> guardarEmpleado(
            @Valid @RequestBody EmpleadoRequestDTO eDTO) {
        log.info("REST: Creando nuevo EMPLEADO");
        EmpleadoResponseDTO creado = empleadoService.guardarEmpleado(eDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado", description = "Actualiza los datos de un empleado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<EmpleadoResponseDTO> actualizarEmpleado(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequestDTO eDTO) {
        log.info("REST: Actualizando EMPLEADO: {}", id);
        EmpleadoResponseDTO actualizado = empleadoService.actualizarEmpleado(id, eDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado", description = "Elimina un empleado del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empleado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Empleado no encontrado")
    })
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        log.warn("REST: Eliminando EMPLEADO ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
