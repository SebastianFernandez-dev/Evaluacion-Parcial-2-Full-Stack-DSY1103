package com.dsy1103.msempleados.controller;


import com.dsy1103.msempleados.dto.EmpleadoDTO;
import com.dsy1103.msempleados.service.EmpleadoService;
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
    public ResponseEntity<List<EmpleadoDTO>> listarTodos() {
        log.info("REST: solicitud para listar todos los EMPLEADOS");
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id) {
        log.info("REST: Buscando EMPLEADO por ID: {}", id);
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorID(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpleadoDTO>> listarPorSucursalYAnio(
            @RequestParam Long sucursalId,
            @RequestParam int anio) {
        log.info("REST: Listando EMPLEADOS por sucursalId {} y año {}", sucursalId, anio);
        return ResponseEntity.ok(empleadoService.listarPorSucursalYAnio(sucursalId, anio));
    }

    @PostMapping
    public ResponseEntity<EmpleadoDTO> guardarEmpleado(
            @Valid @RequestBody EmpleadoDTO eDTO) {
        log.info("REST: Creando nuevo EMPLEADO: {}", eDTO.toString());
        EmpleadoDTO creado = empleadoService.guardarEmpleado(eDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping
    public ResponseEntity<Void> actualizarEmpleado(@Valid @RequestBody EmpleadoDTO eDTO) {
        log.info("REST: Actualizando EMPLEADO: {}", eDTO.getId());
        empleadoService.actualizarEmpleado(eDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@Valid @PathVariable Long id) {
        log.warn("REST: Eliminando EMPLEADO ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
