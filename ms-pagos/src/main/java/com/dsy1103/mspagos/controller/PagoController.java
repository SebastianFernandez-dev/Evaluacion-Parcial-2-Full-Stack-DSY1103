package com.dsy1103.mspagos.controller;

import com.dsy1103.mspagos.dto.PagoDTO;
import com.dsy1103.mspagos.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones CRUD para pagos")
@Slf4j
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Retorna una lista de todos los pagos registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente")
    })
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        log.info("REST: Solicitud para listar todos los PAGOS");
        return ResponseEntity.ok(pagoService.listarPagos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PagoDTO> obtenerPorId(@PathVariable Long id) {
        log.info("REST: Buscando PAGO por ID: {}", id);
        return ResponseEntity.ok(pagoService.obtenerPagoPorId(id));
    }

    @GetMapping("/filtrar")
    @Operation(summary = "Filtrar pagos por monto y estado",
               description = "Filtra pagos con monto mayor al indicado y estado de pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Filtro aplicado exitosamente")
    })
    public ResponseEntity<List<PagoDTO>> filtrarPorMontoYEstado(
            @RequestParam Double monto,
            @RequestParam String estadoPago) {
        log.info("REST: Filtrando PAGOS con monto > {} y estado '{}'", monto, estadoPago);
        return ResponseEntity.ok(pagoService.buscarPorMontoYEstado(monto, estadoPago));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Registra un nuevo pago en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<PagoDTO> guardar(@Valid @RequestBody PagoDTO dto) {
        log.info("REST: Creando nuevo PAGO: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardarPago(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago", description = "Actualiza los datos de un pago existente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pago actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        log.info("REST: Actualizando PAGO ID: {}", id);
        dto.setId(id);
        pagoService.actualizarPago(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.warn("REST: Eliminando PAGO ID: {}", id);
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
