package com.dsy1103.msproveedores.controller;

import com.dsy1103.msproveedores.dto.ProveedorRequestDTO;
import com.dsy1103.msproveedores.dto.ProveedorResponseDTO;
import com.dsy1103.msproveedores.service.ProveedorService;
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
class ProveedorControllerTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorController proveedorController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de proveedores")
    void listarTodosDebeRetornar200() {
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L).nombre("Proveedor Test").build();
        Mockito.when(proveedorService.listarProveedores()).thenReturn(List.of(dto));

        ResponseEntity<List<ProveedorResponseDTO>> respuesta = proveedorController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Proveedor Test", respuesta.getBody().get(0).getNombre());
    }

    @Test
    @DisplayName("GET obtenerProveedorPorId - debe retornar 200 cuando existe")
    void obtenerPorIdDebeRetornar200() {
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L).nombre("Proveedor Test").build();
        Mockito.when(proveedorService.obtenerProveedorPorId(1L)).thenReturn(dto);

        ResponseEntity<ProveedorResponseDTO> respuesta = proveedorController.obtenerProveedorPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Proveedor Test", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("GET obtenerProveedorPorId - debe propagar EntityNotFoundException")
    void obtenerPorIdDebePropagar404() {
        Mockito.when(proveedorService.obtenerProveedorPorId(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> proveedorController.obtenerProveedorPorId(999L));
    }

    @Test
    @DisplayName("GET listarActivos - debe retornar 200 con lista de proveedores activos")
    void listarActivosDebeRetornar200() {
        ProveedorResponseDTO dto = ProveedorResponseDTO.builder()
                .id(1L).nombre("Activo").activo(true).build();
        Mockito.when(proveedorService.listarProveedoresActivos()).thenReturn(List.of(dto));

        ResponseEntity<List<ProveedorResponseDTO>> respuesta = proveedorController.listarActivos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertTrue(respuesta.getBody().get(0).getActivo());
    }

    @Test
    @DisplayName("POST guardarProveedor - debe retornar 201 al crear")
    void guardarDebeRetornar201() {
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .nombre("Nuevo Proveedor").razonSocial("Razon Social S.A.")
                .documentoFiscal("76.123.456-7").correoContacto("correo@test.cl")
                .ciudad("Santiago").calificacion(4).activo(true)
                .fechaRegistro(LocalDate.now()).build();
        ProveedorResponseDTO response = ProveedorResponseDTO.builder()
                .id(1L).nombre("Nuevo Proveedor").build();
        Mockito.when(proveedorService.guardarProveedor(request)).thenReturn(response);

        ResponseEntity<ProveedorResponseDTO> respuesta = proveedorController.guardarProveedor(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarProveedor - debe retornar 200 al actualizar")
    void actualizarDebeRetornar200() {
        ProveedorRequestDTO request = ProveedorRequestDTO.builder()
                .nombre("Actualizado").razonSocial("Razon Actualizada S.A.")
                .documentoFiscal("76.123.456-7").correoContacto("correo@test.cl")
                .ciudad("Santiago").calificacion(5).activo(true)
                .fechaRegistro(LocalDate.now()).build();
        ProveedorResponseDTO response = ProveedorResponseDTO.builder()
                .id(1L).nombre("Actualizado").build();
        Mockito.when(proveedorService.actualizarProveedor(1L, request)).thenReturn(response);

        ResponseEntity<ProveedorResponseDTO> respuesta = proveedorController.actualizarProveedor(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Actualizado", respuesta.getBody().getNombre());
    }

    @Test
    @DisplayName("DELETE eliminarProveedor - debe retornar 204 al eliminar")
    void eliminarDebeRetornar204() {
        ResponseEntity<Void> respuesta = proveedorController.eliminarProveedor(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(proveedorService).eliminarProveedor(1L);
    }
}
