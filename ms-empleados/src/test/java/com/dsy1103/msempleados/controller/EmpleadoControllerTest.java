package com.dsy1103.msempleados.controller;

import com.dsy1103.msempleados.dto.request.EmpleadoRequestDTO;
import com.dsy1103.msempleados.dto.response.EmpleadoResponseDTO;
import com.dsy1103.msempleados.service.EmpleadoService;
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
class EmpleadoControllerTest {

    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @Test
    @DisplayName("GET listarTodos - debe retornar 200 con lista de empleados")
    void listarTodosDebeRetornar200() {
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Juan").primerApellido("Perez").build();
        Mockito.when(empleadoService.listarEmpleados()).thenReturn(List.of(dto));

        ResponseEntity<List<EmpleadoResponseDTO>> respuesta = empleadoController.listarTodos();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Juan", respuesta.getBody().get(0).getPrimerNombre());
    }

    @Test
    @DisplayName("GET obtenerEmpleadoPorId - debe retornar 200 cuando existe")
    void obtenerPorIdDebeRetornar200() {
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Maria").build();
        Mockito.when(empleadoService.obtenerEmpleadoPorID(1L)).thenReturn(dto);

        ResponseEntity<EmpleadoResponseDTO> respuesta = empleadoController.obtenerEmpleadoPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Maria", respuesta.getBody().getPrimerNombre());
    }

    @Test
    @DisplayName("GET obtenerEmpleadoPorId - debe propagar EntityNotFoundException")
    void obtenerPorIdDebePropagar404() {
        Mockito.when(empleadoService.obtenerEmpleadoPorID(999L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        assertThrows(EntityNotFoundException.class, () -> empleadoController.obtenerEmpleadoPorId(999L));
    }

    @Test
    @DisplayName("GET listarPorSucursalYAnio - debe retornar 200 con resultados")
    void listarPorSucursalYAnioDebeRetornar200() {
        EmpleadoResponseDTO dto = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Ana").sucursalId(1L).build();
        Mockito.when(empleadoService.listarPorSucursalYAnio(1L, 2024)).thenReturn(List.of(dto));

        ResponseEntity<List<EmpleadoResponseDTO>> respuesta =
                empleadoController.listarPorSucursalYAnio(1L, 2024);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    @DisplayName("POST guardarEmpleado - debe retornar 201 al crear")
    void guardarDebeRetornar201() {
        EmpleadoRequestDTO request = EmpleadoRequestDTO.builder()
                .primerNombre("Carlos").primerApellido("Lopez")
                .rut(12345678).dvRut("K").correoEmpleado("carlos@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(1L).build();
        EmpleadoResponseDTO response = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Carlos").build();
        Mockito.when(empleadoService.guardarEmpleado(request)).thenReturn(response);

        ResponseEntity<EmpleadoResponseDTO> respuesta = empleadoController.guardarEmpleado(request);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    @DisplayName("PUT actualizarEmpleado - debe retornar 200 al actualizar")
    void actualizarDebeRetornar200() {
        EmpleadoRequestDTO request = EmpleadoRequestDTO.builder()
                .primerNombre("Actualizado").primerApellido("Perez")
                .rut(87654321).dvRut("5").correoEmpleado("actualizado@test.com")
                .fechaIngreso(LocalDate.now()).activoEmpleado(true).sucursalId(1L).build();
        EmpleadoResponseDTO response = EmpleadoResponseDTO.builder()
                .id(1L).primerNombre("Actualizado").build();
        Mockito.when(empleadoService.actualizarEmpleado(1L, request)).thenReturn(response);

        ResponseEntity<EmpleadoResponseDTO> respuesta = empleadoController.actualizarEmpleado(1L, request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Actualizado", respuesta.getBody().getPrimerNombre());
    }

    @Test
    @DisplayName("DELETE eliminarEmpleado - debe retornar 204 al eliminar")
    void eliminarDebeRetornar204() {
        ResponseEntity<Void> respuesta = empleadoController.eliminarEmpleado(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        Mockito.verify(empleadoService).eliminarEmpleado(1L);
    }
}
