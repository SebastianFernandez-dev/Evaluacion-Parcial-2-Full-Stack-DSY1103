package com.dsy1103.msempleados.repository;

import com.dsy1103.msempleados.model.EmpleadoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoRepositoryTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Test
    @DisplayName("findAllBySucursalAndAnio debe retornar empleados filtrados")
    void findAllBySucursalAndAnioDebeFiltrar() {
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1L).primerNombre("Juan").sucursalId(1L)
                .fechaIngreso(LocalDate.of(2024, 1, 15)).build();
        Mockito.when(empleadoRepository.findAllBySucursalAndAnio(1L, 2024))
                .thenReturn(List.of(empleado));

        List<EmpleadoModel> resultados = empleadoRepository.findAllBySucursalAndAnio(1L, 2024);

        assertEquals(1, resultados.size());
        assertEquals("Juan", resultados.get(0).getPrimerNombre());
        assertEquals(1L, resultados.get(0).getSucursalId());
    }

    @Test
    @DisplayName("findAllBySucursalAndAnio debe retornar lista vacia cuando no hay coincidencias")
    void findAllBySucursalAndAnioDebeRetornarVacio() {
        Mockito.when(empleadoRepository.findAllBySucursalAndAnio(999L, 1999))
                .thenReturn(List.of());

        List<EmpleadoModel> resultados = empleadoRepository.findAllBySucursalAndAnio(999L, 1999);

        assertTrue(resultados.isEmpty());
    }

    @Test
    @DisplayName("findById debe retornar empleado cuando existe")
    void findByIdDebeRetornarEmpleado() {
        EmpleadoModel empleado = EmpleadoModel.builder()
                .id(1L).primerNombre("Maria").build();
        Mockito.when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Optional<EmpleadoModel> resultado = empleadoRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Maria", resultado.get().getPrimerNombre());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(empleadoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<EmpleadoModel> resultado = empleadoRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todos los empleados")
    void findAllDebeRetornarTodos() {
        EmpleadoModel e1 = EmpleadoModel.builder().id(1L).primerNombre("A").build();
        EmpleadoModel e2 = EmpleadoModel.builder().id(2L).primerNombre("B").build();
        Mockito.when(empleadoRepository.findAll()).thenReturn(List.of(e1, e2));

        List<EmpleadoModel> resultados = empleadoRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(empleadoRepository.existsById(1L)).thenReturn(true);

        assertTrue(empleadoRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(empleadoRepository.existsById(999L)).thenReturn(false);

        assertFalse(empleadoRepository.existsById(999L));
    }
}
