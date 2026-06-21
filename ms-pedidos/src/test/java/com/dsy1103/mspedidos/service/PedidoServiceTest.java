package com.dsy1103.mspedidos.service;

import com.dsy1103.mspedidos.dto.PedidoDTO;
import com.dsy1103.mspedidos.mapper.PedidoMapper;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import com.dsy1103.mspedidos.repository.PedidoRepository;
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
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PedidoMapper pedidoMapper;
    @InjectMocks
    private PedidoService pedidoService;

    @Test
    @DisplayName("Debe listar todos los pedidos exitosamente")
    void debeListarTodosLosPedidos() {
        //Arrange
        PedidoModelo modelo = PedidoModelo.builder().id(1L).codigoPedido("PED-001").build();
        PedidoDTO dto = PedidoDTO.builder().id(1L).codigoPedido("PED-001").build();

        Mockito.when(pedidoRepository.findAll()).thenReturn(List.of(modelo));
        Mockito.when(pedidoMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        List<PedidoDTO> resultado = pedidoService.listarTodos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PED-001", resultado.get(0).getCodigoPedido());
    }

    @Test
    @DisplayName("Debe obtener pedido por ID cuando existe")
    void debeObtenerPedidoPorId() {
        //Arrange
        Long id = 1L;
        PedidoModelo modelo = PedidoModelo.builder().id(id).codigoPedido("PED-001").build();
        PedidoDTO dto = PedidoDTO.builder().id(id).codigoPedido("PED-001").build();

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(modelo));
        Mockito.when(pedidoMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        PedidoDTO resultado = pedidoService.buscarPorId(id);

        //Assert
        assertNotNull(resultado);
        assertEquals("PED-001", resultado.getCodigoPedido());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando pedido no existe al buscar")
    void debeLanzarExcepcionCuandoPedidoNoExiste() {
        //Arrange
        Mockito.when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pedidoService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe crear un pedido exitosamente")
    void debeCrearPedido() {
        //Arrange
        PedidoDTO dtoEntrada = PedidoDTO.builder()
                .codigoPedido("PED-001").totalPedido(100)
                .fechaPedido(LocalDateTime.now())
                .direccionEntrega("Calle 123")
                .pagadopedido(false).usuarioId(1L)
                .estadopedido("PENDIENTE")
                .build();

        PedidoModelo modelo = PedidoModelo.builder()
                .codigoPedido("PED-001").build();

        PedidoModelo guardado = PedidoModelo.builder()
                .id(1L).codigoPedido("PED-001").build();

        PedidoDTO dtoSalida = PedidoDTO.builder()
                .id(1L).codigoPedido("PED-001").build();

        Mockito.when(pedidoMapper.toEntity(dtoEntrada)).thenReturn(modelo);
        Mockito.when(pedidoRepository.save(modelo)).thenReturn(guardado);
        Mockito.when(pedidoMapper.toDTO(guardado)).thenReturn(dtoSalida);

        //Act
        PedidoDTO resultado = pedidoService.crear(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals("PED-001", resultado.getCodigoPedido());
    }

    @Test
    @DisplayName("Debe actualizar un pedido exitosamente")
    void debeActualizarPedido() {
        //Arrange
        Long id = 1L;
        PedidoDTO dto = PedidoDTO.builder()
                .id(id).totalPedido(200).pagadopedido(true)
                .direccionEntrega("Nueva Dirección")
                .estadopedido("ENVIADO")
                .build();

        PedidoModelo existente = PedidoModelo.builder()
                .id(id).codigoPedido("PED-001").build();

        Mockito.when(pedidoRepository.findById(id)).thenReturn(Optional.of(existente));

        //Act
        pedidoService.actualizar(dto);

        //Assert
        Mockito.verify(pedidoRepository).save(any(PedidoModelo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar pedido inexistente")
    void debeLanzarExcepcionAlActualizarInexistente() {
        //Arrange
        PedidoDTO dto = PedidoDTO.builder().id(999L).build();

        Mockito.when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pedidoService.actualizar(dto);
        });

        Mockito.verify(pedidoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un pedido exitosamente")
    void debeEliminarPedido() {
        //Arrange
        Mockito.when(pedidoRepository.existsById(1L)).thenReturn(true);

        //Act
        pedidoService.eliminar(1L);

        //Assert
        Mockito.verify(pedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar pedido inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        //Arrange
        Mockito.when(pedidoRepository.existsById(999L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            pedidoService.eliminar(999L);
        });

        Mockito.verify(pedidoRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe obtener pedidos pagados ordenados por fecha")
    void debeObtenerPedidosPagadosYOrdenados() {
        //Arrange
        PedidoModelo pedido = PedidoModelo.builder().id(1L).pagadopedido(true).build();

        Mockito.when(pedidoRepository.findPedidosPagadosOrdenadosPorFecha())
                .thenReturn(List.of(pedido));

        //Act
        var resultado = pedidoService.obtenerPedidosPagadosYOrdenados();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getPagadopedido());
    }
}