package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import com.dsy1103.mssucursales.service.RegionService;
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
class RegionControllerTest {

    @Mock
    private RegionService regionService;

    @InjectMocks
    private RegionController regionController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de regiones")
    void listarTodosDebeRetornar200() {
        RegionResponseDTO dto = RegionResponseDTO.builder()
                .id(1L).nombre("Metropolitana").build();
        Mockito.when(regionService.listarRegiones()).thenReturn(List.of(dto));

        ResponseEntity<List<RegionResponseDTO>> respuesta = regionController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Metropolitana", respuesta.getBody().get(0).getNombre());
    }

    @Test
    @DisplayName("GET obtenerRegionPorId - debe retornar 200 cuando existe")
    void obtenerRegionPorIdDebeRetornar200() {
        RegionResponseDTO dto = RegionResponseDTO.builder()
                .id(1L).nombre("Biobio").build();
        Mockito.when(regionService.obtenerRegionPorId(1L)).thenReturn(dto);

        ResponseEntity<RegionResponseDTO> respuesta = regionController.obtenerRegionPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Biobio", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("GET obtenerRegionPorId - debe propagar EntityNotFoundException")
    void obtenerRegionPorIdDebePropagar404() {
        Mockito.when(regionService.obtenerRegionPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrada"));

        assertThrows(EntityNotFoundException.class, () -> regionController.obtenerRegionPorId(999L));
    }

    @Test
    @DisplayName("POST guardarRegion - debe retornar 201 al crear")
    void guardarRegionDebeRetornar201() {
        RegionRequestDTO request = RegionRequestDTO.builder()
                .nombre("Nueva Region").descripcion("Descripcion de prueba")
                .codigo("REG-01").pais("Chile").fechaCreacion(LocalDate.now()).build();
        RegionResponseDTO response = RegionResponseDTO.builder()
                .id(1L).nombre("Nueva Region").build();
        Mockito.when(regionService.guardarRegion(request)).thenReturn(response);

        ResponseEntity<RegionResponseDTO> respuesta = regionController.guardarRegion(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarRegion - debe retornar 200 al actualizar")
    void actualizarRegionDebeRetornar200() {
        RegionRequestDTO request = RegionRequestDTO.builder()
                .nombre("Actualizada").descripcion("Desc actualizada")
                .codigo("UPD-01").pais("Chile").fechaCreacion(LocalDate.now()).build();
        RegionResponseDTO response = RegionResponseDTO.builder()
                .id(1L).nombre("Actualizada").build();
        Mockito.when(regionService.actualizarRegion(1L, request)).thenReturn(response);

        ResponseEntity<RegionResponseDTO> respuesta = regionController.actualizarRegion(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Actualizada", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("DELETE eliminarRegion - debe retornar 204 al eliminar")
    void eliminarRegionDebeRetornar204() {
        ResponseEntity<Void> respuesta = regionController.eliminarRegion(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(regionService).eliminarRegion(1L);
    }
}
