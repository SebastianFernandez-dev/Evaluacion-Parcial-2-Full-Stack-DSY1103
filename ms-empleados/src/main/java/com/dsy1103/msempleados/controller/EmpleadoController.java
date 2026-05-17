package com.dsy1103.msempleados.controller;


import com.dsy1103.msempleados.dto.EmpleadoDTO;
import com.dsy1103.msempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<EmpleadoDTO> guardarEmpleado(
            @Valid @RequestBody EmpleadoDTO eDTO,
            UriComponentsBuilder ucb) {
        log.info("REST: Creando nuevo EMPLEADO: {}", eDTO.toString());
        EmpleadoDTO creado = empleadoService.guardarEmpleado(eDTO);
        URI locacionDeEmpleado = ucb.path("/api/v1/empleados/{id}").buildAndExpand(creado.getId()).toUri();
        return ResponseEntity.created(locacionDeEmpleado).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarEmpleado(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoDTO eDTO) {
        log.info("REST: Actualizando EMPLEADO: {}", id);
        empleadoService.actualizarEmpleado(id, eDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@Valid @PathVariable Long id) {
        log.warn("REST: Eliminando EMPLEADO ID: {}", id);
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
