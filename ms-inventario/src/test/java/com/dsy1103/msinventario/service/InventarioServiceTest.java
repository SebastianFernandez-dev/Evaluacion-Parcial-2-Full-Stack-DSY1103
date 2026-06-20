package com.dsy1103.msinventario.service;

import com.dsy1103.msinventario.client.ProductoClient;
import com.dsy1103.msinventario.dto.InventarioDTO;
import com.dsy1103.msinventario.dto.InventarioProductoDTO;
import com.dsy1103.msinventario.dto.ProductoDTO;
import com.dsy1103.msinventario.model.InventarioModel;
import com.dsy1103.msinventario.repository.InventarioRepository;
import com.dsy1103.msinventario.repository.MovimientoStockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;
    @Mock
    private MovimientoStockRepository movimientoStockRepository;
    @Mock
    private ProductoClient productoClient;
    @InjectMocks
    private InventarioService inventarioService;

    @Test
    @DisplayName("Debe listar todos los inventarios exitosamente")
    void debeListarInventariosExitosamente() {
        //Arrange
        InventarioModel inventario = InventarioModel.builder().id(1L).codigo("INV-001").build();
        Mockito.when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        //Act
        List<InventarioDTO> resultado = inventarioService.listarInventarios();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("INV-001", resultado.get(0).getCodigo());
    }

    @Test
    @DisplayName("Debe obtener inventario por ID combinando datos de Producto y Moviientos")
    void debeObtenerInventarioPorIdCompleto() {
        //Arrange
        Long inventarioId = 1L;
        Long productoId = 100L;

        InventarioModel inventarioMock = InventarioModel.builder()
                .id(inventarioId).codigo("INV-001").productoId(productoId).build();

        ProductoDTO productoMock = ProductoDTO.builder()
                .id(productoId).nombre("Teclado Mecánico").sku("TEC-123").build();

        Mockito.when(inventarioRepository.findById(inventarioId)).thenReturn(Optional.of(inventarioMock));
        Mockito.when(productoClient.obtenerProductoPorId(productoId)).thenReturn(productoMock);
        Mockito.when(movimientoStockRepository.findByInventarioId(inventarioId)).thenReturn(new ArrayList<>());

        //Act
        InventarioProductoDTO resultado = inventarioService.obtenerInventarioPorId(inventarioId);

        //Assert
        assertNotNull(resultado);
        assertEquals("Teclado Mecánico", resultado.getNombreProducto());
        assertEquals("INV-001", resultado.getCodigo());
        assertTrue(resultado.getListaMovimientosStock().isEmpty());
    }

    @Test
    @DisplayName("Debe manejar la caída de ProductoClient asignando 'Servicio no disponible'")
    void debeManejarFallaDeProductoClient() {
        //Arrange
        Long inventarioId = 1L;
        InventarioModel inventarioMock = InventarioModel.builder().id(inventarioId).productoId(99L).build();

        Mockito.when(inventarioRepository.findById(inventarioId)).thenReturn(Optional.of(inventarioMock));
        Mockito.when(productoClient.obtenerProductoPorId(99L)).thenThrow(new RuntimeException("Timeout Connection"));
        Mockito.when(movimientoStockRepository.findByInventarioId(inventarioId)).thenReturn(new ArrayList<>());

        //Act
        InventarioProductoDTO resultado = inventarioService.obtenerInventarioPorId(inventarioId);

        //Assert
        assertNotNull(resultado);
        assertEquals("Servicio no disponible", resultado.getNombreProducto());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el inventario no existe al buscar")
    void debeLanzarExcepcionCuandoInventarioNoExiste() {
        //Arrange
        Mockito.when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            inventarioService.obtenerInventarioPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un inventario inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        //Arrange
        Mockito.when(inventarioRepository.existsById(1L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            inventarioService.eliminarInventario(1L);
        });

        //Verificamos que jamás se llamó al metodo delete debido a la excepción previa
        Mockito.verify(inventarioRepository, Mockito.never()).deleteById(any());
    }
}
