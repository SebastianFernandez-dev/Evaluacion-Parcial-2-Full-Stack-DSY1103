package com.dsy1103.msusuarios.service;

import com.dsy1103.msusuarios.dto.UsuarioDTO;
import com.dsy1103.msusuarios.mapper.UsuarioMapper;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Debe listar todos los usuarios exitosamente")
    void debeListarTodosLosUsuarios() {
        //Arrange
        UsuarioModelo modelo = UsuarioModelo.builder().id(1L).primerNombre("Juan").build();
        UsuarioDTO dto = UsuarioDTO.builder().id(1L).primerNombre("Juan").build();

        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(modelo));
        Mockito.when(usuarioMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        List<UsuarioDTO> resultado = usuarioService.listarTodos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getPrimerNombre());
    }

    @Test
    @DisplayName("Debe obtener usuario por ID cuando existe")
    void debeObtenerUsuarioPorId() {
        //Arrange
        Long id = 1L;
        UsuarioModelo modelo = UsuarioModelo.builder().id(id).correoUsuario("test@mail.com").build();
        UsuarioDTO dto = UsuarioDTO.builder().id(id).correoUsuario("test@mail.com").build();

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(modelo));
        Mockito.when(usuarioMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        UsuarioDTO resultado = usuarioService.buscarPorId(id);

        //Assert
        assertNotNull(resultado);
        assertEquals("test@mail.com", resultado.getCorreoUsuario());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando usuario no existe al buscar")
    void debeLanzarExcepcionCuandoUsuarioNoExiste() {
        //Arrange
        Mockito.when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe crear un usuario exitosamente")
    void debeCrearUsuario() {
        //Arrange
        UsuarioDTO dtoEntrada = UsuarioDTO.builder()
                .primerNombre("Carlos").correoUsuario("carlos@mail.com")
                .rut(12345678).dvRut(5).activo(true)
                .fechaRegistro(LocalDate.now()).build();

        UsuarioModelo modelo = UsuarioModelo.builder()
                .primerNombre("Carlos").correoUsuario("carlos@mail.com")
                .rut(12345678).dvRut(5).activo(true)
                .fechaRegistro(LocalDate.now()).build();

        UsuarioModelo guardado = UsuarioModelo.builder()
                .id(1L).primerNombre("Carlos").correoUsuario("carlos@mail.com")
                .rut(12345678).dvRut(5).activo(true)
                .fechaRegistro(LocalDate.now()).build();

        UsuarioDTO dtoSalida = UsuarioDTO.builder()
                .id(1L).primerNombre("Carlos").correoUsuario("carlos@mail.com")
                .rut(12345678).dvRut(5).activo(true)
                .fechaRegistro(LocalDate.now()).build();

        Mockito.when(usuarioMapper.toEntity(dtoEntrada)).thenReturn(modelo);
        Mockito.when(usuarioRepository.save(modelo)).thenReturn(guardado);
        Mockito.when(usuarioMapper.toDTO(guardado)).thenReturn(dtoSalida);

        //Act
        UsuarioDTO resultado = usuarioService.crear(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("carlos@mail.com", resultado.getCorreoUsuario());
    }

    @Test
    @DisplayName("Debe actualizar un usuario exitosamente")
    void debeActualizarUsuario() {
        //Arrange
        Long id = 1L;
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(id).primerNombre("Carlos Updated")
                .correoUsuario("carlos@mail.com").rut(12345678).dvRut(5)
                .activo(true).fechaRegistro(LocalDate.now()).build();

        UsuarioModelo existente = UsuarioModelo.builder().id(id).build();

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(existente));

        //Act
        usuarioService.actualizar(dto);

        //Assert
        Mockito.verify(usuarioRepository).save(any(UsuarioModelo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar usuario inexistente")
    void debeLanzarExcepcionAlActualizarInexistente() {
        //Arrange
        UsuarioDTO dto = UsuarioDTO.builder().id(999L).build();

        Mockito.when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.actualizar(dto);
        });

        Mockito.verify(usuarioRepository, Mockito.never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un usuario exitosamente")
    void debeEliminarUsuario() {
        //Arrange
        Mockito.when(usuarioRepository.existsById(1L)).thenReturn(true);

        //Act
        usuarioService.eliminar(1L);

        //Assert
        Mockito.verify(usuarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar usuario inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        //Arrange
        Mockito.when(usuarioRepository.existsById(999L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.eliminar(999L);
        });

        Mockito.verify(usuarioRepository, Mockito.never()).deleteById(any());
    }
}