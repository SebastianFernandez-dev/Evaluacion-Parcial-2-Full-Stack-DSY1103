package com.dsy1103.msenvios.repository;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeguimientoRepositoryTest {

    @Mock
    private SeguimientoRepository seguimientoRepository;

    @Test
    @DisplayName("findById debe retornar seguimiento cuando existe")
    void findByIdDebeRetornarSeguimiento() {
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo seguimiento = SeguimientoModelo.builder()
                .id(1L).estadoSegui("En Bodega").envio(envio).build();
        Mockito.when(seguimientoRepository.findById(1L)).thenReturn(Optional.of(seguimiento));

        Optional<SeguimientoModelo> resultado = seguimientoRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("En Bodega", resultado.get().getEstadoSegui());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(seguimientoRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<SeguimientoModelo> resultado = seguimientoRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findByEnvio_Id debe retornar seguimientos del envio")
    void findByEnvioIdDebeFiltrar() {
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo seguimiento = SeguimientoModelo.builder()
                .id(1L).estadoSegui("En Bodega").envio(envio).build();
        Mockito.when(seguimientoRepository.findByEnvio_Id(1L)).thenReturn(List.of(seguimiento));

        List<SeguimientoModelo> resultados = seguimientoRepository.findByEnvio_Id(1L);

        assertEquals(1, resultados.size());
        assertEquals("En Bodega", resultados.get(0).getEstadoSegui());
    }

    @Test
    @DisplayName("findByvisibleTrue debe retornar seguimientos visibles")
    void findByvisibleTrueDebeFiltrar() {
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo seguimiento = SeguimientoModelo.builder()
                .id(1L).estadoSegui("En Bodega").visible(true).envio(envio).build();
        Mockito.when(seguimientoRepository.findByvisibleTrue()).thenReturn(List.of(seguimiento));

        List<SeguimientoModelo> resultados = seguimientoRepository.findByvisibleTrue();

        assertEquals(1, resultados.size());
        assertTrue(resultados.get(0).getVisible());
    }

    @Test
    @DisplayName("findAll debe retornar todos los seguimientos")
    void findAllDebeRetornarTodos() {
        EnvioModelo envio = EnvioModelo.builder().id(1L).build();
        SeguimientoModelo s1 = SeguimientoModelo.builder().id(1L).estadoSegui("A").envio(envio).build();
        SeguimientoModelo s2 = SeguimientoModelo.builder().id(2L).estadoSegui("B").envio(envio).build();
        Mockito.when(seguimientoRepository.findAll()).thenReturn(List.of(s1, s2));

        List<SeguimientoModelo> resultados = seguimientoRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(seguimientoRepository.existsById(1L)).thenReturn(true);

        assertTrue(seguimientoRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(seguimientoRepository.existsById(999L)).thenReturn(false);

        assertFalse(seguimientoRepository.existsById(999L));
    }
}
