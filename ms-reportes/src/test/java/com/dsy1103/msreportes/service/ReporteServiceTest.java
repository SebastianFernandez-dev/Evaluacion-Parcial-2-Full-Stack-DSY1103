package com.dsy1103.msreportes.service;

import com.dsy1103.msreportes.client.UsuarioClient;
import com.dsy1103.msreportes.dto.ReporteRequestDTO;
import com.dsy1103.msreportes.dto.ReporteResponseDTO;
import com.dsy1103.msreportes.dto.ReporteUsuarioDTO;
import com.dsy1103.msreportes.dto.UsuarioDTO;
import com.dsy1103.msreportes.mapper.ReporteMapper;
import com.dsy1103.msreportes.model.ReporteModel;
import com.dsy1103.msreportes.repository.ReporteRepository;
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
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;
    @Mock
    private ReporteMapper reporteMapper;
    @Mock
    private UsuarioClient usuarioClient;
    @InjectMocks
    private ReporteService reporteService;

    @Test
    @DisplayName("Debe listar todos los reportes exitosamente")
    void debeListarReportesExitosamente() {
        ReporteModel reporte = ReporteModel.builder()
                .id(1L).descripcion("Reporte Q1").tipo("Ventas").build();
        ReporteResponseDTO dto = ReporteResponseDTO.builder()
                .id(1L).descripcion("Reporte Q1").tipo("Ventas").build();

        Mockito.when(reporteRepository.findAll()).thenReturn(List.of(reporte));
        Mockito.when(reporteMapper.toResponseDTO(reporte)).thenReturn(dto);

        List<ReporteResponseDTO> resultado = reporteService.listarReportes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Reporte Q1", resultado.get(0).getDescripcion());
    }

    @Test
    @DisplayName("Debe obtener reporte por ID exitosamente")
    void debeObtenerReportePorIdExitosamente() {
        Long reporteId = 1L;
        ReporteModel reporte = ReporteModel.builder()
                .id(reporteId).descripcion("Reporte Mensual").usuarioId(10L).build();
        ReporteResponseDTO dto = ReporteResponseDTO.builder()
                .id(reporteId).descripcion("Reporte Mensual").usuarioId(10L).build();

        Mockito.when(reporteRepository.findById(reporteId)).thenReturn(Optional.of(reporte));
        Mockito.when(reporteMapper.toResponseDTO(reporte)).thenReturn(dto);

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(10L).primerNombre("Juan").build();
        Mockito.when(usuarioClient.obtenerUsuarioPorId(10L)).thenReturn(usuarioDTO);

        ReporteUsuarioDTO resultado = reporteService.obtenerReportePorId(reporteId);

        assertNotNull(resultado);
        assertEquals("Reporte Mensual", resultado.getDescripcion());
        assertEquals("Juan", resultado.getPrimerNombreUsuario());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException cuando el reporte no existe")
    void debeLanzarExcepcionCuandoReporteNoExiste() {
        Mockito.when(reporteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            reporteService.obtenerReportePorId(999L);
        });
    }

    @Test
    @DisplayName("Debe guardar un reporte exitosamente")
    void debeGuardarReporteExitosamente() {
        ReporteRequestDTO dtoEntrada = ReporteRequestDTO.builder()
                .descripcion("Nuevo Reporte").tipo("Envios")
                .totalVentas(2000.0).cantidadPedidos(20).cantidadPagos(18)
                .publicado(true).usuarioId(1L).build();

        ReporteModel modelParaGuardar = ReporteModel.builder()
                .descripcion("Nuevo Reporte").tipo("Envios").build();

        ReporteModel modelGuardado = ReporteModel.builder()
                .id(1L).descripcion("Nuevo Reporte").tipo("Envios").build();

        ReporteResponseDTO dtoSalida = ReporteResponseDTO.builder()
                .id(1L).descripcion("Nuevo Reporte").tipo("Envios").build();

        Mockito.when(reporteMapper.toEntity(dtoEntrada)).thenReturn(modelParaGuardar);
        Mockito.when(reporteRepository.save(modelParaGuardar)).thenReturn(modelGuardado);
        Mockito.when(reporteMapper.toResponseDTO(modelGuardado)).thenReturn(dtoSalida);

        ReporteResponseDTO resultado = reporteService.guardarReporte(dtoEntrada);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Nuevo Reporte", resultado.getDescripcion());
        Mockito.verify(reporteRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un reporte exitosamente")
    void debeActualizarReporteExitosamente() {
        Long reporteId = 1L;
        ReporteModel existente = ReporteModel.builder()
                .id(reporteId).descripcion("Vieja Descripcion").tipo("Ventas").build();

        ReporteRequestDTO dtoActualizacion = ReporteRequestDTO.builder()
                .descripcion("Nueva Descripcion").tipo("Pagos")
                .totalVentas(500.0).cantidadPedidos(5).cantidadPagos(4)
                .publicado(false).usuarioId(1L).build();

        ReporteModel actualizado = ReporteModel.builder()
                .id(reporteId).descripcion("Nueva Descripcion").tipo("Pagos").build();

        ReporteResponseDTO dtoSalida = ReporteResponseDTO.builder()
                .id(reporteId).descripcion("Nueva Descripcion").tipo("Pagos").build();

        Mockito.when(reporteRepository.findById(reporteId)).thenReturn(Optional.of(existente));
        Mockito.when(reporteRepository.save(existente)).thenReturn(actualizado);
        Mockito.when(reporteMapper.toResponseDTO(actualizado)).thenReturn(dtoSalida);

        ReporteResponseDTO resultado = reporteService.actualizarReporte(reporteId, dtoActualizacion);

        assertNotNull(resultado);
        assertEquals("Nueva Descripcion", resultado.getDescripcion());
        assertEquals("Pagos", resultado.getTipo());
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al actualizar un reporte inexistente")
    void debeLanzarExcepcionAlActualizarReporteInexistente() {
        ReporteRequestDTO dtoActualizacion = ReporteRequestDTO.builder().build();
        Mockito.when(reporteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            reporteService.actualizarReporte(999L, dtoActualizacion);
        });
    }

    @Test
    @DisplayName("Debe lanzar EntityNotFoundException al intentar eliminar un reporte inexistente")
    void debeLanzarExcepcionAlEliminarReporteInexistente() {
        Mockito.when(reporteRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            reporteService.eliminarReporte(1L);
        });

        Mockito.verify(reporteRepository, Mockito.never()).deleteById(any());
    }

    @Test
    @DisplayName("Debe eliminar un reporte exitosamente si existe")
    void debeEliminarReporteExitosamente() {
        Long idEliminar = 1L;
        Mockito.when(reporteRepository.existsById(idEliminar)).thenReturn(true);

        reporteService.eliminarReporte(idEliminar);

        Mockito.verify(reporteRepository, Mockito.times(1)).deleteById(idEliminar);
    }

    @Test
    @DisplayName("Debe listar reportes por usuario exitosamente")
    void debeListarReportesPorUsuarioExitosamente() {
        ReporteModel reporte = ReporteModel.builder()
                .id(1L).usuarioId(10L).build();
        ReporteResponseDTO dto = ReporteResponseDTO.builder()
                .id(1L).usuarioId(10L).build();

        Mockito.when(reporteRepository.findByUsuarioId(10L)).thenReturn(List.of(reporte));
        Mockito.when(reporteMapper.toResponseDTO(reporte)).thenReturn(dto);

        List<ReporteResponseDTO> resultado = reporteService.listarReportePorUsuario(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}
