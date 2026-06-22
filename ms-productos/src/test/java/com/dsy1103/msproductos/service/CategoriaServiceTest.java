package com.dsy1103.msproductos.service;

import com.dsy1103.msproductos.dto.CategoriaDTO;
import com.dsy1103.msproductos.mapper.CategoriaMapper;
import com.dsy1103.msproductos.model.CategoriaModel;
import com.dsy1103.msproductos.repository.CategoriaRepository;
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
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private CategoriaMapper categoriaMapper;
    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    @DisplayName("Debe listar todas las categorias exitosamente")
    void debeListarCategoriasExitosamente() {
        //Arrange
        CategoriaModel categoria = CategoriaModel.builder()
                .id(1L).nombreCategoria("Electrónica").codigoCategoria("CAT-001").build();
        CategoriaDTO categoriaDTO = CategoriaDTO.builder()
                .id(1L).nombreCategoria("Electrónica").codigoCategoria("CAT-001").build();

        Mockito.when(categoriaRepository.findAll()).thenReturn(List.of(categoria));
        Mockito.when(categoriaMapper.toDTO(categoria)).thenReturn(categoriaDTO);

        //Act
        List<CategoriaDTO> resultado = categoriaService.listarCategorias();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Electrónica", resultado.get(0).getNombreCategoria());
        assertEquals("CAT-001", resultado.get(0).getCodigoCategoria());
    }

    @Test
    @DisplayName("Debe obtener categoria por ID exitosamente")
    void debeObtenerCategoriaPorIdExitosamente() {
        //Arrange
        Long categoriaId = 1L;
        CategoriaModel categoria = CategoriaModel.builder()
                .id(categoriaId).nombreCategoria("Ropa").build();
        CategoriaDTO categoriaDTO = CategoriaDTO.builder()
                .id(categoriaId).nombreCategoria("Ropa").build();

        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        Mockito.when(categoriaMapper.toDTO(categoria)).thenReturn(categoriaDTO);

        //Act
        CategoriaDTO resultado = categoriaService.obtenerCategoriaPorId(categoriaId);

        //Assert
        assertNotNull(resultado);
        assertEquals("Ropa", resultado.getNombreCategoria());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando la categoria no existe al buscar")
    void debeLanzarExcepcionCuandoCategoriaNoExiste() {
        //Arrange
        Mockito.when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            categoriaService.obtenerCategoriaPorId(1L);
        });
    }

    @Test
    @DisplayName("Debe guardar una categoria exitosamente")
    void debeGuardarCategoriaExitosamente() {
        //Arrange
        CategoriaDTO dtoEntrada = CategoriaDTO.builder()
                .nombreCategoria("Hogar").codigoCategoria("CAT-002").build();

        CategoriaModel modelParaGuardar = CategoriaModel.builder()
                .nombreCategoria("Hogar").codigoCategoria("CAT-002").build();

        CategoriaModel modelGuardado = CategoriaModel.builder()
                .id(1L).nombreCategoria("Hogar").codigoCategoria("CAT-002").build();

        CategoriaDTO dtoSalida = CategoriaDTO.builder()
                .id(1L).nombreCategoria("Hogar").codigoCategoria("CAT-002").build();

        Mockito.when(categoriaMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(categoriaRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(categoriaMapper.toDTO(modelGuardado)).thenReturn(dtoSalida);

        //Act
        CategoriaDTO resultado = categoriaService.guardarCategoria(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Hogar", resultado.getNombreCategoria());
        assertEquals("CAT-002", resultado.getCodigoCategoria());
        Mockito.verify(categoriaRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar una categoria exitosamente")
    void debeActualizarCategoriaExitosamente() {
        //Arrange
        Long categoriaId = 1L;
        CategoriaModel existente = CategoriaModel.builder()
                .id(categoriaId).nombreCategoria("Viejo Nombre").codigoCategoria("CAT-001").build();

        CategoriaDTO dtoActualizacion = CategoriaDTO.builder()
                .id(categoriaId).nombreCategoria("Nuevo Nombre").codigoCategoria("CAT-001")
                .activoCategoria(true).fechaCreacion(LocalDate.now()).build();

        CategoriaModel actualizado = CategoriaModel.builder()
                .id(categoriaId).nombreCategoria("Nuevo Nombre").codigoCategoria("CAT-001")
                .activoCategoria(true).fechaCreacion(LocalDate.now()).build();

        CategoriaDTO dtoSalida = CategoriaDTO.builder()
                .id(categoriaId).nombreCategoria("Nuevo Nombre").codigoCategoria("CAT-001")
                .activoCategoria(true).fechaCreacion(LocalDate.now()).build();

        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(existente));
        Mockito.when(categoriaRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(categoriaMapper.toDTO(actualizado)).thenReturn(dtoSalida);

        //Act
        CategoriaDTO resultado = categoriaService.actualizarCategoria(dtoActualizacion);

        //Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombreCategoria());
        assertEquals("CAT-001", resultado.getCodigoCategoria());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar una categoria inexistente")
    void debeLanzarExcepcionAlActualizarCategoriaInexistente() {
        //Arrange
        CategoriaDTO dtoActualizacion = CategoriaDTO.builder().id(999L).build();
        Mockito.when(categoriaRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            categoriaService.actualizarCategoria(dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar una categoria inexistente")
    void debeLanzarExcepcionAlEliminarCategoriaInexistente() {
        //Arrange
        Mockito.when(categoriaRepository.existsById(1L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            categoriaService.eliminarCategoria(1L);
        });

        Mockito.verify(categoriaRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar una categoria exitosamente si existe")
    void debeEliminarCategoriaExitosamente() {
        //Arrange
        Long idEliminar = 1L;
        Mockito.when(categoriaRepository.existsById(idEliminar)).thenReturn(true);

        //Act
        categoriaService.eliminarCategoria(idEliminar);

        //Assert
        Mockito.verify(categoriaRepository, Mockito.times(1)).deleteById(idEliminar);
    }
}
