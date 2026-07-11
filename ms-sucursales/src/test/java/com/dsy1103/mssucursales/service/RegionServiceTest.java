package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.RegionRequestDTO;
import com.dsy1103.mssucursales.dto.RegionResponseDTO;
import com.dsy1103.mssucursales.mapper.RegionMapper;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;
    @Mock
    private RegionMapper regionMapper;
    @InjectMocks
    private RegionService regionService;

    @Test
    @DisplayName("Debe listar todas las regiones exitosamente")
    void debeListarRegionesExitosamente() {
        RegionModel region = RegionModel.builder()
                .id(1L).nombre("Metropolitana").codigo("RM-13").build();
        RegionResponseDTO dto = RegionResponseDTO.builder()
                .id(1L).nombre("Metropolitana").codigo("RM-13").build();

        Mockito.when(regionRepository.findAll()).thenReturn(List.of(region));
        Mockito.when(regionMapper.toResponseDTO(region)).thenReturn(dto);

        List<RegionResponseDTO> resultado = regionService.listarRegiones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Metropolitana", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe obtener region por ID exitosamente")
    void debeObtenerRegionPorIdExitosamente() {
        Long regionId = 1L;
        RegionModel region = RegionModel.builder()
                .id(regionId).nombre("Biobio").build();
        RegionResponseDTO dto = RegionResponseDTO.builder()
                .id(regionId).nombre("Biobio").build();

        Mockito.when(regionRepository.findById(regionId)).thenReturn(Optional.of(region));
        Mockito.when(regionMapper.toResponseDTO(region)).thenReturn(dto);

        RegionResponseDTO resultado = regionService.obtenerRegionPorId(regionId);

        assertNotNull(resultado);
        assertEquals("Biobio", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando la region no existe al buscar")
    void debeLanzarExcepcionCuandoRegionNoExiste() {
        Mockito.when(regionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            regionService.obtenerRegionPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe guardar una region exitosamente")
    void debeGuardarRegionExitosamente() {
        RegionRequestDTO dtoEntrada = RegionRequestDTO.builder()
                .nombre("Valparaiso").codigo("REG-05").descripcion("Region costera")
                .pais("Chile").fechaCreacion(LocalDate.now()).build();

        RegionModel modelParaGuardar = RegionModel.builder()
                .nombre("Valparaiso").codigo("REG-05").build();

        RegionModel modelGuardado = RegionModel.builder()
                .id(1L).nombre("Valparaiso").codigo("REG-05").build();

        RegionResponseDTO dtoSalida = RegionResponseDTO.builder()
                .id(1L).nombre("Valparaiso").codigo("REG-05").build();

        Mockito.when(regionMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(regionRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(regionMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        RegionResponseDTO resultado = regionService.guardarRegion(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Valparaiso", resultado.getNombre());
        Mockito.verify(regionRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar una region exitosamente")
    void debeActualizarRegionExitosamente() {
        Long regionId = 1L;
        RegionModel existente = RegionModel.builder()
                .id(regionId).nombre("Viejo Nombre").codigo("OLD-01").build();

        RegionRequestDTO dtoActualizacion = RegionRequestDTO.builder()
                .nombre("Nuevo Nombre").codigo("NEW-01").descripcion("Nueva desc")
                .pais("Chile").fechaCreacion(LocalDate.now()).build();

        RegionModel actualizado = RegionModel.builder()
                .id(regionId).nombre("Nuevo Nombre").codigo("NEW-01").build();

        RegionResponseDTO dtoSalida = RegionResponseDTO.builder()
                .id(regionId).nombre("Nuevo Nombre").codigo("NEW-01").build();

        Mockito.when(regionRepository.findById(regionId)).thenReturn(Optional.of(existente));
        Mockito.when(regionRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(regionMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        RegionResponseDTO resultado = regionService.actualizarRegion(regionId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar una region inexistente")
    void debeLanzarExcepcionAlActualizarRegionInexistente() {
        RegionRequestDTO dtoActualizacion = RegionRequestDTO.builder().build();
        Mockito.when(regionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            regionService.actualizarRegion(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar una region inexistente")
    void debeLanzarExcepcionAlEliminarRegionInexistente() {
        Mockito.when(regionRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            regionService.eliminarRegion(1L);
        });

        Mockito.verify(regionRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar una region exitosamente si existe")
    void debeEliminarRegionExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(regionRepository.existsById(idEliminar)).thenReturn(true);

        regionService.eliminarRegion(idEliminar);

        Mockito.verify(regionRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
