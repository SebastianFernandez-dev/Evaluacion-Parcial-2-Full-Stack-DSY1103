package com.dsy1103.mssucursales.repository;

import com.dsy1103.mssucursales.model.SucursalModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SucursalRepositoryTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @Test
    @DisplayName("findById debe retornar sucursal cuando existe")
    void findByIdDebeRetornarSucursal() {
        SucursalModel sucursal = SucursalModel.builder()
                .id(1L).nombre("Sucursal Santiago").build();
        Mockito.when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

        Optional<SucursalModel> resultado = sucursalRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Sucursal Santiago", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<SucursalModel> resultado = sucursalRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todas las sucursales")
    void findAllDebeRetornarTodas() {
        SucursalModel s1 = SucursalModel.builder().id(1L).nombre("A").build();
        SucursalModel s2 = SucursalModel.builder().id(2L).nombre("B").build();
        Mockito.when(sucursalRepository.findAll()).thenReturn(List.of(s1, s2));

        List<SucursalModel> resultados = sucursalRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(sucursalRepository.existsById(1L)).thenReturn(true);

        assertTrue(sucursalRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(sucursalRepository.existsById(999L)).thenReturn(false);

        assertFalse(sucursalRepository.existsById(999L));
    }

    @Test
    @DisplayName("findAllByRegionNombre debe retornar sucursales de la region")
    void findAllByRegionNombreDebeRetornarSucursales() {
        SucursalModel s1 = SucursalModel.builder().id(1L).nombre("Sucursal Santiago").build();
        Mockito.when(sucursalRepository.findAllByRegionNombre("Metropolitana")).thenReturn(List.of(s1));

        List<SucursalModel> resultados = sucursalRepository.findAllByRegionNombre("Metropolitana");

        assertEquals(1, resultados.size());
        assertEquals("Sucursal Santiago", resultados.get(0).getNombre());
    }
}
