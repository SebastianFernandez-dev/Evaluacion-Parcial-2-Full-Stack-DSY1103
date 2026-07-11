package com.dsy1103.mssucursales.controller;

import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.service.SucursalService;
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
class SucursalControllerTest {

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de sucursales")
    void listarTodosDebeRetornar200() {
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(1L).nombre("Sucursal Santiago Centro").build();
        Mockito.when(sucursalService.listarSucursales()).thenReturn(List.of(dto));

        ResponseEntity<List<SucursalResponseDTO>> respuesta = sucursalController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Sucursal Santiago Centro", respuesta.getBody().get(0).getNombre());
    }

    @Test
    @DisplayName("GET obtenerSucursalPorId - debe retornar 200 cuando existe")
    void obtenerSucursalPorIdDebeRetornar200() {
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(1L).nombre("Sucursal Concepcion").build();
        Mockito.when(sucursalService.obtenerSucursalPorId(1L)).thenReturn(dto);

        ResponseEntity<SucursalResponseDTO> respuesta = sucursalController.obtenerSucursalPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Sucursal Concepcion", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("GET obtenerSucursalPorId - debe propagar EntityNotFoundException")
    void obtenerSucursalPorIdDebePropagar404() {
        Mockito.when(sucursalService.obtenerSucursalPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrada"));

        assertThrows(EntityNotFoundException.class, () -> sucursalController.obtenerSucursalPorId(999L));
    }

    @Test
    @DisplayName("GET listarPorNombreRegion - debe retornar 200 con lista")
    void listarPorNombreRegionDebeRetornar200() {
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(1L).nombre("Sucursal Santiago Centro").build();
        Mockito.when(sucursalService.listarSucursalesPorRegion("Metropolitana")).thenReturn(List.of(dto));

        ResponseEntity<List<SucursalResponseDTO>> respuesta = sucursalController.listarPorNombreRegion("Metropolitana");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    @DisplayName("POST guardarSucursal - debe retornar 201 al crear")
    void guardarSucursalDebeRetornar201() {
        SucursalRequestDTO request = SucursalRequestDTO.builder()
                .nombre("Nueva Sucursal").codigo("SUC-NEW")
                .direccion("Direccion 123").capacidadAtencion(100)
                .activo(true).fechaApertura(LocalDate.now()).regionId(1L).build();
        SucursalResponseDTO response = SucursalResponseDTO.builder()
                .id(1L).nombre("Nueva Sucursal").build();
        Mockito.when(sucursalService.guardarSucursal(request)).thenReturn(response);

        ResponseEntity<SucursalResponseDTO> respuesta = sucursalController.guardarSucursal(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarSucursal - debe retornar 200 al actualizar")
    void actualizarSucursalDebeRetornar200() {
        SucursalRequestDTO request = SucursalRequestDTO.builder()
                .nombre("Actualizada").codigo("SUC-UPD")
                .direccion("Direccion 456").capacidadAtencion(80)
                .activo(true).fechaApertura(LocalDate.now()).regionId(1L).build();
        SucursalResponseDTO response = SucursalResponseDTO.builder()
                .id(1L).nombre("Actualizada").build();
        Mockito.when(sucursalService.actualizarSucursal(1L, request)).thenReturn(response);

        ResponseEntity<SucursalResponseDTO> respuesta = sucursalController.actualizarSucursal(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Actualizada", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("DELETE eliminarSucursal - debe retornar 204 al eliminar")
    void eliminarSucursalDebeRetornar204() {
        ResponseEntity<Void> respuesta = sucursalController.eliminarSucursal(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(sucursalService).eliminarSucursal(1L);
    }
}
