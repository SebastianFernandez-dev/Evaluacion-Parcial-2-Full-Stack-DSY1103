package com.dsy1103.mspedidos.service;

import com.dsy1103.mspedidos.Client.InventarioClient;
import com.dsy1103.mspedidos.Client.ProductoClient;
import com.dsy1103.mspedidos.dto.DetallePedidoDTO;
import com.dsy1103.mspedidos.dto.InventarioDTO;
import com.dsy1103.mspedidos.dto.ProductoDTO;
import com.dsy1103.mspedidos.mapper.DetallePedidoMapper;
import com.dsy1103.mspedidos.modelo.DetallePedidoModelo;
import com.dsy1103.mspedidos.modelo.PedidoModelo;
import com.dsy1103.mspedidos.repository.DetallePedidoRepository;
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
public class DetallePedidoServiceTest {

    @Mock
    private DetallePedidoRepository detallePedidoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private DetallePedidoMapper detallePedidoMapper;
    @Mock
    private ProductoClient productoClient;
    @Mock
    private InventarioClient inventarioClient;
    @InjectMocks
    private DetallePedidoService detallePedidoService;

    @Test
    @DisplayName("Debe listar todos los detalles de pedido exitosamente")
    void debeListarTodosLosDetalles() {
        //Arrange
        DetallePedidoModelo modelo = DetallePedidoModelo.builder().id(1L).productoId(100L).build();
        DetallePedidoDTO dto = DetallePedidoDTO.builder().id(1L).productoId(100L).build();

        Mockito.when(detallePedidoRepository.findAll()).thenReturn(List.of(modelo));
        Mockito.when(detallePedidoMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        List<DetallePedidoDTO> resultado = detallePedidoService.listarTodos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(100L, resultado.get(0).getProductoId());
    }

    @Test
    @DisplayName("Debe obtener detalle de pedido por ID cuando existe")
    void debeObtenerDetallePorId() {
        //Arrange
        Long id = 1L;
        DetallePedidoModelo modelo = DetallePedidoModelo.builder().id(id).productoId(100L).build();
        DetallePedidoDTO dto = DetallePedidoDTO.builder().id(id).productoId(100L).build();

        Mockito.when(detallePedidoRepository.findById(id)).thenReturn(Optional.of(modelo));
        Mockito.when(detallePedidoMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        DetallePedidoDTO resultado = detallePedidoService.buscarPorId(id);

        //Assert
        assertNotNull(resultado);
        assertEquals(100L, resultado.getProductoId());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando detalle no existe al buscar")
    void debeLanzarExcepcionCuandoDetalleNoExiste() {
        //Arrange
        Mockito.when(detallePedidoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            detallePedidoService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe crear un detalle de pedido exitosamente")
    void debeCrearDetalle() {
        //Arrange
        Long pedidoId = 1L;
        PedidoModelo pedido = PedidoModelo.builder().id(pedidoId).build();

        DetallePedidoDTO dtoEntrada = DetallePedidoDTO.builder()
                .pedidoId(pedidoId).productoId(100L)
                .cantidadPedido(2).precioUnitario(500.0)
                .subtotal(1000.0).fechaRegistro(LocalDateTime.now())
                .estadoDetalle(true).build();

        DetallePedidoModelo modelo = DetallePedidoModelo.builder()
                .productoId(100L).build();

        DetallePedidoModelo guardado = DetallePedidoModelo.builder()
                .id(1L).productoId(100L).pedido(pedido).build();

        DetallePedidoDTO dtoSalida = DetallePedidoDTO.builder()
                .id(1L).productoId(100L).pedidoId(pedidoId).build();

        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedido));
        Mockito.when(detallePedidoMapper.toEntity(dtoEntrada, pedido)).thenReturn(modelo);
        Mockito.when(detallePedidoRepository.save(modelo)).thenReturn(guardado);
        Mockito.when(detallePedidoMapper.toDTO(guardado)).thenReturn(dtoSalida);

        //Act
        DetallePedidoDTO resultado = detallePedidoService.crear(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(100L, resultado.getProductoId());
        assertEquals(pedidoId, resultado.getPedidoId());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear detalle con pedido inexistente")
    void debeLanzarExcepcionAlCrearConPedidoInexistente() {
        //Arrange
        DetallePedidoDTO dto = DetallePedidoDTO.builder().pedidoId(999L).build();

        Mockito.when(pedidoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            detallePedidoService.crear(dto);
        });
    }

    @Test
    @DisplayName("Debe actualizar un detalle validando producto y stock exitosamente")
    void debeActualizarDetalleExitosamente() {
        //Arrange
        Long detalleId = 1L;
        Long productoId = 100L;

        DetallePedidoDTO dto = DetallePedidoDTO.builder()
                .id(detalleId).productoId(productoId)
                .cantidadPedido(2).precioUnitario(500.0)
                .subtotal(1000.0).observacion("OK")
                .fechaRegistro(LocalDateTime.now()).estadoDetalle(true)
                .build();

        PedidoModelo pedido = PedidoModelo.builder().id(1L).build();

        DetallePedidoModelo existente = DetallePedidoModelo.builder()
                .id(detalleId).pedido(pedido).build();

        ProductoDTO producto = ProductoDTO.builder()
                .id(productoId).nombreProducto("Teclado").build();

        InventarioDTO inventario = InventarioDTO.builder()
                .productoId(productoId).cantidadDisponible(10).build();

        Mockito.when(detallePedidoRepository.findById(detalleId)).thenReturn(Optional.of(existente));
        Mockito.when(productoClient.obtenerProductoPorId(productoId)).thenReturn(producto);
        Mockito.when(inventarioClient.obtenerInventarioPorProductoId(productoId)).thenReturn(inventario);

        //Act
        detallePedidoService.actualizar(dto);

        //Assert
        Mockito.verify(detallePedidoRepository).save(any(DetallePedidoModelo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar detalle con producto inexistente")
    void debeLanzarExcepcionCuandoProductoNoExiste() {
        //Arrange
        DetallePedidoDTO dto = DetallePedidoDTO.builder()
                .id(1L).productoId(999L).build();

        DetallePedidoModelo existente = DetallePedidoModelo.builder().id(1L).build();

        Mockito.when(detallePedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(productoClient.obtenerProductoPorId(999L))
                .thenThrow(new RuntimeException("Producto no encontrado"));

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            detallePedidoService.actualizar(dto);
        });

        Mockito.verify(detallePedidoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar detalle con stock insuficiente")
    void debeLanzarExcepcionCuandoStockInsuficiente() {
        //Arrange
        Long productoId = 100L;

        DetallePedidoDTO dto = DetallePedidoDTO.builder()
                .id(1L).productoId(productoId)
                .cantidadPedido(50).build();

        DetallePedidoModelo existente = DetallePedidoModelo.builder().id(1L).build();

        ProductoDTO producto = ProductoDTO.builder().id(productoId).build();
        InventarioDTO inventario = InventarioDTO.builder()
                .productoId(productoId).cantidadDisponible(10).build();

        Mockito.when(detallePedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Mockito.when(productoClient.obtenerProductoPorId(productoId)).thenReturn(producto);
        Mockito.when(inventarioClient.obtenerInventarioPorProductoId(productoId)).thenReturn(inventario);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            detallePedidoService.actualizar(dto);
        });

        Mockito.verify(detallePedidoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar detalle inexistente")
    void debeLanzarExcepcionAlActualizarInexistente() {
        //Arrange
        DetallePedidoDTO dto = DetallePedidoDTO.builder().id(999L).build();

        Mockito.when(detallePedidoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            detallePedidoService.actualizar(dto);
        });

        Mockito.verify(detallePedidoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un detalle de pedido exitosamente")
    void debeEliminarDetalle() {
        //Arrange
        Mockito.when(detallePedidoRepository.existsById(1L)).thenReturn(true);

        //Act
        detallePedidoService.eliminar(1L);

        //Assert
        Mockito.verify(detallePedidoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar detalle inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        //Arrange
        Mockito.when(detallePedidoRepository.existsById(999L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            detallePedidoService.eliminar(999L);
        });

        Mockito.verify(detallePedidoRepository, Mockito.never()).deleteById(any());
    }
}