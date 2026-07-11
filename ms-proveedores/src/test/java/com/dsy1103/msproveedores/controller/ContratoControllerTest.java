package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ContratoRequestDTO;
import com.dsy1103.msproveedores.dto.ContratoResponseDTO;
import com.dsy1103.msproveedores.service.ContratoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContratoControllerTest {

    @Mock
    private ContratoService contratoService;

    @InjectMocks
    private ContratoController contratoController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de contratos")
    void listarTodosDebeRetornar200() {
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(1L).numero("CON-001").build();
        Mockito.when(contratoService.listarContratos()).thenReturn(List.of(dto));

        ResponseEntity<List<ContratoResponseDTO>> respuesta = contratoController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("CON-001", respuesta.getBody().get(0).getNumero());
    }

    @Test
    @DisplayName("GET obtenerContratoPorId - debe retornar 200 cuando existe")
    void obtenerPorIdDebeRetornar200() {
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(1L).numero("CON-001").build();
        Mockito.when(contratoService.obtenerContratoPorId(1L)).thenReturn(dto);

        ResponseEntity<ContratoResponseDTO> respuesta = contratoController.obtenerContratoPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("CON-001", respuesta.getBody().getNumero());
    }

    @Test
    @DisplayName("GET obtenerContratoPorId - debe propagar EntityNotFoundException")
    void obtenerPorIdDebePropagar404() {
        Mockito.when(contratoService.obtenerContratoPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> contratoController.obtenerContratoPorId(999L));
    }

    @Test
    @DisplayName("GET listarPorProveedor - debe retornar 200 con resultados")
    void listarPorProveedorDebeRetornar200() {
        ContratoResponseDTO dto = ContratoResponseDTO.builder()
                .id(1L).numero("CON-001").proveedorId(1L).build();
        Mockito.when(contratoService.listarContratosPorProveedor(1L)).thenReturn(List.of(dto));

        ResponseEntity<List<ContratoResponseDTO>> respuesta = contratoController.listarPorProveedor(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals(1L, respuesta.getBody().get(0).getProveedorId());
    }

    @Test
    @DisplayName("POST guardarContrato - debe retornar 201 al crear")
    void guardarDebeRetornar201() {
        ContratoRequestDTO request = ContratoRequestDTO.builder()
                .numero("CON-NEW-001").tipo("Suministros").valor(50.0)
                .fechaInicio(LocalDate.now()).fechaFin(LocalDate.now().plusMonths(6))
                .vigente(true).observaciones("Test contrato").proveedorId(1L).build();
        ContratoResponseDTO response = ContratoResponseDTO.builder()
                .id(1L).numero("CON-NEW-001").build();
        Mockito.when(contratoService.guardarContrato(request)).thenReturn(response);

        ResponseEntity<ContratoResponseDTO> respuesta = contratoController.guardarContrato(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarContrato - debe retornar 200 al actualizar")
    void actualizarDebeRetornar200() {
        ContratoRequestDTO request = ContratoRequestDTO.builder()
                .numero("CON-UPD-001").tipo("Servicios").valor(75.0)
                .fechaInicio(LocalDate.now()).fechaFin(LocalDate.now().plusMonths(12))
                .vigente(true).observaciones("Actualizado").proveedorId(1L).build();
        ContratoResponseDTO response = ContratoResponseDTO.builder()
                .id(1L).numero("CON-UPD-001").build();
        Mockito.when(contratoService.actualizarContrato(1L, request)).thenReturn(response);

        ResponseEntity<ContratoResponseDTO> respuesta = contratoController.actualizarContrato(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("CON-UPD-001", respuesta.getBody().getNumero());
    }

    @Test
    @DisplayName("DELETE eliminarContrato - debe retornar 204 al eliminar")
    void eliminarDebeRetornar204() {
        ResponseEntity<Void> respuesta = contratoController.eliminarContrato(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(contratoService).eliminarContrato(1L);
    }
}
