package com.dsy1103.mssucursales.service;

import com.dsy1103.mssucursales.dto.SucursalRequestDTO;
import com.dsy1103.mssucursales.dto.SucursalResponseDTO;
import com.dsy1103.mssucursales.mapper.SucursalMapper;
import com.dsy1103.mssucursales.model.RegionModel;
import com.dsy1103.mssucursales.model.SucursalModel;
import com.dsy1103.mssucursales.repository.RegionRepository;
import com.dsy1103.mssucursales.repository.SucursalRepository;
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
class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;
    @Mock
    private RegionRepository regionRepository;
    @Mock
    private SucursalMapper sucursalMapper;
    @InjectMocks
    private SucursalService sucursalService;

    @Test
    @DisplayName("Debe listar todas las sucursales exitosamente")
    void debeListarSucursalesExitosamente() {
        SucursalModel sucursal = SucursalModel.builder()
                .id(1L).nombre("Sucursal Santiago").build();
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(1L).nombre("Sucursal Santiago").build();

        Mockito.when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        Mockito.when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(dto);

        List<SucursalResponseDTO> resultado = sucursalService.listarSucursales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Santiago", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debe obtener sucursal por ID exitosamente")
    void debeObtenerSucursalPorIdExitosamente() {
        Long sucursalId = 1L;
        SucursalModel sucursal = SucursalModel.builder()
                .id(sucursalId).nombre("Sucursal Concepcion").build();
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(sucursalId).nombre("Sucursal Concepcion").build();

        Mockito.when(sucursalRepository.findById(sucursalId)).thenReturn(Optional.of(sucursal));
        Mockito.when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(dto);

        SucursalResponseDTO resultado = sucursalService.obtenerSucursalPorId(sucursalId);

        assertNotNull(resultado);
        assertEquals("Sucursal Concepcion", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando la sucursal no existe al buscar")
    void debeLanzarExcepcionCuandoSucursalNoExiste() {
        Mockito.when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            sucursalService.obtenerSucursalPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe guardar una sucursal exitosamente")
    void debeGuardarSucursalExitosamente() {
        RegionModel region = RegionModel.builder().id(1L).nombre("Metropolitana").build();

        SucursalRequestDTO dtoEntrada = SucursalRequestDTO.builder()
                .nombre("Nueva Sucursal").codigo("SUC-NEW").direccion("Direccion 123")
                .capacidadAtencion(100).activo(true).fechaApertura(LocalDate.now()).regionId(1L).build();

        SucursalModel modelParaGuardar = SucursalModel.builder()
                .nombre("Nueva Sucursal").codigo("SUC-NEW").region(region).build();

        SucursalModel modelGuardado = SucursalModel.builder()
                .id(1L).nombre("Nueva Sucursal").codigo("SUC-NEW").region(region).build();

        SucursalResponseDTO dtoSalida = SucursalResponseDTO.builder()
                .id(1L).nombre("Nueva Sucursal").regionId(1L).build();

        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
        Mockito.when(sucursalMapper.toEntity(dtoEntrada, region)).thenReturn(modelParaGuardar);
        Mockito.when(sucursalRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(sucursalMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        SucursalResponseDTO resultado = sucursalService.guardarSucursal(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nueva Sucursal", resultado.getNombre());
        Mockito.verify(sucursalRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al guardar sucursal con region inexistente")
    void debeLanzarExcepcionAlGuardarSucursalConRegionInexistente() {
        SucursalRequestDTO dtoEntrada = SucursalRequestDTO.builder()
                .regionId(999L).build();

        Mockito.when(regionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            sucursalService.guardarSucursal(dtoEntrada);
        });
    }

    @Test
    @DisplayName("Debe actualizar una sucursal exitosamente")
    void debeActualizarSucursalExitosamente() {
        Long sucursalId = 1L;
        SucursalModel existente = SucursalModel.builder()
                .id(sucursalId).nombre("Viejo Nombre").build();

        SucursalRequestDTO dtoActualizacion = SucursalRequestDTO.builder()
                .nombre("Nuevo Nombre").codigo("SUC-UPD").direccion("Direccion 456")
                .capacidadAtencion(80).activo(true).fechaApertura(LocalDate.now()).regionId(1L).build();

        RegionModel region = RegionModel.builder().id(1L).nombre("Metropolitana").build();

        SucursalModel actualizado = SucursalModel.builder()
                .id(sucursalId).nombre("Nuevo Nombre").build();

        SucursalResponseDTO dtoSalida = SucursalResponseDTO.builder()
                .id(sucursalId).nombre("Nuevo Nombre").regionId(1L).build();

        Mockito.when(sucursalRepository.findById(sucursalId)).thenReturn(Optional.of(existente));
        Mockito.when(regionRepository.findById(1L)).thenReturn(Optional.of(region));
        Mockito.when(sucursalRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(sucursalMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        SucursalResponseDTO resultado = sucursalService.actualizarSucursal(sucursalId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar una sucursal inexistente")
    void debeLanzarExcepcionAlActualizarSucursalInexistente() {
        SucursalRequestDTO dtoActualizacion = SucursalRequestDTO.builder().build();
        Mockito.when(sucursalRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            sucursalService.actualizarSucursal(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar una sucursal inexistente")
    void debeLanzarExcepcionAlEliminarSucursalInexistente() {
        Mockito.when(sucursalRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            sucursalService.eliminarSucursal(1L);
        });

        Mockito.verify(sucursalRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar una sucursal exitosamente si existe")
    void debeEliminarSucursalExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(sucursalRepository.existsById(idEliminar)).thenReturn(true);

        sucursalService.eliminarSucursal(idEliminar);

        Mockito.verify(sucursalRepository, Mockito.times(1)).deleteById(idEliminar);
    }

    @Test
    @DisplayName("Debe listar sucursales por region exitosamente")
    void debeListarSucursalesPorRegionExitosamente() {
        SucursalModel sucursal = SucursalModel.builder()
                .id(1L).nombre("Sucursal Santiago").build();
        SucursalResponseDTO dto = SucursalResponseDTO.builder()
                .id(1L).nombre("Sucursal Santiago").regionId(1L).build();

        Mockito.when(sucursalRepository.findAllByRegionNombre("Metropolitana")).thenReturn(List.of(sucursal));
        Mockito.when(sucursalMapper.toResponseDTO(sucursal)).thenReturn(dto);

        List<SucursalResponseDTO> resultado = sucursalService.listarSucursalesPorRegion("Metropolitana");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sucursal Santiago", resultado.get(0).getNombre());
    }
}
