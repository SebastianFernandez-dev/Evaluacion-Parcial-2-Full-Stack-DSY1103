package com.dsy1103.msenvios.repository;

import com.dsy1103.msenvios.modelo.EnvioModelo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EnvioRepositoryTest {

    @Mock
    private EnvioRepository envioRepository;

    @Test
    @DisplayName("findById debe retornar envio cuando existe")
    void findByIdDebeRetornarEnvio() {
        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").build();
        Mockito.when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));

        Optional<EnvioModelo> resultado = envioRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("ENV-001", resultado.get().getCodigoEnvio());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(envioRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<EnvioModelo> resultado = envioRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findBycodigoEnvio debe retornar envio cuando existe")
    void findBycodigoEnvioDebeRetornarEnvio() {
        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").build();
        Mockito.when(envioRepository.findBycodigoEnvio("ENV-001")).thenReturn(Optional.of(envio));

        Optional<EnvioModelo> resultado = envioRepository.findBycodigoEnvio("ENV-001");

        assertTrue(resultado.isPresent());
        assertEquals("ENV-001", resultado.get().getCodigoEnvio());
    }

    @Test
    @DisplayName("findBycodigoEnvio debe retornar empty cuando no existe")
    void findBycodigoEnvioDebeRetornarEmpty() {
        Mockito.when(envioRepository.findBycodigoEnvio("INVALIDO")).thenReturn(Optional.empty());

        Optional<EnvioModelo> resultado = envioRepository.findBycodigoEnvio("INVALIDO");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todos los envios")
    void findAllDebeRetornarTodos() {
        EnvioModelo e1 = EnvioModelo.builder().id(1L).codigoEnvio("ENV-001").build();
        EnvioModelo e2 = EnvioModelo.builder().id(2L).codigoEnvio("ENV-002").build();
        Mockito.when(envioRepository.findAll()).thenReturn(List.of(e1, e2));

        List<EnvioModelo> resultados = envioRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(envioRepository.existsById(1L)).thenReturn(true);

        assertTrue(envioRepository.existsById(1L));
    }

    @Test
    @DisplayName("existsById debe retornar false cuando no existe")
    void existsByIdDebeRetornarFalse() {
        Mockito.when(envioRepository.existsById(999L)).thenReturn(false);

        assertFalse(envioRepository.existsById(999L));
    }

    @Test
    @DisplayName("findEnviosEnRangoNoEntregados debe retornar envios filtrados")
    void findEnviosEnRangoNoEntregadosDebeFiltrar() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(7);
        LocalDateTime fin = LocalDateTime.now();
        EnvioModelo envio = EnvioModelo.builder()
                .id(1L).codigoEnvio("ENV-001").estadoEnvio("En Transito").build();
        Mockito.when(envioRepository.findEnviosEnRangoNoEntregados(inicio, fin))
                .thenReturn(List.of(envio));

        List<EnvioModelo> resultados = envioRepository.findEnviosEnRangoNoEntregados(inicio, fin);

        assertEquals(1, resultados.size());
        assertNotEquals("Entregado", resultados.get(0).getEstadoEnvio());
    }
}
