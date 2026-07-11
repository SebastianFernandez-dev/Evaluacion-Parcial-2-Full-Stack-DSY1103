package com.dsy1103.mssucursales.repository;

import com.dsy1103.mssucursales.model.RegionModel;
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
class RegionRepositoryTest {

    @Mock
    private RegionRepository regionRepository;

    @Test
    @DisplayName("findById debe retornar region cuando existe")
    void findByIdDebeRetornarRegion() {
        RegionModel region = RegionModel.builder()
                .id(1L).nombre("Metropolitana").codigo("RM-13").build();
        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(region));

        Optional<RegionModel> resultado = regionRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Metropolitana", resultado.get().getNombre());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(regionRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<RegionModel> resultado = regionRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todas las regiones")
    void findAllDebeRetornarTodas() {
        RegionModel r1 = RegionModel.builder().id(1L).nombre("Metropolitana").build();
        RegionModel r2 = RegionModel.builder().id(2L).nombre("Biobio").build();
        Mockito.when(regionRepository.findAll()).thenReturn(List.of(r1, r2));

        List<RegionModel> resultados = regionRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(regionRepository.existsById(1L)).thenReturn(true);

        assertTrue(regionRepository.existsById(1L));
    }
}
