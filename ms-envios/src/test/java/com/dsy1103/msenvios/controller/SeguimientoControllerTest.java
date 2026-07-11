package com.dsy1103.msenvios.controller;

import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.service.SeguimientoService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeguimientoControllerTest {

    @Mock
    private SeguimientoService seguimientoService;

    @InjectMocks
    private SeguimientoController seguimientoController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de seguimientos")
    void listarTodosDebeRetornar200() {
        SeguimientoResponseDTO dto = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Bodega").build();
        Mockito.when(seguimientoService.listarTodos()).thenReturn(List.of(dto));

        ResponseEntity<List<SeguimientoResponseDTO>> respuesta = seguimientoController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("En Bodega", respuesta.getBody().get(0).getEstadoSegui());
    }

    @Test
    @DisplayName("GET buscarPorId - debe retornar 200 cuando existe")
    void buscarPorIdDebeRetornar200() {
        SeguimientoResponseDTO dto = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Bodega").build();
        Mockito.when(seguimientoService.buscarPorId(1L)).thenReturn(dto);

        ResponseEntity<SeguimientoResponseDTO> respuesta = seguimientoController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("En Bodega", respuesta.getBody().getEstadoSegui());
    }

    @Test
    @DisplayName("GET buscarPorId - debe propagar EntityNotFoundException")
    void buscarPorIdDebePropagar404() {
        Mockito.when(seguimientoService.buscarPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> seguimientoController.buscarPorId(999L));
    }

    @Test
    @DisplayName("POST crear - debe retornar 201 al crear")
    void crearDebeRetornar201() {
        SeguimientoRequestDTO request = SeguimientoRequestDTO.builder()
                .envioId(1L).estadoSegui("En Bodega")
                .ubiAtual("Santiago").observacion("Sin novedades")
                .fechaSegui(LocalDateTime.now()).visible(true).build();
        SeguimientoResponseDTO response = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Bodega").build();
        Mockito.when(seguimientoService.crear(request)).thenReturn(response);

        ResponseEntity<SeguimientoResponseDTO> respuesta = seguimientoController.crear(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizar - debe retornar 200 al actualizar")
    void actualizarDebeRetornar200() {
        SeguimientoRequestDTO request = SeguimientoRequestDTO.builder()
                .envioId(1L).estadoSegui("En Reparto")
                .ubiAtual("Providencia").observacion("En camino")
                .fechaSegui(LocalDateTime.now()).visible(true).build();
        SeguimientoResponseDTO response = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Reparto").build();
        Mockito.when(seguimientoService.actualizar(1L, request)).thenReturn(response);

        ResponseEntity<SeguimientoResponseDTO> respuesta = seguimientoController.actualizar(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("En Reparto", respuesta.getBody().getEstadoSegui());
    }

    @Test
    @DisplayName("DELETE eliminar - debe retornar 204 al eliminar")
    void eliminarDebeRetornar204() {
        ResponseEntity<Void> respuesta = seguimientoController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(seguimientoService).eliminar(1L);
    }
}
