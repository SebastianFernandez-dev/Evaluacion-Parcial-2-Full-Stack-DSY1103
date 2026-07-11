package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.service.EnvioService;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnvioControllerTest {

    @Mock
    private EnvioService envioService;

    @InjectMocks
    private EnvioController envioController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de envios")
    void listarTodosDebeRetornar200() {
        EnvioResponseDTO dto = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-001").build();
        Mockito.when(envioService.listarTodos()).thenReturn(List.of(dto));

        ResponseEntity<List<EnvioResponseDTO>> respuesta = envioController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("ENV-001", respuesta.getBody().get(0).getCodigoEnvio());
    }

    @Test
    @DisplayName("GET buscarPorId - debe retornar 200 cuando existe")
    void buscarPorIdDebeRetornar200() {
        EnvioResponseDTO dto = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-001").build();
        Mockito.when(envioService.buscarPorId(1L)).thenReturn(dto);

        ResponseEntity<EnvioResponseDTO> respuesta = envioController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("ENV-001", respuesta.getBody().getCodigoEnvio());
    }

    @Test
    @DisplayName("GET buscarPorId - debe propagar EntityNotFoundException")
    void buscarPorIdDebePropagar404() {
        Mockito.when(envioService.buscarPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> envioController.buscarPorId(999L));
    }

    @Test
    @DisplayName("POST crear - debe retornar 201 al crear")
    void crearDebeRetornar201() {
        EnvioRequestDTO request = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-NEW-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Calle Test 123").estadoEnvio("Pendiente")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();
        EnvioResponseDTO response = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-NEW-001").build();
        Mockito.when(envioService.crear(request)).thenReturn(response);

        ResponseEntity<EnvioResponseDTO> respuesta = envioController.crear(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizar - debe retornar 200 al actualizar")
    void actualizarDebeRetornar200() {
        EnvioRequestDTO request = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Calle Actualizada").estadoEnvio("En Transito")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();
        EnvioResponseDTO response = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-001").direccionDestino("Calle Actualizada").build();
        Mockito.when(envioService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<EnvioResponseDTO> respuesta = envioController.actualizar(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Calle Actualizada", respuesta.getBody().getDireccionDestino());
    }

    @Test
    @DisplayName("DELETE eliminar - debe retornar 204 al eliminar")
    void eliminarDebeRetornar204() {
        ResponseEntity<Void> respuesta = envioController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(envioService).eliminar(1L);
    }

    @Test
    @DisplayName("GET listarNoEntregados - debe retornar 200 con resultados")
    void listarNoEntregadosDebeRetornar200() {
        String inicio = "2025-01-01T00:00:00";
        String fin = "2025-12-31T23:59:59";

        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").build();

        Mockito.when(envioService.obtenerEnviosEnRangoNoEntregados(
                LocalDateTime.parse(inicio), LocalDateTime.parse(fin)))
                .thenReturn(List.of(envio));

        ResponseEntity<List<EnvioModelo>> respuesta = envioController.listarNoEntregados(inicio, fin);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("ENV-001", respuesta.getBody().get(0).getCodigoEnvio());
    }
}
