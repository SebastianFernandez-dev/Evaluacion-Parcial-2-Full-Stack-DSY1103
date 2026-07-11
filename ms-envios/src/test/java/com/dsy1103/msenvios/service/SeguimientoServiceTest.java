package com.dsy1103.msenvios.service;

import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.mapper.SeguimientoMapper;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import com.dsy1103.msenvios.repository.EnvioRepository;
import com.dsy1103.msenvios.repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class SeguimientoServiceTest {

    @Mock
    private SeguimientoRepository seguimientoRepo;
    @Mock
    private SeguimientoMapper seguimientoMapper;
    @Mock
    private EnvioRepository envioRepository;
    @InjectMocks
    private SeguimientoService seguimientoService;

    @Test
    @DisplayName("Debe listar todos los seguimientos exitosamente")
    void debeListarSeguimientosExitosamente() {
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo seguimiento = SeguimientoModelo.builder()
                .id(1L).estadoSegui("En Bodega").envio(envio).build();
        SeguimientoResponseDTO dto = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Bodega").envioId(1L).build();

        Mockito.when(seguimientoRepo.findAll()).thenReturn(List.of(seguimiento));
        Mockito.when(seguimientoMapper.toResponseDTO(seguimiento)).thenReturn(dto);

        List<SeguimientoResponseDTO> resultado = seguimientoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("En Bodega", resultado.get(0).getEstadoSegui());
    }

    @Test
    @DisplayName("Debe obtener seguimiento por ID exitosamente")
    void debeObtenerSeguimientoPorIdExitosamente() {
        Long seguimientoId = 1L;
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo seguimiento = SeguimientoModelo.builder()
                .id(seguimientoId).estadoSegui("En Bodega").envio(envio).build();
        SeguimientoResponseDTO dto = SeguimientoResponseDTO.builder()
                .id(seguimientoId).estadoSegui("En Bodega").envioId(1L).build();

        Mockito.when(seguimientoRepo.findById(seguimientoId)).thenReturn(Optional.of(seguimiento));
        Mockito.when(seguimientoMapper.toResponseDTO(seguimiento)).thenReturn(dto);

        SeguimientoResponseDTO resultado = seguimientoService.buscarPorId(seguimientoId);

        assertNotNull(resultado);
        assertEquals("En Bodega", resultado.getEstadoSegui());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el seguimiento no existe al buscar")
    void debeLanzarExcepcionCuandoSeguimientoNoExiste() {
        Mockito.when(seguimientoRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            seguimientoService.buscarPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar un seguimiento exitosamente si el envio existe")
    void debeGuardarSeguimientoExitosamente() {
        Long envioId = 1L;
        EnvioModelo envio = EnvioModelo.builder().id(envioId).build();

        SeguimientoRequestDTO dtoEntrada = SeguimientoRequestDTO.builder()
                .envioId(envioId).estadoSegui("En Bodega")
                .ubiAtual("Santiago").observacion("Sin novedades")
                .fechaSegui(LocalDateTime.now()).visible(true).build();

        SeguimientoModelo modelParaGuardar = SeguimientoModelo.builder()
                .estadoSegui("En Bodega").build();

        SeguimientoModelo modelGuardado = SeguimientoModelo.builder()
                .id(1L).estadoSegui("En Bodega").envio(envio).build();

        SeguimientoResponseDTO dtoSalida = SeguimientoResponseDTO.builder()
                .id(1L).estadoSegui("En Bodega").envioId(envioId).build();

        Mockito.when(envioRepository.findById(envioId)).thenReturn(Optional.of(envio));
        Mockito.when(seguimientoMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(seguimientoRepo.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(seguimientoMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        SeguimientoResponseDTO resultado = seguimientoService.crear(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("En Bodega", resultado.getEstadoSegui());
        Mockito.verify(seguimientoRepo, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException al guardar seguimiento si el envio no existe")
    void debeLanzarExcepcionAlGuardarSiEnvioNoExiste() {
        SeguimientoRequestDTO dtoEntrada = SeguimientoRequestDTO.builder()
                .envioId(999L).estadoSegui("Test")
                .ubiAtual("Test").observacion("Test")
                .fechaSegui(LocalDateTime.now()).visible(true).build();

        Mockito.when(envioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            seguimientoService.crear(dtoEntrada);
        });

        Mockito.verify(seguimientoRepo, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un seguimiento exitosamente")
    void debeActualizarSeguimientoExitosamente() {
        Long seguimientoId = 1L;
        Long envioId = 1L;
        EnvioModelo envio = EnvioModelo.builder().id(envioId).build();
        SeguimientoModelo existente = SeguimientoModelo.builder()
                .id(seguimientoId).estadoSegui("En Bodega").envio(envio).build();

        SeguimientoRequestDTO dtoActualizacion = SeguimientoRequestDTO.builder()
                .envioId(envioId).estadoSegui("En Reparto")
                .ubiAtual("Providencia").observacion("En camino")
                .fechaSegui(LocalDateTime.now()).visible(true).build();

        SeguimientoModelo actualizado = SeguimientoModelo.builder()
                .id(seguimientoId).estadoSegui("En Reparto").envio(envio).build();

        SeguimientoResponseDTO dtoSalida = SeguimientoResponseDTO.builder()
                .id(seguimientoId).estadoSegui("En Reparto").envioId(envioId).build();

        Mockito.when(seguimientoRepo.findById(seguimientoId)).thenReturn(Optional.of(existente));
        Mockito.when(envioRepository.findById(envioId)).thenReturn(Optional.of(envio));
        Mockito.when(seguimientoRepo.save(existente)).thenReturn(actualizado);
        Mockito.when(seguimientoMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        SeguimientoResponseDTO resultado = seguimientoService.actualizar(seguimientoId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("En Reparto", resultado.getEstadoSegui());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un seguimiento inexistente")
    void debeLanzarExcepcionAlActualizarSeguimientoInexistente() {
        SeguimientoRequestDTO dtoActualizacion = SeguimientoRequestDTO.builder()
                .envioId(1L).estadoSegui("Test")
                .ubiAtual("Test").observacion("Test")
                .fechaSegui(LocalDateTime.now()).visible(true).build();

        Mockito.when(seguimientoRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            seguimientoService.actualizar(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un seguimiento inexistente")
    void debeLanzarExcepcionAlEliminarSeguimientoInexistente() {
        Mockito.when(seguimientoRepo.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            seguimientoService.eliminar(1L);
        });

        Mockito.verify(seguimientoRepo, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un seguimiento exitosamente si existe")
    void debeEliminarSeguimientoExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(seguimientoRepo.existsById(idEliminar)).thenReturn(true);

        seguimientoService.eliminar(idEliminar);

        Mockito.verify(seguimientoRepo, Mockito.times(1)).deleteById(idEliminar);
    }
}
