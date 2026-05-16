package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.EnvioDTO;
import com.dsy1103.msenvios.service.EnvioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    // Listar
    @GetMapping
    public ResponseEntity<List<EnvioDTO>> listarTodos() {
        log.info("Recibida solicitud para listar todos los envíos");
        return ResponseEntity.ok(envioService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnvioDTO> buscarPorId(@PathVariable Long id) {
        log.info("Recibida solicitud para crear un envio");
        return ResponseEntity.ok(envioService.buscarPorId(id));
    }

    // Crear: POST
    @PostMapping
    public ResponseEntity<EnvioDTO> crear(@Valid @RequestBody EnvioDTO dto) {
        log.info("Recibida solicitud para crear un nuevo Envio");
        return new ResponseEntity<>(envioService.crear(dto), HttpStatus.CREATED);
    }

    // Actualizar: PUT
    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EnvioDTO dto) {
        log.info("Recibida solicitud para actualizar Envio con ID: {}", id);
        return ResponseEntity.ok(envioService.actualizar(id, dto));
    }

    // ELIMINAR: DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        log.info("Recibida solicitud para eliminar Envio con ID: {}", id);
        envioService.eliminar(id);
        return ResponseEntity.ok("Envío eliminado correctamente con el ID: " + id);
    }

}
