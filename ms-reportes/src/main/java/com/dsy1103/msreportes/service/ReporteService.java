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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private ReporteMapper reporteMapper;
    @Autowired
    private UsuarioClient usuarioClient;

    public List<ReporteResponseDTO> listarReportes() {
        log.info("Listando todos los REPORTES");
        return reporteRepository.findAll()
                .stream()
                .map(reporteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReporteUsuarioDTO obtenerReportePorId(Long id) {
        log.info("Obteniendo REPORTE por ID {}", id);
        ReporteResponseDTO rDTO = reporteRepository.findById(id)
                .map(reporteMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error: El REPORTE con ID " + id + " no pudo ser encontrado"));

        return convertirConUsuario(rDTO);
    }

    public List<ReporteResponseDTO> listarReportePorUsuario(Long id) {
        log.info("Obteniendo REPORTES por USUARIO ID {}", id);
        return reporteRepository.findByUsuarioId(id)
                .stream()
                .map(reporteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReporteResponseDTO guardarReporte(ReporteRequestDTO dto) {
        log.info("Registrando REPORTE: {}", dto.getDescripcion());
        ReporteModel model = reporteMapper.toEntity(dto);
        ReporteModel guardado = reporteRepository.save(model);
        log.info("REPORTE guardado exitosamente con ID: {}", guardado.getId());
        return reporteMapper.toResponseDTO(guardado);
    }

    public ReporteResponseDTO actualizarReporte(Long id, ReporteRequestDTO dto) {
        log.info("Actualizando REPORTE con ID {}", id);

        ReporteModel existente = reporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: REPORTE no encontrado"));

        existente.setDescripcion(dto.getDescripcion());
        existente.setTipo(dto.getTipo());
        existente.setTotalVentas(dto.getTotalVentas());
        existente.setCantidadPedidos(dto.getCantidadPedidos());
        existente.setCantidadPagos(dto.getCantidadPagos());
        existente.setPublicado(dto.getPublicado());
        existente.setUsuarioId(dto.getUsuarioId());

        ReporteModel actualizado = reporteRepository.save(existente);
        return reporteMapper.toResponseDTO(actualizado);
    }

    public void eliminarReporte(Long id) {
        log.warn("Eliminando REPORTE con ID: {}", id);
        if (!reporteRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: REPORTE no encontrado");
        }
        reporteRepository.deleteById(id);
        log.info("REPORTE eliminado exitosamente con ID: {}", id);
    }

    private ReporteUsuarioDTO convertirConUsuario(ReporteResponseDTO rEntrada) {
        log.info("Intentando convertir con USUARIO ID: {}", rEntrada.getUsuarioId());

        ReporteUsuarioDTO rSalida = ReporteUsuarioDTO.builder()
                .id(rEntrada.getId())
                .descripcion(rEntrada.getDescripcion())
                .tipo(rEntrada.getTipo())
                .totalVentas(rEntrada.getTotalVentas())
                .cantidadPedidos(rEntrada.getCantidadPedidos())
                .cantidadPagos(rEntrada.getCantidadPagos())
                .fechaGeneracion(rEntrada.getFechaGeneracion())
                .publicado(rEntrada.getPublicado())
                .usuarioId(rEntrada.getUsuarioId())
                .build();

        try {
            UsuarioDTO uDTO = usuarioClient.obtenerUsuarioPorId(rEntrada.getUsuarioId());

            if (uDTO != null) {
                rSalida.setPrimerNombreUsuario(uDTO.getPrimerNombre());
                rSalida.setSegundoNombreUsuario(uDTO.getSegundoNombre());
                rSalida.setPrimerApellidoUsuario(uDTO.getPrimerApellido());
                rSalida.setSegundoApellidoUsuario(uDTO.getSegundoApellido());
                rSalida.setCorreoUsuarioUsuario(uDTO.getCorreoUsuario());
                rSalida.setRutUsuario(uDTO.getRut());
                rSalida.setDvRutUsuario(uDTO.getDvRut());
                rSalida.setActivoUsuario(uDTO.getActivo());
                rSalida.setFechaRegistroUsuario(uDTO.getFechaRegistro());
            }
        } catch (Exception e) {
            rSalida.setPrimerNombreUsuario("Servicio no disponible");
        }
        log.info("Conversion exitosa con ID: {}", rEntrada.getUsuarioId());

        return rSalida;
    }
}
