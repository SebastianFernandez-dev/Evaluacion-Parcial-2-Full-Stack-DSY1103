package com.dsy1103.msenvios.service;

import com.dsy1103.msenvios.dto.SeguimientoRequestDTO;
import com.dsy1103.msenvios.dto.SeguimientoResponseDTO;
import com.dsy1103.msenvios.mapper.SeguimientoMapper;
import com.dsy1103.msenvios.modelo.EnvioModelo;
import com.dsy1103.msenvios.modelo.SeguimientoModelo;
import com.dsy1103.msenvios.repository.EnvioRepository;
import com.dsy1103.msenvios.repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepo;
    @Autowired
    private SeguimientoMapper seguimientoMapper;
    @Autowired
    private EnvioRepository envioRepository;

    @Transactional(readOnly = true)
    public List<SeguimientoResponseDTO> listarTodos() {
        log.info("Iniciando consulta de todos los Seguimientos");
        return seguimientoRepo.findAll().stream()
                .map(seguimientoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SeguimientoResponseDTO crear(SeguimientoRequestDTO dto) {
        try {
            EnvioModelo envioExistente = envioRepository.findById(dto.getEnvioId())
                    .orElseThrow(() -> new RuntimeException("El envio con ID " + dto.getEnvioId() + " no existe."));

            SeguimientoModelo modelo = seguimientoMapper.toEntity(dto);
            modelo.setEnvio(envioExistente);

            SeguimientoModelo guardado = seguimientoRepo.save(modelo);
            return seguimientoMapper.toResponseDTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear Seguimiento: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public SeguimientoResponseDTO buscarPorId(Long id) {
        log.info("Buscando Seguimiento con ID: {}", id);
        SeguimientoModelo seguimiento = seguimientoRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Seguimiento con ID {} no encontrado", id);
                    return new EntityNotFoundException("Seguimiento no encontrado con ID: " + id);
                });
        return seguimientoMapper.toResponseDTO(seguimiento);
    }

    @Transactional
    public SeguimientoResponseDTO actualizar(Long id, SeguimientoRequestDTO dto) {
        log.info("Actualizando SEGUIMIENTO con ID: {}", id);

        SeguimientoModelo existente = seguimientoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: SEGUIMIENTO no encontrado para actualizar."));

        EnvioModelo envio = envioRepository.findById(dto.getEnvioId())
                .orElseThrow(() -> new EntityNotFoundException("Error: ENVIO no encontrado con ID: " + dto.getEnvioId()));

        existente.setEnvio(envio);
        existente.setEstadoSegui(dto.getEstadoSegui());
        existente.setUbiAtual(dto.getUbiAtual());
        existente.setObservacion(dto.getObservacion());
        existente.setFechaSegui(dto.getFechaSegui());
        existente.setVisible(dto.getVisible());

        SeguimientoModelo actualizado = seguimientoRepo.save(existente);
        log.info("SEGUIMIENTO actualizado exitosamente con ID: {}", actualizado.getId());

        return seguimientoMapper.toResponseDTO(actualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        log.info("Solicitud para eliminar Seguimiento ID: {}", id);
        if (!seguimientoRepo.existsById(id)) {
            log.error("Error al eliminar, ID {} no existe", id);
            throw new EntityNotFoundException("No se puede eliminar: Seguimiento no encontrado");
        }
        seguimientoRepo.deleteById(id);
        log.info("Seguimiento ID: {} eliminado correctamente", id);
    }
}
