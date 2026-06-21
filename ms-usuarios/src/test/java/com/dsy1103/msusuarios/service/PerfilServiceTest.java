package com.dsy1103.msusuarios.service;


import com.dsy1103.msusuarios.dto.PerfilDTO;
import com.dsy1103.msusuarios.mapper.PerfilMapper;
import com.dsy1103.msusuarios.modelo.PerfilModelo;
import com.dsy1103.msusuarios.modelo.UsuarioModelo;
import com.dsy1103.msusuarios.repository.PerfilRepository;
import com.dsy1103.msusuarios.repository.UsuarioRepository;
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
public class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PerfilMapper perfilMapper;
    @InjectMocks
    private PerfilService perfilService;

    @Test
    @DisplayName("Debe listar todos los perfiles exitosamente")
    void debeListarTodosLosPerfiles() {
        //Arrange
        PerfilModelo modelo = PerfilModelo.builder().id(1L).nombrePerfil("Admin").build();
        PerfilDTO dto = PerfilDTO.builder().id(1L).nombrePerfil("Admin").build();

        Mockito.when(perfilRepository.findAll()).thenReturn(List.of(modelo));
        Mockito.when(perfilMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        List<PerfilDTO> resultado = perfilService.listarTodo();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Admin", resultado.get(0).getNombrePerfil());
    }

    @Test
    @DisplayName("Debe obtener perfil por ID cuando existe")
    void debeObtenerPerfilPorId() {
        //Arrange
        Long id = 1L;
        PerfilModelo modelo = PerfilModelo.builder().id(id).nombrePerfil("Admin").build();
        PerfilDTO dto = PerfilDTO.builder().id(id).nombrePerfil("Admin").build();

        Mockito.when(perfilRepository.findById(id)).thenReturn(Optional.of(modelo));
        Mockito.when(perfilMapper.toDTO(modelo)).thenReturn(dto);

        //Act
        PerfilDTO resultado = perfilService.buscarPorId(id);

        //Assert
        assertNotNull(resultado);
        assertEquals("Admin", resultado.getNombrePerfil());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando perfil no existe al buscar")
    void debeLanzarExcepcionCuandoPerfilNoExiste() {
        //Arrange
        Mockito.when(perfilRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            perfilService.buscarPorId(999L);
        });
    }

    @Test
    @DisplayName("Debe crear un perfil exitosamente")
    void debeCrearPerfil() {
        //Arrange
        Long usuarioId = 1L;
        UsuarioModelo usuario = UsuarioModelo.builder().id(usuarioId).build();

        PerfilDTO dtoEntrada = PerfilDTO.builder()
                .nombrePerfil("Editor").usuarioId(usuarioId).build();

        PerfilModelo modelo = PerfilModelo.builder()
                .nombrePerfil("Editor").build();

        PerfilModelo guardado = PerfilModelo.builder()
                .id(1L).nombrePerfil("Editor").usuario(usuario).build();

        PerfilDTO dtoSalida = PerfilDTO.builder()
                .id(1L).nombrePerfil("Editor").usuarioId(usuarioId).build();

        Mockito.when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        Mockito.when(perfilMapper.toEntity(dtoEntrada)).thenReturn(modelo);
        Mockito.when(perfilRepository.save(any(PerfilModelo.class))).thenReturn(guardado);
        Mockito.when(perfilMapper.toDTO(guardado)).thenReturn(dtoSalida);

        //Act
        PerfilDTO resultado = perfilService.crear(dtoEntrada);

        //Assert
        assertNotNull(resultado);
        assertEquals("Editor", resultado.getNombrePerfil());
        assertEquals(usuarioId, resultado.getUsuarioId());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear perfil con usuario inexistente")
    void debeLanzarExcepcionAlCrearConUsuarioInexistente() {
        //Arrange
        PerfilDTO dto = PerfilDTO.builder()
                .nombrePerfil("Admin").usuarioId(999L).build();

        Mockito.when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            perfilService.crear(dto);
        });
    }

    @Test
    @DisplayName("Debe eliminar un perfil exitosamente")
    void debeEliminarPerfil() {
        //Arrange
        Mockito.when(perfilRepository.existsById(1L)).thenReturn(true);

        //Act
        perfilService.eliminar(1L);

        //Assert
        Mockito.verify(perfilRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar perfil inexistente")
    void debeLanzarExcepcionAlEliminarInexistente() {
        //Arrange
        Mockito.when(perfilRepository.existsById(999L)).thenReturn(false);

        //Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            perfilService.eliminar(999L);
        });

        Mockito.verify(perfilRepository, Mockito.never()).deleteById(any());
    }
}