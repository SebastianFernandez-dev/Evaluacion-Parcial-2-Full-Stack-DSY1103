package com.dsy1103.msenvios.service;

import com.dsy1103.msenvios.Client.PedidoClient;
import com.dsy1103.msenvios.Client.UsuarioClient;
import com.dsy1103.msenvios.dto.EnvioRequestDTO;
import com.dsy1103.msenvios.dto.EnvioResponseDTO;
import com.dsy1103.msenvios.dto.PedidoDTO;
import com.dsy1103.msenvios.dto.UsuarioDTO;
import com.dsy1103.msenvios.exception.CodigoEnvioAlreadyExistsException;
import com.dsy1103.msenvios.mapper.EnvioMapper;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.repository.EnvioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;
    @Mock
    private EnvioMapper envioMapper;
    @Mock
    private PedidoClient pedidoClient;
    @Mock
    private UsuarioClient usuarioClient;
    @InjectMocks
    private EnvioService envioService;

    @Test
    @DisplayName("Debe listar todos los envios exitosamente")
    void debeListarEnviosExitosamente() {
        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").build();
        EnvioResponseDTO dto = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-001").build();

        Mockito.when(envioRepository.findAll()).thenReturn(List.of(envio));
        Mockito.when(envioMapper.toResponseDTO(envio)).thenReturn(dto);

        List<EnvioResponseDTO> resultado = envioService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ENV-001", resultado.get(0).getCodigoEnvio());
    }

    @Test
    @DisplayName("Debe obtener envio por ID exitosamente")
    void debeObtenerEnvioPorIdExitosamente() {
        Long envioId = 1L;
        EnvioModelo envio = EnvioModelo.builder()
                .id(envioId).codigoEnvio("ENV-001").build();
        EnvioResponseDTO dto = EnvioResponseDTO.builder()
                .id(envioId).codigoEnvio("ENV-001").build();

        Mockito.when(envioRepository.findById(envioId)).thenReturn(Optional.of(envio));
        Mockito.when(envioMapper.toResponseDTO(envio)).thenReturn(dto);

        EnvioResponseDTO resultado = envioService.buscarPorId(envioId);

        assertNotNull(resultado);
        assertEquals("ENV-001", resultado.getCodigoEnvio());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el envio no existe al buscar")
    void debeLanzarExcepcionCuandoEnvioNoExisteAlBuscar() {
        Mockito.when(envioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            envioService.buscarPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar un envio exitosamente")
    void debeGuardarEnvioExitosamente() {
        EnvioRequestDTO dtoEntrada = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-NEW-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Calle Test 123").estadoEnvio("Pendiente")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();

        EnvioModelo modelParaGuardar = EnvioModelo.builder()
                .codigoEnvio("ENV-NEW-001").pedidoId(1L).build();

        EnvioModelo modelGuardado = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-NEW-001").pedidoId(1L).build();

        EnvioResponseDTO dtoSalida = EnvioResponseDTO.builder()
                .id(1L).codigoEnvio("ENV-NEW-001").build();

        Mockito.when(envioRepository.findBycodigoEnvio("ENV-NEW-001")).thenReturn(Optional.empty());
        Mockito.when(pedidoClient.obtenerPedidoPorId(1L)).thenReturn(new PedidoDTO());
        Mockito.when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(new UsuarioDTO());
        Mockito.when(envioMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(envioRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(envioMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        EnvioResponseDTO resultado = envioService.crear(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("ENV-NEW-001", resultado.getCodigoEnvio());
        Mockito.verify(envioRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar CodigoEnvioAlreadyExistsException al guardar con codigo duplicado")
    void debeLanzarExcepcionAlGuardarConCodigoDuplicado() {
        EnvioRequestDTO dtoEntrada = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Test").estadoEnvio("Pendiente")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();

        EnvioModelo existente = EnvioModelo.builder()
                .id(2L).codigoEnvio("ENV-001").build();

        Mockito.when(envioRepository.findBycodigoEnvio("ENV-001")).thenReturn(Optional.of(existente));

        assertThrows(CodigoEnvioAlreadyExistsException.class, () -> {
            envioService.crear(dtoEntrada);
        });

        Mockito.verify(envioRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un envio exitosamente")
    void debeActualizarEnvioExitosamente() {
        Long envioId = 1L;
        EnvioModelo existente = EnvioModelo.builder()
                .id(envioId).codigoEnvio("ENV-001").build();

        EnvioRequestDTO dtoActualizacion = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Calle Nueva 456").estadoEnvio("En Transito")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();

        EnvioModelo actualizado = EnvioModelo.builder()
                .id(envioId).codigoEnvio("ENV-001").direccionDestino("Calle Nueva 456").build();

        EnvioResponseDTO dtoSalida = EnvioResponseDTO.builder()
                .id(envioId).codigoEnvio("ENV-001").direccionDestino("Calle Nueva 456").build();

        Mockito.when(envioRepository.findById(envioId)).thenReturn(Optional.of(existente));
        Mockito.when(envioRepository.findBycodigoEnvio("ENV-001")).thenReturn(Optional.of(existente));
        Mockito.when(envioRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(envioMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        EnvioResponseDTO resultado = envioService.actualizar(envioId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Calle Nueva 456", resultado.getDireccionDestino());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un envio inexistente")
    void debeLanzarExcepcionAlActualizarEnvioInexistente() {
        EnvioRequestDTO dtoActualizacion = EnvioRequestDTO.builder()
                .codigoEnvio("ENV-001").pedidoId(1L).usuarioId(1L)
                .direccionDestino("Test").estadoEnvio("Pendiente")
                .fechaSalida(LocalDateTime.now())
                .fechaEntregaEstimada(LocalDate.now().plusDays(3))
                .fechaEntregado(LocalDate.now()).activo(true).build();

        Mockito.when(envioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            envioService.actualizar(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un envio inexistente")
    void debeLanzarExcepcionAlEliminarEnvioInexistente() {
        Mockito.when(envioRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            envioService.eliminar(1L);
        });

        Mockito.verify(envioRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un envio exitosamente si existe")
    void debeEliminarEnvioExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(envioRepository.existsById(idEliminar)).thenReturn(true);

        envioService.eliminar(idEliminar);

        Mockito.verify(envioRepository, Mockito.times(1)).deleteById(idEliminar);
    }

    @Test
    @DisplayName("Debe obtener envios en rango no entregados")
    void debeObtenerEnviosEnRangoNoEntregados() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fin = LocalDateTime.now();

        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").estadoEnvio("En Transito").build();

        Mockito.when(envioRepository.findEnviosEnRangoNoEntregados(inicio, fin))
                .thenReturn(List.of(envio));

        List<EnvioModelo> resultado = envioService.obtenerEnviosEnRangoNoEntregados(inicio, fin);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ENV-001", resultado.get(0).getCodigoEnvio());
    }
}
