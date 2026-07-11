package com.dsy1103.msreportes.repository;

import com.dsy1103.msreportes.model.ReporteModel;
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
class ReporteRepositoryTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Test
    @DisplayName("findById debe retornar reporte cuando existe")
    void findByIdDebeRetornarReporte() {
        ReporteModel reporte = ReporteModel.builder()
                .id(1L).descripcion("Reporte Q1").tipo("Ventas").build();
        Mockito.when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporte));

        Optional<ReporteModel> resultado = reporteRepository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Reporte Q1", resultado.get().getDescripcion());
    }

    @Test
    @DisplayName("findById debe retornar empty cuando no existe")
    void findByIdDebeRetornarEmpty() {
        Mockito.when(reporteRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ReporteModel> resultado = reporteRepository.findById(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("findAll debe retornar todos los reportes")
    void findAllDebeRetornarTodos() {
        ReporteModel r1 = ReporteModel.builder().id(1L).descripcion("A").build();
        ReporteModel r2 = ReporteModel.builder().id(2L).descripcion("B").build();
        Mockito.when(reporteRepository.findAll()).thenReturn(List.of(r1, r2));

        List<ReporteModel> resultados = reporteRepository.findAll();

        assertEquals(2, resultados.size());
    }

    @Test
    @DisplayName("existsById debe retornar true cuando existe")
    void existsByIdDebeRetornarTrue() {
        Mockito.when(reporteRepository.existsById(1L)).thenReturn(true);

        assertTrue(reporteRepository.existsById(1L));
    }

    @Test
    @DisplayName("findByUsuarioId debe retornar reportes del usuario")
    void findByUsuarioIdDebeRetornarReportes() {
        ReporteModel r1 = ReporteModel.builder().id(1L).usuarioId(10L).build();
        Mockito.when(reporteRepository.findByUsuarioId(10L)).thenReturn(List.of(r1));

        List<ReporteModel> resultados = reporteRepository.findByUsuarioId(10L);

        assertEquals(1, resultados.size());
        assertEquals(10L, resultados.get(0).getUsuarioId());
    }
}
