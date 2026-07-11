package com.dsy1103.msreportes.controller;

import com.dsy1103.msreportes.dto.ReporteRequestDTO;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import com.dsy1103.msreportes.dto.ReporteUsuarioDTO;
import com.dsy1103.msreportes.service.ReporteService;
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
class ReporteControllerTest {

    @Mock
    private ReporteService reporteService;

    @InjectMocks
    private ReporteController reporteController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de reportes")
    void listarTodosDebeRetornar200() {
        ReporteResponseDTO dto = ReporteResponseDTO.builder()
                .id(1L).descripcion("Reporte Q1").tipo("Ventas").build();
        Mockito.when(reporteService.listarReportes()).thenReturn(List.of(dto));

        ResponseEntity<List<ReporteResponseDTO>> respuesta = reporteController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Reporte Q1", respuesta.getBody().get(0).getDescripcion());
    }

    @Test
    @DisplayName("GET obtenerReportePorId - debe retornar 200 cuando existe")
    void obtenerReportePorIdDebeRetornar200() {
        ReporteUsuarioDTO dto = ReporteUsuarioDTO.builder()
                .id(1L).descripcion("Reporte Q1").build();
        Mockito.when(reporteService.obtenerReportePorId(1L)).thenReturn(dto);

        ResponseEntity<ReporteUsuarioDTO> respuesta = reporteController.obtenerReportePorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Reporte Q1", respuesta.getBody().getDescripcion());
    }

    @Test
    @DisplayName("GET obtenerReportePorId - debe propagar EntityNotFoundException")
    void obtenerReportePorIdDebePropagar404() {
        Mockito.when(reporteService.obtenerReportePorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> reporteController.obtenerReportePorId(999L));
    }

    @Test
    @DisplayName("GET listarPorUsuario - debe retornar 200 con lista")
    void listarPorUsuarioDebeRetornar200() {
        ReporteResponseDTO dto = ReporteResponseDTO.builder()
                .id(1L).usuarioId(10L).build();
        Mockito.when(reporteService.listarReportePorUsuario(10L)).thenReturn(List.of(dto));

        ResponseEntity<List<ReporteResponseDTO>> respuesta = reporteController.listarPorUsuario(10L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    @DisplayName("POST guardarReporte - debe retornar 201 al crear")
    void guardarReporteDebeRetornar201() {
        ReporteRequestDTO request = ReporteRequestDTO.builder()
                .descripcion("Nuevo Reporte").tipo("Ventas")
                .totalVentas(1000.0).cantidadPedidos(10).cantidadPagos(8)
                .publicado(true).usuarioId(1L).build();
        ReporteResponseDTO response = ReporteResponseDTO.builder()
                .id(1L).descripcion("Nuevo Reporte").build();
        Mockito.when(reporteService.guardarReporte(request)).thenReturn(response);

        ResponseEntity<ReporteResponseDTO> respuesta = reporteController.guardarReporte(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarReporte - debe retornar 200 al actualizar")
    void actualizarReporteDebeRetornar200() {
        ReporteRequestDTO request = ReporteRequestDTO.builder()
                .descripcion("Actualizado").tipo("Pagos")
                .totalVentas(500.0).cantidadPedidos(5).cantidadPagos(4)
                .publicado(false).usuarioId(1L).build();
        ReporteResponseDTO response = ReporteResponseDTO.builder()
                .id(1L).descripcion("Actualizado").build();
        Mockito.when(reporteService.actualizarReporte(1L, request)).thenReturn(response);

        ResponseEntity<ReporteResponseDTO> respuesta = reporteController.actualizarReporte(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Actualizado", respuesta.getBody().getDescripcion());
    }

    @Test
    @DisplayName("DELETE eliminarReporte - debe retornar 204 al eliminar")
    void eliminarReporteDebeRetornar204() {
        ResponseEntity<Void> respuesta = reporteController.eliminarReporte(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(reporteService).eliminarReporte(1L);
    }
}
