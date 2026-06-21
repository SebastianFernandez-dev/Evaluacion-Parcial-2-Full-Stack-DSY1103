package com.dsy1103.msproductos.service;

import com.dsy1103.msproductos.dto.ProductoDTO;
import com.dsy1103.msproductos.mapper.ProductoMapper;
import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.model.ProductoModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
import com.dsy1103.msproductos.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private ProductoMapper productoMapper;
    @InjectMocks
    private ProductoService productoService;

    @Test
    @DisplayName("Debe listar todos los productos exitosamente")
    void debeListarProductosExitosamente() {
        //Arrange
        CategoriaModel categoria = CategoriaModel.builder().id(1L).build();
        ProductoModel producto = ProductoModel.builder()
                .id(1L).nombreProducto("Teclado Mecánico").precio(45000.0).categoria(categoria).build();
        ProductoDTO productoDTO = ProductoDTO.builder()
                .id(1L).nombreProducto("Teclado Mecánico").precio(45000.0).categoriaId(1L).build();

        Mockito.when(productoRepository.findAll()).thenReturn(List.of(producto));
        Mockito.when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        //Act
        List<ProductoDTO> resultado = productoService.listarProductos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Teclado Mecánico", resultado.get(0).getNombreProducto());
        assertEquals(45000.0, resultado.get(0).getPrecio());
    }

    @Test
    @DisplayName("Debe obtener producto por ID exitosamente")
    void debeObtenerProductoPorIdExitosamente() {
        //Arrange
        Long productoId = 1L;
        CategoriaModel categoria = CategoriaModel.builder().id(1L).build();
        ProductoModel producto = ProductoModel.builder()
                .id(productoId).nombreProducto("Mouse Inalámbrico").sku("MOU-001").categoria(categoria).build();
        ProductoDTO productoDTO = ProductoDTO.builder()
                .id(productoId).nombreProducto("Mouse Inalámbrico").sku("MOU-001").categoriaId(1L).build();

        Mockito.when(productoRepository.findById(productoId)).thenReturn(Optional.of(producto));
        Mockito.when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        //Act
        ProductoDTO resultado = productoService.obtenerProductoPorId(productoId);

        //Assert
        assertNotNull(resultado);
        assertEquals("Mouse Inalámbrico", resultado.getNombreProducto());
        assertEquals("MOU-001", resultado.getSku());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el producto no existe al buscar")
    void debeLanzarExcepcionCuandoProductoNoExisteAlBuscar() {
        //Arrange
        Mockito.when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            productoService.obtenerProductoPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar un producto exitosamente si la categoría existe")
    void debeGuardarProductoExitosamente() {
        //Arrange
        Long categoriaId = 1L;
        CategoriaModel categoria = CategoriaModel.builder().id(categoriaId).nombreCategoria("Periféricos").build();

        ProductoDTO dtoEntrada = ProductoDTO.builder()
                .nombreProducto("Monitor 27\"").precio(250000.0).categoriaId(categoriaId).build();

        ProductoModel modelParaGuardar = ProductoModel.builder()
                .nombreProducto("Monitor 27\"").precio(250000.0).categoria(categoria).build();

        ProductoModel modelGuardado = ProductoModel.builder()
                .id(1L).nombreProducto("Monitor 27\"").precio(250000.0).categoria(categoria).build();

        ProductoDTO dtoSalida = ProductoDTO.builder()
                .id(1L).nombreProducto("Monitor 27\"").precio(250000.0).categoriaId(categoriaId).build();

        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        Mockito.when(productoMapper.toEntity(dtoEntrada, categoria)).thenReturn(modelParaGuardar);
        Mockito.when(productoRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(productoMapper.toDTO(modelGuardado)).thenReturn(dtoSalida);

        //Act
        ProductoDTO resultado = productoService.guardarProducto(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Monitor 27\"", resultado.getNombreProducto());
        assertEquals(categoriaId, resultado.getCategoriaId());
        Mockito.verify(productoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al guardar producto si la categoría asociada no existe")
    void debeLanzarExcepcionAlGuardarSiCategoriaNoExiste() {
        //Arrange
        ProductoDTO dtoEntrada = ProductoDTO.builder()
                .nombreProducto("Tablet").categoriaId(999L).build();

        Mockito.when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            productoService.guardarProducto(dtoEntrada);
        });

        Mockito.verify(productoRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un producto exitosamente")
    void debeActualizarProductoExitosamente() {
        //Arrange
        Long productoId = 1L;
        Long categoriaId = 1L;
        CategoriaModel categoria = CategoriaModel.builder().id(categoriaId).build();
        ProductoModel existente = ProductoModel.builder()
                .id(productoId).nombreProducto("Teclado").precio(30000.0).categoria(categoria).build();

        ProductoDTO dtoActualizacion = ProductoDTO.builder()
                .id(productoId).nombreProducto("Teclado RGB").precio(45000.0).categoriaId(categoriaId).build();

        Mockito.when(productoRepository.findById(productoId)).thenReturn(Optional.of(existente));
        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));

        ProductoModel actualizado = ProductoModel.builder()
                .id(productoId).nombreProducto("Teclado RGB").precio(45000.0).categoria(categoria).build();

        ProductoDTO dtoSalida = ProductoDTO.builder()
                .id(productoId).nombreProducto("Teclado RGB").precio(45000.0).categoriaId(categoriaId).build();

        Mockito.when(productoRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(productoMapper.toDTO(actualizado)).thenReturn(dtoSalida);

        //Act
        ProductoDTO resultado = productoService.actualizarProducto(dtoActualizacion);

        //Assert
        assertNotNull(resultado);
        assertEquals("Teclado RGB", resultado.getNombreProducto());
        assertEquals(45000.0, resultado.getPrecio());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un producto inexistente")
    void debeLanzarExcepcionAlActualizarProductoInexistente() {
        //Arrange
        ProductoDTO dtoActualizacion = ProductoDTO.builder().id(999L).build();

        Mockito.when(productoRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            productoService.actualizarProducto(dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un producto inexistente")
    void debeLanzarExcepcionAlEliminarProductoInexistente() {
        //Arrange
        Mockito.when(productoRepository.existsById(1L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            productoService.eliminarProducto(1L);
        });

        Mockito.verify(productoRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un producto exitosamente si existe")
    void debeEliminarProductoExitosamente() {
        //Arrange
        Long idEliminar = 1L;
        Mockito.when(productoRepository.existsById(idEliminar)).thenReturn(true);

        //Act
        productoService.eliminarProducto(idEliminar);

        //Assert
        Mockito.verify(productoRepository, Mockito.times(1)).deleteById(idEliminar);
    }

    @Test
    @DisplayName("Debe buscar productos por nombre y precio exitosamente")
    void debeBuscarPorNombreYPrecioExitosamente() {
        //Arrange
        String nombre = "teclado";
        Double precio = 50000.0;
        CategoriaModel categoria = CategoriaModel.builder().id(1L).build();

        ProductoModel producto = ProductoModel.builder()
                .id(1L).nombreProducto("Teclado Mecánico").precio(45000.0).categoria(categoria).build();
        ProductoDTO productoDTO = ProductoDTO.builder()
                .id(1L).nombreProducto("Teclado Mecánico").precio(45000.0).categoriaId(1L).build();

        Mockito.when(productoRepository.findByNombreContengaAndPrecioMenorQue(nombre, precio))
                .thenReturn(List.of(producto));
        Mockito.when(productoMapper.toDTO(producto)).thenReturn(productoDTO);

        //Act
        List<ProductoDTO> resultado = productoService.buscarPorNombreYPrecio(nombre, precio);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Teclado Mecánico", resultado.get(0).getNombreProducto());
    }
}
